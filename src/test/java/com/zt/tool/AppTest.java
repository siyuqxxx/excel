package com.zt.tool;

import jxl.write.Label;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.fail;

public class AppTest {
    @Test
    public void dataList2excel() {
        ArrayList<Product> data = new ArrayList<>();
        data.add(new Product().setName("PC").setDescription("personal computer").setPrice("3999.00"));

        SheetDataContainer<Product> container = new SheetDataContainer<>();
        container.setSheetName("data list");
        container.addHead("产品名称").addHead("产品描述").addHead("单价");
        container.setWriter(((sheet, row, been) -> {
            sheet.addCell(new Label(0, row, been.getName()));
            sheet.addCell(new Label(0, row, been.getDescription()));
            sheet.addCell(new Label(0, row, been.getPrice()));
        }));
        container.setData(data);

        List<SheetWriter> writers = new LinkedList<>();
        writers.add(new SheetWriter<Product>().addData(container));

        File file = new File("C:\\Users\\think\\Desktop\\test.xls");
        try (FileOutputStream stream = new FileOutputStream(file)) {
            new ExcelHelper().writeWorkBook(stream, writers);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }
}
