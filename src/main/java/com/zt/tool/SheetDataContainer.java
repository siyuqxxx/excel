package com.zt.tool;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class SheetDataContainer<T> {
    private String SheetName = "";
    private List<String> headers = new LinkedList<>();
    private List<T> data = new LinkedList<>();
    private IDataWriter<T> writer;

    public SheetDataContainer<T> addHead(String h) {
        if (Objects.nonNull(h) && !h.trim().isEmpty() && !this.headers.contains(h)) {
            this.headers.add(h);
        }
        return this;
    }

    public String getSheetName() {
        return SheetName;
    }

    public SheetDataContainer<T> setSheetName(String sheetName) {
        SheetName = sheetName;
        return this;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public SheetDataContainer<T> setHeaders(List<String> headers) {
        if (Objects.nonNull(headers)) {
            this.headers.clear();
            this.headers.addAll(headers);
        }
        return this;
    }

    public List<T> getData() {
        return data;
    }

    public SheetDataContainer<T> setData(List<T> data) {
        if (Objects.nonNull(data)) {
            this.data.clear();
            this.data.addAll(data);
        }
        return this;
    }

    public IDataWriter<T> getWriter() {
        return Objects.nonNull(this.writer) ? this.writer : ((sheet, row, been) -> {});
    }

    public SheetDataContainer<T> setWriter(IDataWriter<T> writer) {
        this.writer = writer;
        return this;
    }

    @Override
    public String toString() {
        return "SheetDataContainer{" +
                "SheetName='" + SheetName + '\'' +
                ", headers=" + headers +
                ", data=" + data +
                ", writer=" + writer +
                '}';
    }
}
