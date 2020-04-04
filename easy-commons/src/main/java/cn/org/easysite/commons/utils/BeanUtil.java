package cn.org.easysite.commons.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import static cn.org.easysite.commons.constants.BaseConstants.LONG_DATE_FORMAT_STR;
import static cn.org.easysite.commons.utils.DateUtils.LONG_DATE_FORMAT;

/**
 * <p>Title: BeanUtil</p> <p>Description: com.wqb.test</p> <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: easysite</p>
 * @author panda
 */
@Slf4j
public final class BeanUtil {
    private static final String JAVA_LANG_PACKAGE = "java.lang";

    /**
     * 复制资源Bean中的属性值到目标Bean中
     *
     * @param targetObj 目标Bean
     * @param sourceObj 资源Bean
     */
    public static Object copyProperties(Object targetObj, Object sourceObj) {
        if (null == targetObj || null == sourceObj) {
            return targetObj;
        }
        BeanUtils.copyProperties(sourceObj, targetObj, getNullPropertyNames(sourceObj));
        return targetObj;
    }

    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = beanWrapper.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = beanWrapper.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * 获取class本身及所有父类定义的所有属性(不重复)
     *
     * @param clazz
     * @return
     */
    public static Collection<Field> getAllDeclaredFields(Class clazz) {
        if (String.class == clazz) {
            return Collections.EMPTY_LIST;
        }
        Class cls = clazz;
        HashMap<String, Field> fieldMap = new HashMap<>();
        String key;
        while (!cls.getName().startsWith(JAVA_LANG_PACKAGE)) {
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                key = field.getName();
                if (fieldMap.containsKey(key)) {
                    continue;
                }
                fieldMap.put(key, field);
            }
            cls = cls.getSuperclass();
        }
        return fieldMap.values();
    }

    /**
     * Bean转换为Map格式
     */
    @SuppressWarnings("all")
    public static Map<String, Object> toMap(Object bean) {
        if (bean == null) {
            return null;
        }
        if (Map.class.isAssignableFrom(bean.getClass())) {
            return (Map<String, Object>) bean;
        }
        Collection<Field> fields = getAllDeclaredFields(bean.getClass());
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        try {
            for (Field field : fields) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    field.setAccessible(true);
                    map.put(field.getName(), field.get(bean));
                }
            }
        } catch (Exception e) {
            log.error("exception:", e);
        }
        return map;
    }

    /**
     * Bean转换为Map格式 忽略NULL
     */
    @SuppressWarnings("all")
    public static Map<String, Object> toMapIgnoreNULL(Object bean) {
        if (bean == null) {
            return null;
        }
        if (Map.class.isAssignableFrom(bean.getClass())) {
            return (Map<String, Object>) bean;
        }
        Collection<Field> fields = getAllDeclaredFields(bean.getClass());
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                if (!Modifier.isStatic(field.getModifiers()) && field.get(bean) != null) {
                    map.put(field.getName(), field.get(bean));
                }
            }
        } catch (Exception e) {
            log.error("exception:", e);
        }
        return map;
    }

    /**
     * 设置javabean中非static、非final的对象属性的值为null
     *
     * @param bean
     */
    public static void setField2Null(Object bean) {
        try {
            Collection<Field> fields = BeanUtil.getAllDeclaredFields(bean.getClass());
            for (Field field : fields) {
                int modifier = field.getModifiers();
                if (Modifier.isStatic(modifier) || Modifier.isFinal(modifier)) {
                    continue;
                }
                if (Object.class.isAssignableFrom(field.getType())) {
                    field.setAccessible(true);
                    field.set(bean, null);
                }
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
    }

    /**
     * 根据名称递归获取class中定义的字段
     *
     * @param clazz
     * @param name
     * @return
     */
    public static Field getFieldRecursion(Class clazz, String name) {
        if (clazz.getName().startsWith(JAVA_LANG_PACKAGE) || StringUtils.isBlank(name)) {
            return null;
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (name.equals(field.getName())) {
                return field;
            }
        }
        return BeanUtil.getFieldRecursion(clazz.getSuperclass(), name);
    }

    /**
     * 根据class中的属性名获取对应的json字段名，没有JsonProperty时，直接返回name
     *
     * @param clazz
     * @param name
     * @return
     */
    public static String getJsonColumnName(Class clazz, String name) {
        Field field = BeanUtil.getFieldRecursion(clazz, name);
        if (null == field) {
            log.error("尝试获取json字段名出错,class={},feildName={}", clazz, name);
            return name;
        }
        return BeanUtil.getJsonColumnName(field, name);
    }

    /**
     * 根据属性Field获取对应的json字段名，没有JsonProperty时，直接返回fieldName
     *
     * @param field
     * @param name
     * @return
     */
    public static String getJsonColumnName(Field field, String name) {
        if (null == field) {
            return name;
        }
        JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
        if (null == jsonProperty) {
            return name;
        }
        String jsonName = jsonProperty.value();
        if (StringUtils.isBlank(jsonName)) {
            return name;
        }
        return jsonName;
    }

    /**
     * 获取对象中指定属性的值，屏蔽异常
     *
     * @param obj
     * @param field
     * @return Object
     */
    public static Object getFieldValue(Object obj, Field field) {
        if (null == field) {
            return null;
        }
        Object value = null;
        try {
            field.setAccessible(true);
            value = field.get(obj);
        } catch (Exception e) {
            log.error("获取{}.{}的值出错,{}", obj.getClass(), field.getName(), e.getLocalizedMessage());
        }
        return value;
    }

    /**
     * 获取对象中指定属性的值，屏蔽异常
     *
     * @param obj
     * @param fieldName
     * @return Object
     */
    public static Object getFieldValueByName(Object obj, String fieldName) {
        Field field = getFieldRecursion(obj.getClass(), fieldName);

        return getFieldValue(obj, field);
    }

    /**
     * 设置字符串值到对象的指定字段（会将字符串转换为目标字段的类型）
     *
     * @param obj
     * @param field
     * @param value
     */
    public static void setFieldValue(Object obj, Field field, Object value) {
        try {
            Object objValue = convertToBaseType(value, field.getType());
            if (null == objValue) {
                return;
            }
            field.setAccessible(true);
            field.set(obj, objValue);
        } catch (Exception e) {
            log.error("设置数据失败，fieldName:{}，fieldValue:{}，{}", field.getName(), value, e.getLocalizedMessage());
        }
    }

    /**
     * 未知对象转基本类型值
     *
     * @param value
     * @param clazz
     * @return Object
     */
    public static Object convertToBaseType(Object value, Class clazz) {
        if (value == null || null == clazz) {
            return null;
        }
        if (clazz.isAssignableFrom(value.getClass())) {
            return value;
        }
        return BaseTypeHandlerFactory.getBaseTypeHandler(clazz.getTypeName()).handler(String.valueOf(value));
    }
    /**
     * 处理基础类型factory
     */
    private static class BaseTypeHandlerFactory {

        /**
         * TypeHandler缓存
         */
        private static final Map<Class<?>, BaseTypeHandler> BASE_HANDLER_CACHE;

        static {
            BASE_HANDLER_CACHE = new HashMap<>();
            //注册int
            BASE_HANDLER_CACHE.put(int.class, new AbstractNumberBaseTypeHandler<Integer>() {
                @Override
                protected Integer handle(Number value) {
                    return value.intValue();
                }
            });
            BASE_HANDLER_CACHE.put(Integer.class, BASE_HANDLER_CACHE.get(int.class));

            //注册long
            BASE_HANDLER_CACHE.put(long.class, new AbstractNumberBaseTypeHandler<Long>() {
                @Override
                protected Long handle(Number value) {
                    return value.longValue();
                }
            });
            BASE_HANDLER_CACHE.put(Long.class, BASE_HANDLER_CACHE.get(long.class));

            //float
            BASE_HANDLER_CACHE.put(float.class, new AbstractNumberBaseTypeHandler<Float>() {
                @Override
                protected Float handle(Number value) {
                    return value.floatValue();
                }
            });
            BASE_HANDLER_CACHE.put(Float.class, BASE_HANDLER_CACHE.get(float.class));

            //注册double
            BASE_HANDLER_CACHE.put(double.class, new AbstractNumberBaseTypeHandler<Double>() {
                @Override
                protected Double handle(Number value) {
                    return value.doubleValue();
                }
            });
            BASE_HANDLER_CACHE.put(Short.class, BASE_HANDLER_CACHE.get(float.class));

            //注册double
            BASE_HANDLER_CACHE.put(short.class, new AbstractNumberBaseTypeHandler<Short>() {
                @Override
                protected Short handle(Number value) {
                    return value.shortValue();
                }
            });
            BASE_HANDLER_CACHE.put(Short.class, BASE_HANDLER_CACHE.get(short.class));

            //注册byte
            BASE_HANDLER_CACHE.put(byte.class, Byte::valueOf);
            BASE_HANDLER_CACHE.put(Byte.class, BASE_HANDLER_CACHE.get(byte.class));

            //注册char
            BASE_HANDLER_CACHE.put(char.class, value -> value.charAt(0));
            BASE_HANDLER_CACHE.put(Character.class, BASE_HANDLER_CACHE.get(char.class));

            //注册boolean
            BASE_HANDLER_CACHE.put(boolean.class, Boolean::valueOf);
            BASE_HANDLER_CACHE.put(Boolean.class, BASE_HANDLER_CACHE.get(boolean.class));

            //注册BigDecimal
            BASE_HANDLER_CACHE.put(BigDecimal.class, BigDecimal::new);

            //注册timestamp
            BASE_HANDLER_CACHE.put(Timestamp.class, value -> {
                if (NumberUtils.isCreatable(value)) {
                    return new Timestamp(NumberUtils.createLong(value));
                }
                try {
                    return LONG_DATE_FORMAT.get().parse(value);
                } catch (ParseException pe) {
                    log.error("反序列化[" + value + "]为Timestamp类型时出错");
                }
                return null;
            });

            //注册Date
            BASE_HANDLER_CACHE.put(Date.class, BASE_HANDLER_CACHE.get(Timestamp.class));
        }

        /**
         * 基础类型处理Handler
         */
        private interface BaseTypeHandler<T> {
            /**
             * 返回基本类型
             *
             * @param value 字符串值
             * @return 值对象
             */
            T handler(String value);
        }

        /**
         * 数值实现类
         */
        private abstract static class AbstractNumberBaseTypeHandler<T extends Number> implements BaseTypeHandler {
            @Override
            public Number handler(String value) {
                if (!NumberUtils.isCreatable(value)) {
                    return 0;
                }
                Number number = NumberUtils.createNumber(value);
                return handle(number);
            }

            /**
             * 实现类处理
             */
            protected abstract T handle(Number value);
        }

        private static BaseTypeHandler getBaseTypeHandler(String typeName) {
            for (Class clazz : BASE_HANDLER_CACHE.keySet()) {
                if (isClassName(typeName, clazz)) {
                    return BASE_HANDLER_CACHE.get(clazz);
                }
            }
            return value -> value;
        }
    }

    /**
     * 判断class名字是否与被判断的值相等
     * @param name
     * @param clazz
     * @return
     */
    public static boolean isClassName(String name, Class clazz) {
        return name.equalsIgnoreCase(clazz.getSimpleName()) || name.equalsIgnoreCase(clazz.getName());
    }

    /**
     * 解析时间字符串为毫秒数
     *
     * @param dateTime
     * @return
     */
    public static long parseTimeStr2ms(String dateTime) throws ParseException {
        if (StringUtils.isBlank(dateTime)) {
            return 0;
        }
        if (NumberUtils.isCreatable(dateTime)) {
            return Long.parseLong(dateTime);
        }
        return org.apache.commons.lang3.time.DateUtils.parseDate(dateTime, LONG_DATE_FORMAT_STR).getTime();
    }
}
