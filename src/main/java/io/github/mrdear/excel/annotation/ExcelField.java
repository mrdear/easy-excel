package io.github.mrdear.excel.annotation;

import io.github.mrdear.excel.domain.convert.DefaultConverter;
import io.github.mrdear.excel.domain.convert.IConverter;

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
public @interface ExcelField {
  /**
   * excel header name
   */
  String columnName() default "";

  /**
   * 写入时所采取的转换器
   */
  Class<? extends IConverter> convert() default DefaultConverter.class;
}
