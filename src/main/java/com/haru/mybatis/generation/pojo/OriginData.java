package com.haru.mybatis.generation.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 原始需要添加进freemarker模板中的数据
 *
 * @author HARU
 * @date 2019/5/5
 **/
@Data
public class OriginData implements Serializable {

    private static final long serialVersionUID = 2402079146938006912L;

    /**
     * 类名前缀，请使用规范的bean类命名方式
     */
    private String className;

    /**
     * 文件生成所在的包 不填默认在com.[fileSuffix]下
     */
    private String packageName;

    /**
     * 文件名后缀，fileSuffixes长度为0时，默认后缀为PO、BO和VO
     */
    private List<String> fileSuffixes;

    /**
     * 字段
     */
    private List<Field> fields;

    /**
     * 类注解描述
     */
    private String description;

    /**
     * 创建人
     */
    private String author;

    /**
     * 集成的类名
     */
    private String extendsName;

    /**
     * 实现的接口
     */
    private List<String> implementsList;
}
