package com.haru.mybatis.generation.component;


import com.haru.mybatis.generation.pojo.OriginData;

/**
 * 通过freemarker模板生成.java文件，包括PO、DO和VO。
 *
 * @author HARU
 * @date 2019/5/5
 **/
public interface GenerateBeanComponent {

    /**
     * 生成java文件
     *
     * @param originData     原始需要添加进freemarker模板中的数据
     */
    void generateBean(OriginData originData);
}
