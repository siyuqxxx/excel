package com.zt.tool.exception;

public class EmptyWorksheetException extends ReadExcelException {
    public EmptyWorksheetException() {
        super("Empty worksheet!");
    }
}
