package io.github.mrdear.excel.annotation;

import io.github.mrdear.excel.convert.IConverter;
import io.github.mrdear.excel.convert.NotSpecifyConverter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Quding Ding
 * @since 2018/5/28
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DocField {
  /**
   * excel header name
   */
  String columnName() default "";

  /**
   * 写入时所采取的转换器
   */
  Class<? extends IConverter> convert() default NotSpecifyConverter.class;

  /**
   * 表格的列排序，默认全部为 0（不排序）
   */
  int order() default 0;
}
