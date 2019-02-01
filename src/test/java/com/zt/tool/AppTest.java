package com.zt.tool;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertTrue;

public class AppTest {
    private File file = new File("C:\\Users\\think\\Desktop\\test.xls");

    @Test
    public void dataList2excel() {
        ArrayList<Product> data = new ArrayList<>();
        data.add(new Product().setName("PC").setDescription("personal computer").setPrice("3999.00"));

        List<SheetWriter> writers = Collections.singletonList(new SheetWriter<Product>().setSheetName("data list").setType(Product.class));

        boolean success = write(writers, this.file);
        assertTrue(success);
    }

    @Test
    public void emptyDataList2excel() {
        List<SheetWriter> writers = Collections.singletonList(new SheetWriter<Product>().setSheetName("data list").setType(Product.class));

        boolean success = write(writers, this.file);
        assertTrue(success);
    }

    private static boolean write(List<SheetWriter> writers, File file) {
        try (FileOutputStream stream = new FileOutputStream(file);) {
            new ExcelHelper().writeWorkBook(stream, writers);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Test
    public void tableHeader2excel() {
        List<SheetWriter> writers = Collections.singletonList(new SheetWriter<Product>().setSheetName("data list").setType(Product.class));

        boolean success = write(writers, this.file);
        assertTrue(success);
    }
}
