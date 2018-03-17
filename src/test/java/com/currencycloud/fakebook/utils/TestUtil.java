package com.currencycloud.fakebook.utils;

import java.lang.reflect.Field;

/**
 * Created by lekanomotayo on 16/03/2018.
 */
public class TestUtil {

    public static void setField(Object object, String key, Object value) throws Exception{
        Field field = object.getClass().getDeclaredField(key);
        field.setAccessible(true);
        field.set(object, value);
    }
}
