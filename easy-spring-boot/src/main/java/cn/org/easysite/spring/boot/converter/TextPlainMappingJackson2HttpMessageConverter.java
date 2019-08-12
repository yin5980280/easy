package cn.org.easysite.spring.boot.converter;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * 针对返回类型是text/plain类型的结果返回是json增加解析
 * @author yinlin
 */
public class TextPlainMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

    public TextPlainMappingJackson2HttpMessageConverter() {
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.TEXT_PLAIN);
        mediaTypes.add(MediaType.TEXT_HTML);
        setSupportedMediaTypes(mediaTypes);
    }

}
