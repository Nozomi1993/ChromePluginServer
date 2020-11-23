package com.example.demo.util;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class HttpParamUtil {

    public static Map<String, Object> getRequestMap(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String paraName = enu.nextElement();
            result.put(paraName, request.getParameter(paraName));
        }
        return result;
    }

    /**
     * 获取所有头文件值
     *
     * @param request
     * @return
     */
    public static String getHeaders(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Enumeration<String> enumerations = request.getHeaderNames();
        while (enumerations.hasMoreElements()) {
            String headerName = enumerations.nextElement();
            String headerValue = request.getHeader(headerName);
            result.put(headerName, headerValue);
        }
        return result.toString();
    }

    /**
     * 获取客户端真实ip
     *
     * @param request
     * @return
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

}
