package cn.org.easysite.commons.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

import cn.org.easysite.commons.utils.FormatUtils;

/**
 * @author panda
 * 该类表示为给bigDecimal转换为人民币形式转换，保留两位小数
 */
public class JsonBigDecimalSerializer extends JsonSerializer<BigDecimal> {
    @Override
    public void serialize(BigDecimal bigDecimal, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(FormatUtils.formatMoney(bigDecimal));
    }
}
