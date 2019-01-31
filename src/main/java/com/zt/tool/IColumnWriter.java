package com.zt.tool;

import jxl.write.WriteException;

public interface IColumnWriter<T> {
    public String preform(T been) throws WriteException;
}