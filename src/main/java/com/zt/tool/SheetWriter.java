package com.zt.tool;

import com.zt.tool.annotation.ToExcelParser;
import com.zt.tool.exception.WriteExcelException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.util.List;

public class SheetWriter<T> {
    private SheetDataContainer<T> data = new SheetDataContainer<>();

    public SheetWriter<T> setSheetName(String name) {
        this.data.setSheetName(name);
        return this;
    }

    public SheetWriter<T> setSheetData(List<T> data) {
        this.data.setData(data);
        return this;
    }

    void preform(WritableWorkbook book, int sheet_index) throws WriteExcelException {
        boolean hasError = false;
        StringBuilder errMsg = new StringBuilder("\"worksheet\":{");
        WritableSheet sheet = book.createSheet(this.data.getSheetName(), sheet_index);

        // 设置表头
        int row = 0;
        try {
            writerHeader(sheet, row, ToExcelParser.getHeader(this.data.getEntityClass()));
        } catch (WriteExcelException e) {
            errMsg.append(String.format("{\"head\":[%s], ", e.getMessage()));
            hasError = true;
        }

        // 写入数据
        try {
            writeData(sheet, row);
        } catch (WriteExcelException e) {
            errMsg.append(String.format("{\"data\":[%s]", e.getMessage()));
            hasError = true;
        }

        if (hasError) {
            errMsg.append("}");
            throw new WriteExcelException(errMsg.toString());
        }
    }

    private void writeData(WritableSheet sheet, int row) throws WriteExcelException {
        boolean hasError = false;
        StringBuilder errMsg = new StringBuilder();
        for (T rowBean : this.data.getData()) {
            row++;
            try {
                int column = 0;
                for (String value : ToExcelParser.getRowData(rowBean)) {
                    sheet.addCell(new Label(column, row, value));
                    column++;
                }
            } catch (WriteException e) {
                errMsg.append(String.format("{\"row\":%d, \"value\":\"%s\"}, ", row, rowBean));
                hasError = true;
            }
        }

        if (hasError) {
            errMsg.deleteCharAt(errMsg.length() - 2);
            throw new WriteExcelException(errMsg.toString());
        }
    }

    private void writerHeader(WritableSheet sheet, int row, List<String> headers) throws WriteExcelException {
        int column = 0;
        boolean hasError = false;
        StringBuilder errMsg = new StringBuilder();

        for (String h : headers) {
            try {
                sheet.addCell(new Label(column, row, h));
            } catch (WriteException e) {
                errMsg.append(String.format("{\"column\":%d,\"value\":\"%s\"}, ", column, h));
                hasError = true;
            }
            column++;
        }

        if (hasError) {
            errMsg.deleteCharAt(errMsg.length() - 2);
            throw new WriteExcelException(errMsg.toString());
        }
    }
}
