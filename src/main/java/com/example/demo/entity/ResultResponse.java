package com.example.demo.entity;

import lombok.Data;

/**
 * @Author: ljl
 * @Date: 2019/5/7 0007 10:08
 * @Version 1.0
 */

@Data
public class ResultResponse<T> {

    private String info;

    private T data;

    private Integer status;

    private T total;

    public static <T> ResultResponse success(T data) {

        ResultResponse<T> resultResponse = new ResultResponse<>();

        resultResponse.setStatus(1);

        resultResponse.setInfo("提交成功");

        resultResponse.setData(data);

        return resultResponse;

    }

    public static <T> ResultResponse success(T data, T total) {

        ResultResponse<T> resultResponse = new ResultResponse<>();

        resultResponse.setStatus(1);

        resultResponse.setInfo("success");

        resultResponse.setData(data);

        resultResponse.setTotal(total);

        return resultResponse;

    }

    public static <T> ResultResponse<T> success() {

        ResultResponse<T> resultResponse = new ResultResponse<>();

        resultResponse.setStatus(1);

        resultResponse.setInfo("提交成功");

        return resultResponse;

    }

    public static <T> ResultResponse<T> error(String msg) {

        ResultResponse<T> resultResponse = new ResultResponse<>();

        resultResponse.setStatus(666);

        resultResponse.setInfo(msg);

        return resultResponse;

    }

    public static <T> ResultResponse<T> error(Integer status, String msg) {

        ResultResponse<T> resultResponse = new ResultResponse<>();

        resultResponse.setStatus(status);

        resultResponse.setInfo(msg);

        return resultResponse;

    }

}
