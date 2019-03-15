package com.zt.tool.annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class ToExcelParser {
    private static final String METHOD_PREFIX = "get";
    private static final String STR_REQUIRED = " (必填)";

    public static List<String> getHeader(Class<?> clazz) {
        return getMethodInDeclaringClassAndStartWithGet(clazz).stream()
                .map(m -> new Header()
                        .setMethodName(m.getName())
                        .setHeader(m.getAnnotation(ToExcel.class).columnName())
                        .setRequired(m.getAnnotation(ToExcel.class).required()))
                .map(Header::getHeader).collect(Collectors.toList());
    }

    public static List<String> getRowData(Object obj) {
        List<Method> methodList = getMethodInDeclaringClassAndStartWithGet(obj.getClass());
        List<String> rowData = new ArrayList<>(methodList.size());
        for (Method m : methodList) {
            try {
                rowData.add((String) m.invoke(obj));
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                rowData.add("");
            }
        }
        return rowData;
    }

    private static List<Method> getMethodInDeclaringClassAndStartWithGet(Class<?> clazz) {
        return Arrays.stream(Optional.ofNullable(clazz).map(Class::getMethods).orElse(new Method[0]))
                .filter(m -> m.getDeclaringClass().equals(clazz))
                .filter(m -> m.getReturnType().equals(String.class))
                .filter(m -> m.getName().startsWith(METHOD_PREFIX))
                .sorted(new MethodSorter())
                .collect(Collectors.toList());
    }

    private static class Header {
        private String methodName = "";
        private String header = "";
        private boolean required = false;

        public Header setMethodName(String methodName) {
            this.methodName = methodName;
            return this;
        }

        public String getHeader() {
            if (this.header.trim().isEmpty()) {
                return this.methodName.substring(METHOD_PREFIX.length());
            }
            if (required) {
                return header + STR_REQUIRED;
            }
            return header;
        }

        public Header setHeader(String header) {
            this.header = header;
            return this;
        }

        public Header setRequired(boolean required) {
            this.required = required;
            return this;
        }
    }

    private static class MethodSorter implements Comparator<Method> {
        @Override
        public int compare(Method m1, Method m2) {
            ToExcel a1 = m1.getAnnotation(ToExcel.class);
            ToExcel a2 = m2.getAnnotation(ToExcel.class);
            if (Objects.nonNull(a1) && Objects.nonNull(a2)) {
                int i = a1.columnIndex() - a2.columnIndex();
                return i == 0 ? m1.getName().compareTo(m2.getName()) : i;
            } else if (Objects.nonNull(a1)) {
                return 1;
            } else if (Objects.nonNull(a2)) {
                return -1;
            } else {
                return m1.getName().compareTo(m2.getName());
            }
        }
    }
}
