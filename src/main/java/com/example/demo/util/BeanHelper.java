package com.example.demo.util;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: bean copy
 */
@Slf4j
public class BeanHelper extends PropertyUtils {

    /**
     * 对象copy
     * @param sourceList sourceList
     * @param target target
     * @param <T> t
     * @return t
     */
    public static <T> List<T> copyTo(List<?> sourceList, Class<T> target) {
        if(CollectionUtils.isEmpty(sourceList)) {
            return Collections.emptyList();
        }
        List<T> list = Lists.newArrayList();
        try {
            for (Object o : sourceList) {
                list.add(copyTo(o, target));
            }
            return list;
        } catch (Exception e) {
            log.error("数组复制出现错误source=[{}],targetType=[{}],errMsg=[{}]", sourceList, target, e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 对象copy
     * @param sourceObj sourceObj
     * @param target 目标
     * @param <T> t
     * @return t
     */
    public static <T> T copyTo(Object sourceObj, Class<T> target) {
        if(sourceObj == null) {
            return null;
        }
        try {
            T e = target.newInstance();
            PropertyUtils.copyProperties(e, sourceObj);
            return e;
        } catch (Exception e) {
            log.error("对象复制出现错误source=[{}],targetType=[{}],errMsg=[{}]", sourceObj, target, e);
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * bean转成map，忽略null。
     *
     * @param obj 对象
     * @return Map
     */
    public static Map<String, Object> toMap(Object obj) {
        Map<String, Object> result = new HashMap<>();

        for(Class clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] fs = clazz.getDeclaredFields();
            for (Field f : fs) {
                if (!Modifier.isStatic(f.getModifiers())) {
                    f.setAccessible(true);
                    try {
                        Object value = f.get(obj);
                        if (value != null) {
                            result.put(f.getName(), String.valueOf(value));
                        }
                    } catch (Exception var7) {
                        log.error("对象转map出现错误source=[{}],errMsg=[{}]", new Object[]{obj, var7.getMessage()});
                        throw new RuntimeException(var7.getMessage());
                    }
                }
            }
        }
        return result;
    }
}
