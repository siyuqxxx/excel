package com.zt.tool;

import jxl.write.WritableSheet;
import jxl.write.WriteException;

public interface IDataWriter<T> {
    public void preform(WritableSheet sheet, int row, T been) throws WriteException;
}
