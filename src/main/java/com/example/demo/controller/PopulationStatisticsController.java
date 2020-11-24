package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.base.BaseAdminController;
import com.example.demo.entity.PopulationStatistics;
import com.example.demo.entity.PopulationStatisticsDetail;
import com.example.demo.service.IPopulationStatisticsDetailService;
import com.example.demo.service.IPopulationStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * <p>
 * 人员统计表 前端控制器
 * </p>
 *
 * @author DuQian
 * @since 2020-11-23
 */
@Controller
@RequestMapping("/populationStatistics")
@ResponseBody
public class PopulationStatisticsController extends BaseAdminController<PopulationStatistics, IPopulationStatisticsService> {

    @Autowired
    private IPopulationStatisticsDetailService populationStatisticsDetailService;

    @GetMapping("/work")
    public void work(Integer type, String ip, String country) {
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        String today = s.format(new Date());
        QueryWrapper<PopulationStatistics> wrapper = new QueryWrapper<>();
        wrapper.eq("date", today);
        PopulationStatistics statistics = this.serviceImpl.getOne(wrapper);
        PopulationStatisticsDetail detail = new PopulationStatisticsDetail();
        detail.setDate(today);
        detail.setIp(ip);
        detail.setCountry(country);
        detail.setType(type == 1? "install" : "uninstall");
        populationStatisticsDetailService.save(detail);
        switch (type) {
            //卸载+1
            case 0:
                if (Objects.isNull(statistics)) {
                    statistics = new PopulationStatistics();
                    statistics.setDate(today);
                    statistics.setPeopleOfIncrease(0);
                    statistics.setPeopleOfUninstall(1);
                    statistics.setTotalRemaining(0);
                    this.serviceImpl.save(statistics);
                } else {
                    statistics.setPeopleOfUninstall(statistics.getPeopleOfUninstall() + 1);
                    statistics.setTotalRemaining(statistics.getTotalRemaining() - 1);
                    this.serviceImpl.updateById(statistics);
                }
                break;
            //新增+1
            case 1:
                if (Objects.isNull(statistics)) {
                    statistics = new PopulationStatistics();
                    statistics.setDate(today);
                    statistics.setPeopleOfIncrease(1);
                    statistics.setPeopleOfUninstall(0);
                    statistics.setTotalRemaining(1);
                    this.serviceImpl.save(statistics);
                } else {
                    statistics.setPeopleOfIncrease(statistics.getPeopleOfIncrease() + 1);
                    statistics.setTotalRemaining(statistics.getTotalRemaining() + 1);
                    this.serviceImpl.updateById(statistics);
                }
                break;
            default:
        }
    }

}
