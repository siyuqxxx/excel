package com.zt.tool;

import com.zt.tool.exception.WriteExcelException;
import jxl.Workbook;
import jxl.write.WritableWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ExcelHelper {
    public String writeWorkBook(OutputStream outputStream, List<SheetWriter> sheetWriters) {
        boolean hasError = false;
        StringBuilder errMsg = new StringBuilder("\"workbook\":{");
        WritableWorkbook book = null;
        try {
            book = Workbook.createWorkbook(outputStream);

            int sheet_index = 0;
            for (SheetWriter writer : sheetWriters) {
                try {
                    writer.preform(book, sheet_index);
                } catch (WriteExcelException e) {
                    errMsg.append(String.format("{\"worksheet\":[%s], ", e.getMessage()));
                    hasError = true;
                }
                sheet_index++;
            }

            //写入数据并关闭文件
            book.write();
        } catch (IOException e) {
            e.printStackTrace();
            errMsg.append(String.format("\"IOException\":%s", e.getMessage()));
            hasError = true;
        } finally {
            if (book != null) {
                try {
                    book.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    errMsg.append(String.format("\"closeIOErr\":%s", e.getMessage()));
                    hasError = true;
                }
            }
        }

        if (hasError) {
            errMsg.append("}");
            return errMsg.toString();
        }
        return "success";
    }
}
