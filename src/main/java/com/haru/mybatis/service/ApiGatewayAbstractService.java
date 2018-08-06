package com.haru.mybatis.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.haru.mybatis.exception.AccessApiGatewayException;
import com.haru.mybatis.exception.ClassNotFoundApiGatewayException;
import com.haru.mybatis.exception.CustomException;
import com.haru.mybatis.exception.MethodNotFoundApiGatewayException;
import com.haru.mybatis.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;
import sun.reflect.generics.reflectiveObjects.TypeVariableImpl;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author HARU
 * @since 2018/8/2
 */
public abstract class ApiGatewayAbstractService {
    private static Logger logger = LoggerFactory.getLogger(ApiGatewayAbstractService.class);

    private static Map<String, Class> REMOTE_INTERFACE_CACHE = new HashMap<>();

    private Gson gson;

    private Gson getGson() {
        if (gson == null) {
            GsonBuilder gb = new GsonBuilder();
            GsonView.regGson(gb);
            gson = gb.create();
        }
        return gson;
    }

    public ApiGatewayAbstractService() {
        init();
    }

    private void init() {
        REMOTE_INTERFACE_CACHE.clear();
        List<Class> clazzs = PackageScanUtils.scanClassName(getScanClassName());
        if (0 == clazzs.size()) {
            return;
        }
        for (Class clazz : clazzs) {
            String[] split = clazz.getName().split("\\.");
            REMOTE_INTERFACE_CACHE.put(split[split.length - 1], clazz);
        }
    }

    /**
     * 服务接口执行
     *
     * @param serviceName 服务名
     * @param methodName  方法名
     * @param request     参数
     * @return 执行结果
     */
    public Object executeService(String serviceName, String methodName, HttpServletRequest request) throws
            ClassNotFoundApiGatewayException, MethodNotFoundApiGatewayException, InvocationTargetException,
            AccessApiGatewayException, UnsupportedEncodingException {
        logger.info("ServiceName: {}, MethodName: {}", serviceName, methodName);
        Object serviceInvoker = ApplicationUtil.getBean(serviceName);
        request.setCharacterEncoding("UTF-8");
        Method method;
        String realTypeName = null;
        try {
            Class interfaceClass = resolveInterfaceByServiceName(serviceName);
            method = getMethod(methodName, interfaceClass);
            if (null == method) {
                logger.warn("接口方法不存在。");
                throw new MethodNotFoundApiGatewayException("接口方法不存在。");
            }

            // 判断方法中有没有泛型
            if (isMethodGenericType(method)) {
                realTypeName = getRealTypeName(interfaceClass, method);
            }
        } catch (ClassNotFoundApiGatewayException e) {
            logger.warn("类不存在");
            throw new ClassNotFoundApiGatewayException(e.getMsg(), e);
        }

        List<Object> objParams = resolveMethodParameters(method, request, realTypeName);
        Object exeResult;
        try {
            logger.info("objParams:" + objParams);
            exeResult = method.invoke(serviceInvoker, objParams.toArray(new Object[0]));
        } catch (IllegalAccessException e) {
            logger.error("反射接口异常", e);
            throw new AccessApiGatewayException("反射接口出错", e);
        } catch (InvocationTargetException e) {
            throw e;
        } catch (Exception e) {
            CustomException exception = getFromStack(CustomException.class, e);
            logger.error("网关调用异常" + (exception == null ? "" : exception.getMessage()), e);
            throw exception;
        }
        return exeResult;
    }

    private Class resolveInterfaceByServiceName(String serviceName) throws ClassNotFoundApiGatewayException {
        Class serviceClz = REMOTE_INTERFACE_CACHE.get(serviceName);
        if (null != serviceClz) {
            return serviceClz;
        }
        List<Class> clazzs = PackageScanUtils.scanClassName(getScanClassName());
        if (0 == clazzs.size()) {
            throw new ClassNotFoundApiGatewayException("没有该类");
        }
        Class clz = null;
        for (Class clazz : clazzs) {
            String[] split = clazz.getName().split("\\.");
            if (split[split.length - 1].equals(serviceName)) {
                clz = clazz;
                REMOTE_INTERFACE_CACHE.put(split[split.length - 1], clazz);
            }
        }
        if (null == clz) {
            throw new ClassNotFoundApiGatewayException("找不到调用接口类。");
        }
        return clz;

    }

    public static <T> T getFromStack(Class<T> destCls, Throwable t) {
        logger.info(destCls.getSimpleName());
        while (t != null) {
            if (TypeUtils.isAssignable(t.getClass(), destCls)) {
                return (T) t;
            }
            t = t.getCause();
        }
        return null;
    }

