package com.jteam.GroupProject.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class EntityUtils {
    /**
     * Метод для копирования из одного объекта в другой
     * игнорируя поля в которых есть null
     *
     * @param fromObj объект от которого копируем
     * @param toObj   объект в который копируем
     */
    public static void copyNonNullFields(Object fromObj, Object toObj) {
        // Проверка на null перед доступом к полям
        if (fromObj == null || toObj == null) {
            return;
        }

        Logger logger = LoggerFactory.getLogger(EntityUtils.class);
        Field[] fields = fromObj.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(fromObj);
                if (value != null) {
                    field.set(toObj, value);
                }
            } catch (IllegalAccessException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

}
