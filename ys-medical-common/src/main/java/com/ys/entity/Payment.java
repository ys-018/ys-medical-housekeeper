package com.ys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author
 * @ClassName:
 * @Description:
 * @date
 */
// @Data 注解在类上，会为类的所有属性自动生成setter/getter、equals、canEqual、hashCode、toString方法，如为final属性，则不会为该属性生成setter方法
// @NoArgsConstructor 空构造器
// @RequiredArgsConstructor 部分参数的构造器
// @AllArgsConstructor 全部参数的构造器

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment implements Serializable {

    private Long id;
    private String serial;
}
