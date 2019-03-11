[TOC]

# 目的

简化 bean =\> excel; excel =\> bean 的导入导出过程

# 使用说明

基于注解控制 bean 中哪些字段需要导入到 excel 中。

+ 通过 columnName 指定导出的数据表的表头名称；如果不指定，自动去掉方法名中的 get 前缀 做为表头名称
+ 通过 columnIndex 指定导出的数据表各列的顺序； 排序优先级 1. index 2. 方法名字符串排序

注意：
+ 此注解不可作用于父类
+ 被注解的方法必须是公有的（public）
+ 被注解的方法必须返回 String 类型
+ 被注解的方法必须是以 get 开头；例如： getName()

# 版本说明

## V1.3

支持根据 Bean.class 生成只包含表头的空白 excel 模板

## V1.2

支持通过注解的方式导出 List\<bean\> 数据到 excel

# 核心依赖

```xml
<!-- excel 读写 -->
<dependency>
  <groupId>net.sourceforge.jexcelapi</groupId>
  <artifactId>jxl</artifactId>
  <version>2.6.12</version>
</dependency>
```