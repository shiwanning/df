package com.tcg.mis.common.util;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;

public class ReflectUtil {

    private ReflectUtil() {
        throw new IllegalStateException("Utility class");
    }
    
    public static <T> T getFieldValue(Object model, String fieldName) {
        Field field = FieldUtils.getDeclaredField(model.getClass(), fieldName, true);
        try {
            return field != null ? (T) field.get(model) : null;
        } catch (IllegalAccessException ignored) {
            return null;
        }
    }

    public static <T> T getSuperFieldValue(Object model, String fieldName) {
        Field field = FieldUtils.getDeclaredField(model.getClass().getSuperclass(), fieldName, true);
        try {
            return field != null ? (T) field.get(model) : null;
        } catch (IllegalAccessException ignored) {
            return null;
        }
    }

    public static void setFieldValue(Object model, String fieldName, Object value) {
        try {
            Field field = FieldUtils.getDeclaredField(model.getClass(), fieldName, true);
            field.set(model, value);
        } catch (IllegalAccessException ignored) {
        }
    }

    public static void setSuperFieldValue(Object model, String fieldName, Object value) {
        try {
            Field field = FieldUtils.getDeclaredField(model.getClass().getSuperclass(), fieldName, true);
            field.set(model, value);
        } catch (IllegalAccessException ignored) {
        }
    }

    public static void main(String[] args) {
        BigDecimal a = new BigDecimal("2");
        System.out.println(a.signum());
        BigDecimal b = BigDecimal.ZERO;
        System.out.println(b.add(a)); ;
    }

}
