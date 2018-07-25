package com.haru.mybatis.util;

import com.google.gson.*;
import com.haru.mybatis.enumPackage.ErrorEnum;
import com.haru.mybatis.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * @author HARU
 * @since 2018/5/22
 * <p>
 * Gson解析
 */
public class GsonView extends AbstractView {
    static Logger logger = LoggerFactory.getLogger(GsonView.class);

    /**
     * Date / Enum 等
     */
    public static void regGson(GsonBuilder gb) {
        regSimpleTypes(gb);
    }


    public static void regSimpleTypes(GsonBuilder gb) {
        /* 通用的Timestamp转换 */
        class TimestampSerializer implements JsonSerializer<Timestamp>, JsonDeserializer<Timestamp> {
            SimpleDateFormat shortFmt = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat hourFmt = new SimpleDateFormat("yyyy-MM-dd HH");
            SimpleDateFormat minuteFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            SimpleDateFormat longFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            //fromJSON格式化时间
            @Override
            public synchronized Timestamp deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                String str = json.getAsString();
                if (StringUtils.isEmpty(str)) {
                    return null;
                }

                SimpleDateFormat dateFmt = new SimpleDateFormat();
                int length = str.length();
                switch (length) {
                    case 10:
                        dateFmt = shortFmt;
                        break;
                    case 13:
                        dateFmt = hourFmt;
                        break;
                    case 16:
                        dateFmt = minuteFmt;
                        break;
                    case 19:
                        dateFmt = longFmt;
                        break;
                    default:
                        logger.error("Timestamp长度不支持Json解析：" + str);
                        throw new CustomException("Timestamp长度不支持Json解析：" + str, String.valueOf(ErrorEnum.Timestamp长度不支持Json解析.getValue()));
                }
                try {
                    return new Timestamp(dateFmt.parse(str).getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                    logger.error("TimestampJson解析失败：" + e.getMessage());
                    throw new CustomException("TimestampJson解析失败：" + e.getMessage(), String.valueOf(ErrorEnum.TimestampJson解析失败.getValue()));
                }
            }

            //toJSON格式化时间
            @Override
            public synchronized JsonElement serialize(Timestamp src, Type typeOfSrc, JsonSerializationContext context) {
                if (src != null) {
                    String s = longFmt.format(src);
                    return new JsonPrimitive(s);
                } else
                    return null;
            }

        }
        gb.registerTypeAdapter(Timestamp.class, new TimestampSerializer());

        /* 通用的枚举转换 */
        class EnumSerializer implements JsonSerializer<Enum>, JsonDeserializer<Enum> {

            public JsonElement serialize(Enum src, Type t, JsonSerializationContext context) {
                Class enumCls = ((Class) t);
                return new JsonPrimitive(src.toString());
            }

            public Enum deserialize(JsonElement j, Type t, JsonDeserializationContext c) throws JsonParseException {
                Class enumCls = ((Class) t);
                if (((JsonPrimitive) j).isNumber()) {
                    int value = j.getAsInt();
                    return EnumUtils.getEnum(enumCls, value);
                } else {
                    String text = j.getAsString();
                    return EnumUtils.getEnum(enumCls, text);
                }
            }

        }
        gb.registerTypeHierarchyAdapter(Enum.class, new EnumSerializer());
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

    }
}
