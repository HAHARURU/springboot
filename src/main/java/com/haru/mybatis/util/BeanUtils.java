package com.haru.mybatis.util;

import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 对象操作工具类
 *
 * @author HARU
 * @date 2019/5/4
 **/
public class BeanUtils {

    /**
     * 得到类中字段名的集合
     *
     * @param object 需要得到的字段名的类
     * @return 字段集合
     */
    public static List<String> classFieldNames(Class object) {
        List<String> fieldList = new ArrayList<>();
        Field[] fields = object.getDeclaredFields();
        for (Field field : fields) {
            fieldList.add(field.getName());
        }
        return fieldList;
    }

    /**
     * 对象深拷贝，可以按具体字段；params长度为0时拷贝所有字段
     *
     * @param origin         源对象
     * @param deepCopyObject 深拷贝后的对象
     * @param fields         需拷贝的字段
     * @return 得到的深拷贝对象
     */
    public static void deepCopy(Object origin, Object deepCopyObject, List<String> fields) {
        long begin = System.currentTimeMillis();
        if (origin != null) {
            Class<?> originClass = origin.getClass();
            List<String> allFieldNames = fields.size() == 0 ? classFieldNames(originClass) : fields;
            for (String fieldNames : allFieldNames) {
                Field field = ReflectionUtils.findField(originClass, fieldNames);
                Assert.notNull(field, "字段为空");

                // 跳过序列化字段的serialVersionUID
                if ("serialVersionUID".equals(field.getName())) {
                    continue;
                }

                Field deepCopyField = ReflectionUtils.findField(deepCopyObject.getClass(), fieldNames);
                field.setAccessible(true);
                try {
                    Object originFieldObject = field.get(origin);
                    Object deepCopyFieldObject = deepCopyField(originFieldObject);
                    Assert.notNull(deepCopyField, "字段为空");
                    ReflectionUtils.setField(deepCopyField, deepCopyObject, deepCopyFieldObject);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            long end = System.currentTimeMillis();
            System.out.println(end - begin);
        }
    }

    /**
     * 对象流深拷贝字段
     *
     * @param fieldObject 需拷贝的字段对象
     * @return 深拷贝后的字段
     */
    private static Object deepCopyField(Object fieldObject) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(fieldObject);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
