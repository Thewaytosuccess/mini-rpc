package com.rpc.mini.common.util;

import lombok.SneakyThrows;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xhzy
 */
public class ReflectionUtil {

    @SneakyThrows
    public static <T> T newInstance(Class<T> clazz){
        return clazz.newInstance();
    }

    public static List<Method> getPublicMethods(Class<?> clazz){
        return Arrays.stream(clazz.getDeclaredMethods()).filter(e -> Modifier.isPublic(e.getModifiers())).collect(Collectors.toList());
    }
}
