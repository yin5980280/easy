package cn.org.easysite.commons.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonMapper {
    public static ObjectMapper MAPPER = nonNullMapper();
    private static TypeFactory typeFactory = MAPPER.getTypeFactory();

    public static ObjectMapper getMapper(JsonInclude.Include include) {
        ObjectMapper mapper = new ObjectMapper();
        // 设置输出时包含属性的风格
        if (include != null) {
            mapper.setSerializationInclusion(include);
        }
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //增加转义符支持
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        //日期类型转换为时间戳
        SimpleModule module = new SimpleModule("DatesModule", new Version(0, 1, 0, "alpha", "com.keruyun", "kds"));
        module.addSerializer(Date.class, new DateTimeSerializer());
        mapper.registerModule(module);
        return mapper;
    }

    public static class DateTimeSerializer extends JsonSerializer<Date> {
        @Override
        public void serialize(
                Date value,
                JsonGenerator jgen,
                SerializerProvider provider) throws IOException {
            if (null == jgen) {
                return;
            }
            if (value == null) {
                jgen.writeNull();
            } else {
                jgen.writeNumber(value.getTime());
            }
        }
    }

    /**
     * 创建只输出非Null且非Empty(如List.isEmpty)的属性到Json字符串的Mapper,建议在外部接口中使用.
     */
//    public static ObjectMapper nonEmptyMapper() {
//        return JsonMapper.getMapper(JsonInclude.Include.NON_EMPTY);
//    }

    /**
     * 创建只输出初始值被改变的属性到Json字符串的Mapper, 最节约的存储方式，建议在内部接口中使用。
     */
    public static ObjectMapper nonNullMapper() {
        return JsonMapper.getMapper(JsonInclude.Include.NON_NULL);
    }

    public static String toJson(Object obj) {
        if (null == obj) {
            return null;
        }
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException jpe) {
            log.error("object to json error ", jpe);
        }
        return null;
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        if (null == json) {
            return null;
        }
        try {
            return MAPPER.readValue(json, contructType(clazz));
        } catch (IOException ioe) {
            log.error("json to object ", ioe);
        }
        return null;
    }

    public static <T> T fromJson(String json, JavaType javaType) {
        if (null == json) {
            return null;
        }
        try {
            return MAPPER.readValue(json, javaType);
        } catch (IOException ioe) {
            log.error("json to object ", ioe);
        }
        return null;
    }

    public static JavaType contructType(Class clazz) {
        return typeFactory.constructType(clazz);
    }

    public static JavaType contructType(Class clazz, Class elementClass) {
        return typeFactory.constructParametrizedType(clazz, clazz, elementClass);
    }

    public static JavaType contructType(Class clazz, JavaType elementClass) {
        return typeFactory.constructParametrizedType(clazz, clazz, elementClass);
    }

    /**
     * 构造Collection类型.
     */
    public static JavaType contructCollectionType(Class<? extends Collection> collectionClass, Class<?> elementClass) {
        return typeFactory.constructCollectionType(collectionClass, elementClass);
    }

    /**
     * 构造Map类型.
     */
    public static JavaType contructMapType(Class<? extends Map> mapClass, Class<?> keyClass, Class<?> valueClass) {
        return typeFactory.constructMapType(mapClass, keyClass, valueClass);
    }
}
