package com.zt.tool;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

class SheetDataContainer<T> {
    private String SheetName = "";
    private List<T> data = new LinkedList<>();

    String getSheetName() {
        return this.SheetName;
    }

    void setSheetName(String sheetName) {
        SheetName = sheetName;
    }

    List<T> getData() {
        return data;
    }

    SheetDataContainer<T> setData(List<T> data) {
        if (Objects.nonNull(data)) {
            this.data.clear();
            this.data.addAll(data);
        }
        return this;
    }

    Class<?> getEntityClass() {
        return this.data.isEmpty() ? null : this.data.get(0).getClass();
    }

    @Override
    public String toString() {
        return "SheetDataContainer{" +
                "SheetName='" + SheetName + '\'' +
                ", data=" + data +
                '}';
    }
}
