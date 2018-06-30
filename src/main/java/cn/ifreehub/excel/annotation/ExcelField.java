package cn.ifreehub.excel.annotation;

import cn.ifreehub.excel.domain.convert.DefaultFunction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Function;

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
  Class<? extends Function> writerConvert() default DefaultFunction.class;

  /**
   * 读取时所采取的转换器
   */
  Class<? extends Function> readerConvert() default DefaultFunction.class;

}
