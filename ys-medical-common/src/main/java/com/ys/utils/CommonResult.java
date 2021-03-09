package com.ys.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yingsu
 * @version V1.0
 * @Title: CommonResult.java
 * @Package com.ys.utils
 * @Description: 自定义响应数据结构
 * 这个类是提供给门户，ios，安卓，微信商城用的
 * 门户接受此类数据后需要使用本类的方法转换成对于的数据类型格式（类，或者list）
 * 其他自行处理
 * 200：表示成功
 * 500：表示错误，错误信息在msg字段中
 * 501：bean验证错误，不管多少个错误都以map形式返回
 * 502：拦截器拦截到用户token出错
 * 555：异常抛出信息
 * Copyright: Copyright (c) 2016
 * Company:ys
 * @date 2021年01月05日 下午23:33:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {

    private int code;

    private String message;

    private T data;

    public static <T> CommonResult<T> success() {
        return new CommonResult<T>(200, "success", null);
    }

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(200, "success", data);
    }

    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<T>(200, message, data);
    }

    public static <T> CommonResult<T> errorMsg(String message) {
        return new CommonResult<T>(500, message, null);
    }

    public static <T> CommonResult<T> errorMap(T data) {
        return new CommonResult<T>(501, "error", data);
    }

    public static <T> CommonResult<T> errorTokenMsg(String message) {
        return new CommonResult<T>(502, message, null);
    }

    public static <T> CommonResult<T> errorException(String message) {
        return new CommonResult<T>(555, message, null);
    }

    public static <T> CommonResult<T> error(int code, String message) {
        return new CommonResult<T>(code, message, null);
    }

}
