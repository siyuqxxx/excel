package com.zt.tool;

import com.zt.tool.annotation.ToExcel;

public class Product {
    private String name = "";
    private String description = "";
    private String price = "";

    @ToExcel(columnName = "产品名称", columnIndex = 1)
    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    @ToExcel(columnName = "产品描述", columnIndex = 2)
    public String getDescription() {
        return description;
    }

    public Product setDescription(String description) {
        this.description = description;
        return this;
    }

    @ToExcel(columnName = "单价", columnIndex = 3)
    public String getPrice() {
        return price;
    }

    public Product setPrice(String price) {
        this.price = price;
        return this;
    }
}
