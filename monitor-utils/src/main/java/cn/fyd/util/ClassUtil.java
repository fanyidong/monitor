package cn.fyd.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static cn.fyd.common.Constant.TWO;

/**
 * 反射共用方法
 * @author fanyidong
 * @date Created in 2019-01-16
 */
public class ClassUtil {

    /**
     * 根据属性名获得get方法
     * @param fieldName 属性名
     * @return String类型的getter方法
     */
    public static String toGetter(String fieldName) {
        if (fieldName == null || fieldName.length() == 0) {
            return null;
        }
        // 如果第二个字母是大写 eBlog->geteBlog
        if (fieldName.length() > TWO) {
            String second = fieldName.substring(1, 2);
            if (second.equals(second.toUpperCase())) {
                return new StringBuffer("get").append(fieldName).toString();
            }
        }
        // 通常情况
        fieldName = new StringBuffer("get").append(fieldName.substring(0, 1).toUpperCase())
                .append(fieldName.substring(1)).toString();
        return  fieldName;
    }

    /**
     * 根据对象和属性名获取本对象对应属性的值
     * @param object 对象
     * @param fieldName 属性名
     * @return Object
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static Object getValueByFieldName(Object object, String fieldName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // 获得此类
        Class clazz = object.getClass();
        // 获取getter方法
        String getterMethod = ClassUtil.toGetter(fieldName);
        // 得到该方法
        Method method = clazz.getMethod(getterMethod);
        // 获取getter方法的返回内容
        Object value = method.invoke(object);
        return value;
    }
}
