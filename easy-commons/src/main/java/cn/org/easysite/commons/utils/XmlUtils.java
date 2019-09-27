package cn.org.easysite.commons.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;
import java.io.Writer;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

import static cn.org.easysite.commons.constants.BaseConstants.DEFAULT_CHARSET;

/**
 * @author panda
 */
@Slf4j
public class XmlUtils {

    private static final String PREFIX_CDATA = "<![CDATA[";

    private static final String SUFFIX_CDATA = "]]>";

    private static final ConcurrentHashMap<Class, XStream> XSTREAM_WRITER_MAP = new ConcurrentHashMap<>();

    private static final ConcurrentHashMap<Class, XStream> XSTREAM_READER_MAP = new ConcurrentHashMap<>();

    public static String object2Xml(Object obj) {
        return object2Xml(obj, true);
    }

    public static String object2Xml(Object obj, boolean useCdata) {
        if (null == obj) {
            return null;
        }
        Class clazz = obj.getClass();
        XStream xStream = getXStreamWriter(clazz, useCdata);
        xStream.processAnnotations(clazz);
        xStream.autodetectAnnotations(true);
        xStream.aliasSystemAttribute(null, "class");
        return xStream.toXML(obj);
    }

    public static <T> T xml2Object(String xml, Class<T> clazz) {
        if (StringUtils.isEmpty(xml)) {
            return null;
        }
        try {
            XStream xStream = getXStreamReader(clazz);
            xStream.processAnnotations(clazz);
            xStream.autodetectAnnotations(true);
            return clazz.cast(xStream.fromXML(xml));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 转XMLmap
     *
     * @author
     */
    public static Map<String, String> toMap(byte[] xmlBytes, String charset) throws Exception {
        SAXReader reader = new SAXReader(false);
        InputSource source = new InputSource(new ByteArrayInputStream(xmlBytes));
        source.setEncoding(charset);
        Document doc = reader.read(source);
        Map<String, String> params = XmlUtils.toMap(doc.getRootElement());
        return params;
    }
    /**
     * 转XMLmap
     *
     * @author
     */
    public static TreeMap<String, String> toTreeMap(byte[] xmlBytes, String charset) throws Exception {
        SAXReader reader = new SAXReader(false);
        InputSource source = new InputSource(new ByteArrayInputStream(xmlBytes));
        source.setEncoding(charset);
        Document doc = reader.read(source);
        TreeMap<String, String> params = XmlUtils.toTreeMap(doc.getRootElement());
        return params;
    }

    /**
     * 转MAP
     *
     * @author
     */
    public static Map<String, String> toMap(Element element) {
        Map<String, String> rest = new HashMap<String, String>();
        List<Element> els = element.elements();
        for (Element el : els) {
            rest.put(el.getName().toLowerCase(), el.getTextTrim());
        }
        return rest;
    }
    /**
     * 转MAP
     *
     * @author
     */
    public static TreeMap<String, String> toTreeMap(Element element) {
        TreeMap<String, String> rest = new TreeMap<String, String>();
        List<Element> els = element.elements();
        for (Element el : els) {
            rest.put(el.getName().toLowerCase(), el.getTextTrim());
        }
        return rest;
    }

    /**
     * Collect post form data
     */
    public static TreeMap<String, Object> parseXStream2Map(Object obj, boolean isEncodeKey) {
        TreeMap<String, Object> paramMap = new TreeMap<>();
        Collection<Field> fields = BeanUtil.getAllDeclaredFields(obj.getClass());
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(obj);
                if (null == value) {
                    continue;
                }
                XStreamAlias xStreamAlias = field.getAnnotation(XStreamAlias.class);
                String key;
                if (null == xStreamAlias) {
                    continue;
                } else {
                    key = xStreamAlias.value();
                    if (StringUtils.isEmpty(key)) {
                        key = field.getName();
                    }
                }
                if (isEncodeKey) {
                    String s = URLEncoder.encode(key, DEFAULT_CHARSET);
                    if (!key.equals(s)) {
                        log.warn("object to map encode filed name by URLEncode(filed name={})",
                                key);
                    }
                    key = s;
                }
                paramMap.put(key, field.get(obj));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return paramMap;
    }

    /**
     * 获取一个object转xml的XStream，如果缓存中存在则从缓存中获取，否则new一个并缓存起来
     */
    private static XStream getXStreamWriter(Class clazz) {
        XStream xStream = XSTREAM_WRITER_MAP.get(clazz);
        if (null == xStream) {
            xStream = createXStreamWriter(true);
            XSTREAM_WRITER_MAP.put(clazz, xStream);
        }
        return xStream;
    }

    private static XStream getXStreamWriter(Class clazz, boolean useCdata) {
        XStream xStream = XSTREAM_WRITER_MAP.get(clazz);
        if (null == xStream) {
            xStream = createXStreamWriter(useCdata);
            XSTREAM_WRITER_MAP.put(clazz, xStream);
        }
        return xStream;
    }


    /**
     * 获取一个xml转object的XStream，如果缓存中存在则从缓存中获取，否则new一个并缓存起来
     */
    private static XStream getXStreamReader(Class clazz) {
        XStream xStream = XSTREAM_READER_MAP.get(clazz);
        if (null == xStream) {
            xStream = createXStreamReader();
            XSTREAM_READER_MAP.put(clazz, xStream);
        }
        return xStream;
    }

    private static XStream createXStreamWriter(final boolean useCdata) {
        XStream xstream = new XStream(new XppDriver(new NoNameCoder()) {
            @Override
            public HierarchicalStreamWriter createWriter(Writer out) {
                //                return new CompactWriter(out);
                return new PrettyPrintWriter(out, new char[]{}, "") {
                    //                    boolean isBody = false;//是否为body字段，该字段可能含有需要特殊处理

                    @Override
                    @SuppressWarnings("rawtypes")
                    public void startNode(String name, Class clazz) {
                        super.startNode(name, clazz);
                    }

                    @Override
                    public String encodeNode(String name) {
                        return name;
                    }

                    @Override
                    protected void writeText(QuickWriter writer, String text) {
                        if (useCdata) {
                            writer.write(PREFIX_CDATA + text + SUFFIX_CDATA);
                        } else {
                            writer.write(text);
                        }
                    }
                };
            }
        });
        return xstream;
    }

    private static XStream createXStreamReader() {
        XStream xstream = new XStream(new XppDriver() {
            @Override
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new PrettyPrintWriter(out) {
                    @Override
                    protected void writeText(QuickWriter writer, String text) {
                        writer.write(PREFIX_CDATA + text + SUFFIX_CDATA);
                    }
                };
            }
        }) {
            @Override
            protected MapperWrapper wrapMapper(MapperWrapper next) {
                return new MapperWrapper(next) {
                    @Override
                    public boolean shouldSerializeMember(Class definedIn, String fieldName) {
                        if (definedIn == Object.class) {
                            return false;
                        }
                        return super.shouldSerializeMember(definedIn, fieldName);
                    }
                };
            }
        };
        return xstream;
    }
}