    public static Method getMethod(String methodName, Class interfaceClass) {
        Method method = null;
        Method[] methods = interfaceClass.getDeclaredMethods();
        for (Method m : methods) {
            if (methodName.equals(m.getName())) {
                method = m;
            }
        }
        if (method != null)
            return method;
        if (interfaceClass.getInterfaces() != null && interfaceClass.getInterfaces().length > 0) {
            for (Class ifCls : interfaceClass.getInterfaces()) {
                method = getMethod(methodName, ifCls);
                if (method != null)
                    return method;
            }
        }
        return method;
    }

    private boolean isMethodGenericType(Method method) {
        Parameter[] parameters = method.getParameters();
        if (null == parameters || 0 == parameters.length) {
            return false;
        }
        for (Parameter p : parameters) {
            if (isArrayGenericType(p) || isNormalGenericType(p)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断数组是否是泛型
     *
     * @param parameter
     * @return
     */
    private boolean isArrayGenericType(Parameter parameter) {
        Type type = parameter.getParameterizedType();
        if (type instanceof GenericArrayType) {
            return true;
        }
        return false;
    }

    /**
     * 判断数组外参数是否是泛型
     *
     * @param parameter
     * @return
     */
    private boolean isNormalGenericType(Parameter parameter) {
        Type type = parameter.getParameterizedType();
        if (type instanceof TypeVariableImpl) {
            return true;
        }
        return false;
    }

    /**
     * 获取真正的泛型类型
     *
     * @param clz
     * @param method
     * @return
     */
    private String getRealTypeName(Class clz, Method method) {
        Type[] gpTypes = method.getGenericParameterTypes();
        Type realType = null;
        if (gpTypes.length > 0) {
            if (null == clz.getGenericInterfaces() || 0 == clz.getGenericInterfaces().length) {
                return "";
            }
            ParameterizedTypeImpl pi = (ParameterizedTypeImpl) clz.getGenericInterfaces()[0];
            Type[] types = pi.getActualTypeArguments();
            for (Type gpType : gpTypes) {
                TypeVariableImpl gpRawType = null;
                if (gpType instanceof GenericArrayType) {
                    gpRawType = (TypeVariableImpl) ((GenericArrayType) gpType).getGenericComponentType();
                } else if (gpType instanceof TypeVariableImpl) {
                    gpRawType = (TypeVariableImpl) gpType;
                }
                if (gpRawType != null) {
                    String gpTypeName = gpRawType.getName();//pk
                    GenericDeclaration gpDcls = gpRawType.getGenericDeclaration();
                    TypeVariable[] superMethodTypes = gpDcls.getTypeParameters();
                    for (int i = 0; i < superMethodTypes.length; i++) {
                        if (superMethodTypes[i].getName().equals(gpTypeName)) {
                            realType = types[i];
                            break;
                        }
                    }
                }
            }
        }
        return realType.getTypeName();
    }

    private List<Object> resolveMethodParameters(Method method, HttpServletRequest request, String realTypeName) {
        List<Object> paramsObj = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        if (0 < parameters.length) {
            for (int i = 0; i < parameters.length; i ++) {
                Parameter parameter = parameters[i];
                paramsObj.add(parseSingleParam(parameter, request, realTypeName));
            }
        }
        return paramsObj;
    }

    /**
     * 转换参数类型，以Object返回
     *
     * @param parameter
     * @param request
     * @param realTypeName
     * @return
     */
    private Object parseSingleParam(Parameter parameter, HttpServletRequest request, String realTypeName) {
        Object v;
        // 是否为数组参数
        if (parameter.getType().isArray()) {
            String[] requestValue = request.getParameterValues(parameter.getName());
            if (null == requestValue || 0 == requestValue.length) {
                requestValue = request.getParameterValues(parameter.getName() + "[]");
            }
            v = parseSingleArrayParam(parameter, requestValue, realTypeName);
        } else if (MultipartFile.class.isAssignableFrom(parameter.getType()) && request instanceof MultipartHttpServletRequest) {
            MultiValueMap<String, MultipartFile> fileMap = ((MultipartHttpServletRequest) request).getMultiFileMap();
            v = null == fileMap ? null : (fileMap.get(parameter.getName()) == null ? null : new BytesMultipartFile(fileMap.get(parameter.getName()).get(0)));
        } else {
            v = parseSingleNormalParam(parameter, request.getParameter(parameter.getName()), realTypeName);
        }
        return v;
    }

    /**
     * 转换非数组类型
     *
     * @param parameter
     * @param reqParam
     * @param realTypeName
     * @return
     */
    private Object parseSingleNormalParam(Parameter parameter, String reqParam, String realTypeName) {
        Object v = null;
        if (StringUtils.isBlank(reqParam)) {
            return v;
        }
        String param = transCode(reqParam);
        // 是否是泛型
        if (isNormalGenericType(parameter)) {
            try {
                // 取得泛型真正类型
                v = parseSingleParamWithType(Class.forName(realTypeName), param);
            } catch (ClassNotFoundException e) {
                logger.warn("泛型类型不存在");
                throw new RuntimeException("泛型类型不存在");
            }
        } else {
            v = parseSingleParamWithType(parameter.getType(), param);
        }
        return v;
    }

    /**
     * 转换数组类型
     *
     * @param parameter
     * @param reqParams
     * @return
     */
    private Object parseSingleArrayParam(Parameter parameter, String[] reqParams, String realTypeName) {
        Object v = null;
        if (null == reqParams || 0 == reqParams.length) {
            return v;
        }
        // 判断是否泛型
        if (isArrayGenericType(parameter)) {
            try {
                Class clz = Class.forName(realTypeName);
                Object ary = Array.newInstance(clz, reqParams.length);
                for (int i = 0; i < reqParams.length; i++) {
                    Array.set(ary, i, parseSingleParamWithType(clz, reqParams[i]));
                }
                v = ary;
            } catch (ClassNotFoundException e) {
                logger.warn("泛型类型不存在");
                throw new RuntimeException("泛型类型不存在");
            }
        } else {
            Class clz = parameter.getType().getComponentType();
            Object ary = Array.newInstance(clz, reqParams.length);
            for (int i = 0; i < reqParams.length; i++) {
                Array.set(ary, i, parseSingleParamWithType(clz, reqParams[i]));
            }
            v = ary;
        }
        return v;
    }

    public static String transCode(String param) {
        //解决参数中文乱码
        String val = "";
        try {
            if (null != param && param.equals(new String(param.getBytes("ISO-8859-1"), "ISO-8859-1"))) {
                val = new String(param.getBytes("ISO-8859-1"), "UTF-8");
            } else {
                val = param;
            }
        } catch (UnsupportedEncodingException e) {
            logger.warn("请求参数转码失败");
        }
        return val;
    }

    private Object parseSingleParamWithType(Class fieldType, String paramVal) {
        Object v = new Object();
        String param = transCode(paramVal);
        if (null == param || StringUtils.isBlank(param)) {
            return null;
        }
        try {
            if (fieldType.equals(String.class)) {
                filterFaceCharacters(param);
                v = param;
            } else if (fieldType.equals(Boolean.class)) {
                v = Boolean.valueOf(param);
            } else if (fieldType.equals(boolean.class)) {
                v = Boolean.valueOf(param);
            } else if (fieldType.equals(Long.class)) {
                v = Long.valueOf(param);
            } else if (fieldType.equals(long.class)) {
                v = Long.valueOf(param).longValue();
            } else if (fieldType.equals(Integer.class)) {
                v = Integer.valueOf(param);
            } else if (fieldType.equals(int.class)) {
                v = Integer.valueOf(param).intValue();
            } else if (fieldType.equals(java.sql.Date.class)) {
                SimpleDateFormat sdf = FmtUtils.getDateFmt(param.length());
                if (sdf != null) {
                    v = sdf.parse(param);
                } else {
                    throw new RuntimeException("不支持的日期类型值：" + param);
                }
            } else if (fieldType.equals(Timestamp.class)) {
                SimpleDateFormat sdf = FmtUtils.getDateFmt(param.length());
                if (sdf != null) {
                    v = new Timestamp(sdf.parse(param).getTime());
                } else {
                    throw new RuntimeException("不支持的日期类型值：" + param);
                }
            } else if (fieldType.equals(Date.class)) {
                SimpleDateFormat sdf = FmtUtils.getDateFmt(param.length());
                if (sdf != null) {
                    v = sdf.parse(param);
                } else {
                    throw new RuntimeException("不支持的日期类型值：" + param);
                }
            } else if (fieldType.equals(java.sql.Date.class)) {
                SimpleDateFormat sdf = FmtUtils.getDateFmt(param.length());
                if (sdf != null) {
                    v = sdf.parse(param);
                } else {
                    throw new RuntimeException("不支持的日期类型值：" + param);
                }
            } else if (fieldType.isEnum()) {
                Double dv = Double.parseDouble(param);
                v = EnumUtils.getEnum(fieldType, dv.intValue());
            } else if (fieldType.equals(Double.class)) {
                v = Double.valueOf(param);
            } else if (fieldType.equals(BigDecimal.class)) {
                v = new BigDecimal(param);
            } else {
                logger.info(fieldType.getName());
                v = this.getGson().fromJson(param, fieldType);
            }
        } catch (Exception e) {
            logger.error("参数类型转换出错", e);
            logger.error(fieldType.getName());
            logger.error(paramVal);
        }
        return v;
    }

    /**
     * 过滤表情符号
     */
    public void filterFaceCharacters(String str) {
        if (str.trim().isEmpty()) {
            return;
        }
        String pattern = "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";
        Pattern emoji = Pattern.compile(pattern);
        Matcher emojiMatcher = emoji.matcher(str);
        if (emojiMatcher.find()) {
            throw new CustomException("包含特殊表情字符，请检查");
        }

    }

    public abstract String getScanClassName();
}