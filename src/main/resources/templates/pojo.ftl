package ${packageName};

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
<#if importPackages?exists && (importPackages?size > 0)>
    <#list importPackages as importPackage>
        <#if importPackage?exists>
            <#lt>import ${importPackage};
        </#if>
    </#list>
</#if>

/**
 * <#if description?exists>${description}</#if>
 *
 * @author <#if author?exists>${author}</#if>
 * @date <#if creatDate?exists>${creatDate}</#if>
 **/
@Data
@ToString
<#if className?exists>public class ${className} </#if><#if extendsName?exists> extends ${extendsName}</#if><#if implementsList?exists && (implementsList?size > 0)>implements<#list implementsList as implementsItem> ${implementsItem}<#if implementsItem_has_next>,</#if></#list></#if> {
<#-- 生成属性 -->

    private static final long serialVersionUID = ${serialVersionUID}L;
<#if fields?exists && (fields?size > 0)>

    <#list fields as field>
        <#if field.comments?exists && field.comments != "">
    /**
     * ${field.comments}
     */
    private ${field.type} ${field.name};

        </#if>
    </#list>
</#if>
}
