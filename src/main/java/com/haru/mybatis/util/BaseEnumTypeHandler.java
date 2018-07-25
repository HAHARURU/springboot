package com.haru.mybatis.util;

import com.haru.mybatis.enumPackage.StateEnum;
import org.apache.ibatis.type.EnumTypeHandler;

/**
 * @author HARU
 * @since 2018/5/28
 *
 * mybatis枚举数据库映射，EnumTypeHandler代表在数据库中存枚举的名
 */
public class BaseEnumTypeHandler extends EnumTypeHandler<StateEnum> {
    public BaseEnumTypeHandler(Class<StateEnum> type) {
        super(type);
    }
}
