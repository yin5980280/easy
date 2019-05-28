package cn.org.easysite.commons.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liangb
 * @version 1.0
 * @date 16/7/4 下午4:19
 */
@Slf4j
public class MapUtils {

    public static TreeMap<String, Object> map2ObjectTreeMap(Map<String, String> map) {
        TreeMap<String, Object> treeMap = new TreeMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String value = entry.getValue();
            if (StringUtils.isEmpty(value)) {
                continue;
            }
            treeMap.put(entry.getKey(), value);
        }
        return treeMap;
    }

    public static TreeMap<String, String> map2StringTreeMap(Map<String, Object> map) {
        TreeMap<String, String> treeMap = new TreeMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String value;
            Object obj = entry.getValue();
            if (null == obj) {
                continue;
            }
//            if (obj instanceof Date) {
//                value = String.valueOf(((Date)obj).getTime());
//            } else {
//                value = obj.toString();
//            }
            if (obj instanceof String) {
                value = (String) obj;
            } else {
                value = obj.toString();
            }
            if (StringUtils.isEmpty(value)) {
                continue;
            }
            treeMap.put(entry.getKey(), value);
        }
        return treeMap;
    }

    public static TreeMap<String, String> toStringTreeMap(Object obj) {
        Map<String, Object> newMap = toTreeMap(obj, true);
        return map2StringTreeMap(newMap);
    }

    public static TreeMap<String, Object> toTreeMap(Object obj, boolean isFilterNull) {
        return obj2Map(obj, isFilterNull, false);
    }

    public static TreeMap<String, Object> toJsonMap(Object obj) {
        return obj2Map(obj, true, true);
    }

    public static TreeMap<String, String> toStringJsonMap(Object obj) {
        Map<String, Object> map = obj2Map(obj, true, true);
        return map2StringTreeMap(map);
    }

    private static TreeMap<String, Object> obj2Map(Object obj, boolean isFilterNull, boolean isJson) {
        TreeMap<String, Object> map = new TreeMap<>();
        if (obj == null) {
            return map;
        }
        try {
            Class clazz = obj.getClass();
            BeanInfo e = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] properties = e.getPropertyDescriptors();
            for (int i = 0; i < properties.length; i++) {
                PropertyDescriptor property = properties[i];
                String key = property.getName();
                if (key.equals("class")) {
                    continue;
                }
                Method getter = property.getReadMethod();
                Object value = getter.invoke(obj, new Object[0]);
                if (isFilterNull && value == null) {
                    continue;
                }
//                Class vClazz = value.getClass();
//                if (!vClazz.isAssignableFrom(valueClazz)) {
//                    throw new RuntimeException("属性[" + key + "]的类型[" + vClazz.getTypeName()
//                            + "]跟期望的类型[" + valueClazz.getTypeName() + "]不匹配");
//                }
                if (isJson) {
                    key = BeanUtil.getJsonColumnName(clazz, key);
                }
                map.put(key, value);
            }
        } catch (Exception e) {
            log.error("object to json map error", e);
        }
        return map;
    }

    /**
     * 将TreeMap中所有参数按升序转换成字符串，格式：key1=value1&key2=value2
     *
     * @param map
     * @return
     */
    public static String treeMap2ascString(Map<String, Object> map) {
        return treeMap2ascString(map, null);
    }

    /**
     * 将TreeMap中所有参数按升序转换成字符串，格式：key1=value1&key2=value2
     *
     * @param map
     * @param charset 编码，为空则不编码
     * @return
     */
    public static String treeMap2ascString(Map<String, Object> map, String charset) {
        return treeMap2ascString(map, charset, '&');
    }

    /**
     * 将TreeMap中所有参数按升序转换成字符串
     *
     * @param map
     * @param charset 编码，为空则不编码
     * @param link    连接符
     * @return
     */
    public static String treeMap2ascString(Map<String, Object> map, String charset, Character link) {
        StringBuilder sb = new StringBuilder();
        boolean isEncode = StringUtils.isNotBlank(charset);//是否url编码value
        boolean isLink = null != link;
        try {
            Iterator<Map.Entry<String, Object>> iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, Object> entry = iter.next();
                String value;
                Object obj = entry.getValue();
                if (null == obj) {
                    continue;
                }
//                if (obj instanceof Date) {
//                    value = String.valueOf(((Date)obj).getTime());
//                } else {
                value = obj.toString();
//                }
                if (StringUtils.isEmpty(value)) {
                    continue;
                }
                sb.append(entry.getKey()).append("=").append(isEncode ? URLEncoder.encode(value, charset) : value);
                if (isLink) {
                    sb.append(link);
                }
            }
            int n = sb.length() - 1;
            if (isLink && n >= 0 && sb.charAt(n) == link.charValue()) {
                sb.deleteCharAt(n);
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return sb.toString();
    }

    /**
     * 将map中所有不为空的键值对转换为url参数形式的字符串
     *
     * @param map
     * @param charset 编码，为空则不编码
     * @return StringBuilder
     */
    public static StringBuilder map2StringBuilder(Map<String, String> map, String charset) {
        StringBuilder sb = new StringBuilder();
        if (map.isEmpty()) {
            return sb;
        }
        boolean isEncode = StringUtils.isNotBlank(charset);//是否url编码value
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        Map.Entry<String, String> entry;
        String value;
        try {
            //处理第一个不为空的键值对
            while (it.hasNext()) {
                entry = it.next();
                value = entry.getValue();
                if (StringUtils.isEmpty(value)) {
                    continue;
                }
                sb.append(entry.getKey()).append("=").append(isEncode ? URLEncoder.encode(value, charset) : value);
                break;
            }
            //处理后面的键值对
            while (it.hasNext()) {
                entry = it.next();
                value = entry.getValue();
                if (StringUtils.isEmpty(value)) {
                    continue;
                }
                sb.append("&").append(entry.getKey()).append("=").append(isEncode ? URLEncoder.encode(value, charset) : value);
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return sb;
    }

    public static <T> T mapToObject(Map<String, Object> map, Class<T> beanClass) {
        if (map == null) {
            return null;
        }
        T obj = null;
        //移除空值，防止报错
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            if (null == entry.getValue()) {
                it.remove();
            }
        }
        try {
            obj = beanClass.newInstance();
            BeanUtils.populate(obj, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 解析map中的数据到对象中
     *
     * @param map
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T stringMapToObject(Map<String, String> map, Class<T> clazz) {
        Collection<Field> fields = BeanUtil.getAllDeclaredFields(clazz);
        T t;
        try {
            t = clazz.newInstance();
        } catch (Exception e) {
            log.error("stringMapToObject出错", e);
            return null;
        }
        for (Field field : fields) {
            String value = map.get(field.getName());
            BeanUtil.setFieldValue(t, field, value);
        }
        return t;
    }

    /**
     * 解析map中的数据到json对象中(可能会根据JsonProperty进行转换)
     *
     * @param requestMap
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T mapToJsonObject(Map<String, String> requestMap, Class<T> clazz) {
        Collection<Field> fields = BeanUtil.getAllDeclaredFields(clazz);
        T t;
        try {
            t = clazz.newInstance();
        } catch (Exception e) {
            log.error("map to java bean error", e);
            return null;
        }
        for (Field field : fields) {
            String mappingName = BeanUtil.getJsonColumnName(field, field.getName());
            String value = requestMap.get(mappingName);
            BeanUtil.setFieldValue(t, field, value);
        }
        return t;
    }

    /**
     * 解析map中的数据到json对象中(可能会根据JsonProperty进行转换)
     *
     * @param requestMap
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T objectMapToJsonObject(Map<String, Object> requestMap, Class<T> clazz) {
        Collection<Field> fields = BeanUtil.getAllDeclaredFields(clazz);
        T t;
        try {
            t = clazz.newInstance();
        } catch (Exception e) {
            log.error("map to java bean error", e);
            return null;
        }
        for (Field field : fields) {
            String mappingName = BeanUtil.getJsonColumnName(field, field.getName());
            Object value = requestMap.get(mappingName);
            BeanUtil.setFieldValue(t, field, value);
        }
        return t;
    }

    public static Map<String, String> encodeTreeMap(Map<String, String> map, String charset) {
        Map<String, String> newMap = new TreeMap<String, String>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            try {
                String value = URLEncoder.encode(entry.getValue(), charset);
                newMap.put(entry.getKey(), value);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return newMap;
    }
}
