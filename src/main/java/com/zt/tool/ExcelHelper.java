package com.zt.tool;

import com.zt.tool.annotation.ToExcelParser;
import com.zt.tool.exception.EmptyWorksheetException;
import com.zt.tool.exception.WriteExcelException;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public <T> String readWorkBook(InputStream inputStream, Class<T> type) {
        //仅支持 xls格式的excel
        final int SHEET_1 = 0;
        final int TABLE_HEAD = 1;

        List <T> importList = new LinkedList<>();
        if (Objects.nonNull(inputStream)) {
            Workbook wb = null;
            try {
                wb = Workbook.getWorkbook(inputStream);
                Sheet sheet = wb.getSheet(SHEET_1);
                int rows = sheet.getRows();//得到所有的行
                if (rows <= 1) {
                    throw new EmptyWorksheetException();
                }

                List<String> actualTableHead = Arrays.stream(sheet.getRow(TABLE_HEAD)).map(Cell::getContents).collect(Collectors.toList());
                List<String> exceptTableHead = ToExcelParser.getHeader(type);


                int columns = sheet.getColumns();//得到所有的列
                for (int row = 1; row < rows; row++) {
                    for (int column = 1; column < columns; column++) {
                        //第一个是列数，第二个是行数
                    }
                }
            } catch (BiffException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!checkTableHead(null)) {
                return "模板格式错误，请重新下载模板！";
            }

        }
        return "success";
    }

    private boolean checkTableHead(Sheet rs) {
        String[] heads = {"序号", "学号", "姓名", "分院编号", "分院名称", "年级", "学制"};
        int row = 0;
        int column = 0;
        for (String h : heads) {
            if (!h.equals(rs.getCell(column, row).getContents())) {
                return false;
            }
            column++;
        }
        return true;
    }

    /**
     * 首先进行入参检查防止出现空指针异常
     * 如果两个参数都为空，则返回true
     * 如果有一项为空，则返回false
     * 接着对第一个list进行遍历，如果某一项第二个list里面没有，则返回false
     * 还要再将两个list反过来比较，因为可能一个list是两一个list的子集
     * 如果成功遍历结束，返回true
     * @param listA
     * @param listB
     * @return
     */
    public static <T> boolean isListEqual(List<T> listA, List<T> listB){
        if (listA == listB)
            return true;
        if (listA == null && listB == null)
            return true;
        if (listA == null || listB == null)
            return false;
        if (listA.size() != listB.size())
            return false;
        for (T o : listA) {
            if (!listB.contains(o))
                return false;
        }
        for (T o : listB) {
            if (!listA.contains(o))
                return false;
        }
        return true;
    }

}
