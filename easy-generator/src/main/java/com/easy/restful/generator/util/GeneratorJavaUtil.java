package com.easy.restful.generator.util;

import com.easy.restful.generator.model.FieldSet;

import java.util.List;

/**
 * 生成java代码帮助类
 *
 * @author tengchong
 * @date 2019-02-22
 */
public class GeneratorJavaUtil {
    private GeneratorJavaUtil() {}

    /**
     * 字符串
     */
    private static final String STRING = "String";

    /**
     * 生成验证注解
     *
     * @param propertyName 属性名
     * @param list         配置列表
     * @return 验证注解
     */
    public static String generatorVerification(String propertyName, List<FieldSet> list) {
        if(list != null){
            for (FieldSet fieldSet : list) {
                if (propertyName.equals(fieldSet.getPropertyName())) {
                    return generatorVerification(fieldSet);
                }
            }
        }
        return null;
    }

    private static String generatorVerification(FieldSet fieldSet) {
        if(fieldSet.getRequired()){
            if (STRING.equals(fieldSet.getPropertyType())) {
                return addTab("@NotBlank(message = \"" + fieldSet.getLabel() + "不能为空\")");
            }
            return addTab("@NotNull(message = \"" + fieldSet.getLabel() + "不能为空\")");
        }
        return null;
    }

    /**
     * 添加换行以及缩进
     *
     * @param code 代码
     * @return 代码
     */
    private static String addTab(String code){
        return code + "\r\n    ";
    }
}