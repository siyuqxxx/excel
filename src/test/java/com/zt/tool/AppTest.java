package com.zt.tool;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class AppTest {
    private File file = new File("C:\\Users\\think\\Desktop\\test.xls");

    @Test
    public void dataList2excel() {
        ArrayList<Product> data = new ArrayList<>();
        data.add(new Product().setName("PC").setDescription("personal computer").setPrice("3999.00"));


        List<SheetWriter> writers = new LinkedList<>();
        writers.add(new SheetWriter<Product>().setSheetName("data list").setSheetData(data));



        boolean success = write(writers, this.file);
        assertTrue(success);
    }

    @Test
    public void emptyDataList2excel() {
        List<SheetWriter> writers = new LinkedList<>();
        writers.add(new SheetWriter<Product>().setSheetName("data list").setSheetData(new ArrayList<>()));


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
}
