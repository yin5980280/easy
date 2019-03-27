package cn.org.easysite.commons.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.IOException;
import java.util.Date;

import static cn.org.easysite.commons.utils.FormatUtils.LONG_DATE_FORMAT_STR;

public class JsonDateSerialize extends JsonSerializer<Date> {

    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        if (date == null) {
            jsonGenerator.writeString("");
        } else {
            jsonGenerator.writeString(DateFormatUtils.format(date, LONG_DATE_FORMAT_STR));
        }
    }
}
