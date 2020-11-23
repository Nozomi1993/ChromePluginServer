package com.example.demo.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Query;
import com.example.demo.entity.ResultResponse;
import com.example.demo.util.HttpParamUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
public class BaseAdminController<T, S extends IService<T>> {

    @Autowired
    protected S serviceImpl;

    private static Pattern pattern = Pattern.compile("[A-Z]");

    @GetMapping("/getOne/{id}")
    public ResultResponse<T> getOne(@PathVariable String id) {
        return ResultResponse.success(serviceImpl.getById(id));
    }

    @GetMapping("/getAll")
    public ResultResponse<List<T>> getAll(Query query, String field, String start, String end, String order) {
        QueryWrapper<T> wrapper = formatQueryWrapper();
        if (StringUtils.hasLength(start)) {
            start = start + " 00:00:00";
            wrapper.ge(underline(field), start);
        }
        if (StringUtils.hasLength(end)) {
            end = end + " 23:59:59";
            wrapper.le(underline(field), end);
        }
        Class<T> clazz = deSerializable();
        try {
            clazz.getDeclaredField("isValid");
            wrapper.eq("is_valid", 1);
        } catch (NoSuchFieldException e) {
            log.debug("no is valid");
        }
        if (StringUtils.hasLength(order)) {
            wrapper.orderByDesc(order);
        }
        List<T> list = serviceImpl.list(wrapper);
        return ResultResponse.success(list);
    }

    @GetMapping("/getPage")
    public ResultResponse<T> getPage(Query query, String field, String start, String end, String order) {
        QueryWrapper<T> wrapper = formatQueryWrapper();
        if (StringUtils.hasLength(start)) {
            start = start + " 00:00:00";
            wrapper.ge(underline(field), start);
        }
        if (StringUtils.hasLength(end)) {
            end = end + " 23:59:59";
            wrapper.le(underline(field), end);
        }
        Class<T> clazz = deSerializable();
        try {
            clazz.getDeclaredField("isValid");
            wrapper.eq("is_valid", 1);
        } catch (NoSuchFieldException e) {
            log.debug("no is valid");
        }
        if (StringUtils.hasLength(order)) {
            wrapper.orderByDesc(order);
        }
        IPage<T> page = serviceImpl.page(query.conversionToPage(), wrapper);
        return ResultResponse.success(page.getRecords(), page.getTotal());
    }

    @PutMapping("update")
    public ResultResponse update(@RequestBody T obj) {
        this.serviceImpl.updateById(obj);
        return ResultResponse.success();
    }

    @PutMapping("batchUpdate")
    public ResultResponse batchUpdate(@RequestBody List<T> obj) {
        this.serviceImpl.updateBatchById(obj);
        return ResultResponse.success();
    }

    @PostMapping("insert")
    public ResultResponse insert(@RequestBody T obj) {
        try {
            //新增时设置创建人
            Class<?> cls = obj.getClass();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
            String keyProperty = tableInfo.getKeyProperty();
            Object idVal = ReflectionKit.getMethodValue(cls, obj, tableInfo.getKeyProperty());
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        this.serviceImpl.save(obj);
        return ResultResponse.success();
    }

    @DeleteMapping("delById")
    @ResponseBody
    public ResultResponse delById(String id) {
        this.serviceImpl.removeById(id);
        return ResultResponse.success();
    }

    @DeleteMapping("delList")
    @ResponseBody
    public ResultResponse delList(@RequestBody List<Integer> list) {
        this.serviceImpl.removeByIds(list);
        return ResultResponse.success();
    }

    protected QueryWrapper<T> formatQueryWrapper() {
        //模糊查询 like
        //精确查询 eq
        //小于 lt
        //大于 gt
        //小于等于 le
        //大于等于 ge
        //下滑先分隔
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        Map<String, Object> param = this.getRequestMap();
        Iterator<String> it = param.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            Object value = param.get(key);
            if (StringUtils.isEmpty(value)) {
                continue;
            }
            if (!key.contains("_")) {
                continue;
            }
            String[] strs = key.split("_");
            if (strs[0].toLowerCase().equals("like")) {
                wrapper.like(underline(strs[1]), value);
            } else if (strs[0].toLowerCase().equals("eq")) {
                wrapper.eq(underline(strs[1]), value);
            } else if (strs[0].toLowerCase().equals("lt")) {
                wrapper.lt(underline(strs[1]), value);
            } else if (strs[0].toLowerCase().equals("gt")) {
                wrapper.gt(underline(strs[1]), value);
            } else if (strs[0].toLowerCase().equals("le")) {
                wrapper.le(underline(strs[1]), value);
            } else if (strs[0].toLowerCase().equals("ge")) {
                wrapper.ge(underline(strs[1]), value);
            } else if (strs[0].toLowerCase().equals("ne")) {
                wrapper.ne(underline(strs[1]), value);
            } else if (strs[0].toLowerCase().equals("isnull")) {
                String c = (String) value;
                wrapper.isNull(c);
            } else if (strs[0].toLowerCase().equals("nenull")) {
                String c = (String) value;
                wrapper.isNotNull(c);
            } else if (strs[0].toLowerCase().equals("in")) {
                String c = (String) value;
                wrapper.in(underline(strs[1]), Arrays.asList(c.split(",")));
            }
        }
        return wrapper;
    }

    @Autowired
    protected HttpServletRequest request;

    protected Map<String, Object> getRequestMap() {
        return HttpParamUtil.getRequestMap(request);
    }

    /**
     * 驼峰转下划线
     *
     * @param str
     * @return
     */
    public static String underline(String str) {
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer(str);
        if (matcher.find()) {
            sb = new StringBuffer();
            //将当前匹配子串替换为指定字符串，并且将替换后的子串以及其之前到上次匹配子串之后的字符串段添加到一个StringBuffer对象里。
            //正则之前的字符和被替换的字符
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
            //把之后的也添加到StringBuffer对象里
            matcher.appendTail(sb);
        } else {
            return sb.toString();
        }
        return underline(sb.toString());
    }

    private Class<T> deSerializable() {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            return (Class<T>) parameterizedType.getActualTypeArguments()[0];
        }
        throw new RuntimeException();
    }

}
