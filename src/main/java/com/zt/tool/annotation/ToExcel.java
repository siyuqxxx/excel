package com.zt.tool.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 将 Bean 导出到 excel 注解
 *
 * 通过 columnName 指定导出的数据表的表头名称；如果不指定，自动去掉方法名中的 get 前缀 做为表头
 * 通过 columnIndex 指定导出的数据表各列的顺序； 排序优先级 1. index 2. 方法名字符串排序
 *
 * 注意：
 * 此注解不可作用于父类
 * 被注解的方法必须是共有的
 * 被注解的方法必须返回 String 类型
 * 被注解的方法必须是以 get 开头；例如： getName()
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ToExcel {
    String columnName() default "";
    int columnIndex() default 1;
}
