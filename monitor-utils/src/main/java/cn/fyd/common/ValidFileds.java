package cn.fyd.common;

import cn.fyd.annotation.IsEmpty;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static cn.fyd.common.Constant.VERIFY_OBJECT_CAN_NOT_BE_EMPTY;

/**
 * 验证参数公用类
 * @author fanyidong
 * @date Created in 2018-12-12
 */
public class ValidFileds {

    private static Map<Class, Field[]> fieldMap = new HashMap<Class, Field[]>();

    public static void verificationoColumn(Object object) throws MonitorException {
        if (object == null) {
            throw new MonitorException(VERIFY_OBJECT_CAN_NOT_BE_EMPTY);
        }
        if (!fieldMap.containsKey(object.getClass())) {
            fieldMap.put(object.getClass(), object.getClass().getDeclaredFields());
        }
        Field[] fields = fieldMap.get(object.getClass());
        for (int i = 0; i < fields.length; ++i) {
            Field field = fields[i];
            field.setAccessible(true);
            Object fieldObject;
            try {
                fieldObject = field.get(object);
            } catch (IllegalAccessException e) {
                fieldObject = null;
            }
            checkPersistenceColumn(field, fieldObject);
        }
    }

    private static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        } else {
            if (object instanceof String) {
                if (String.valueOf(object).length() == 0) {
                    return true;
                }
            }
            return false;
        }
    }

    public static void checkPersistenceColumn(Field field, Object fieldObject) throws MonitorException {
        if (field.isAnnotationPresent(IsEmpty.class)) {
            IsEmpty isEmpty = field.getAnnotation(IsEmpty.class);
            checkRequired(isEmpty, fieldObject);
        }
    }

    public static void checkRequired(IsEmpty isEmpty, Object fieldObject) throws MonitorException {
        if (isEmpty.require() && isEmpty(fieldObject)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(isEmpty.name()).append(" ").append(isEmpty.requireFailMessage());
            throw new MonitorException(stringBuilder.toString());
        }
    }
}
