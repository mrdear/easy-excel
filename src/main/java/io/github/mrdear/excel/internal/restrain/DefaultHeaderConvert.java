/**
 * Alipay.com Inc. Copyright (c) 2004-2018 All Rights Reserved.
 */
package io.github.mrdear.excel.internal.restrain;

import io.github.mrdear.excel.annotation.ExcelField;
import io.github.mrdear.excel.domain.ExcelReadHeader;
import io.github.mrdear.excel.domain.convert.IConverter;
import io.github.mrdear.excel.internal.util.ConvertHelper;
import io.github.mrdear.excel.internal.util.Pair;

import java.lang.reflect.Field;

/**
 * 默认header转换规则
 *
 * @author quding
 * @version $Id: DefaultHeaderConvert.java, v 0.1 2018年12月31日 09:52 quding Exp $
 */
public class DefaultHeaderConvert implements IConverter<Field, Pair<String, ExcelReadHeader>> {
  @Override
  public Pair<String, ExcelReadHeader> to(Field field) {
    // 存在ExcelField,则以其为准
    ExcelField annotation = field.getAnnotation(ExcelField.class);
    if (null != annotation) {
      return new Pair<>(annotation.columnName(), ExcelReadHeader.create(field,
          ConvertHelper.getConvert(annotation.convert())));
    }
    return new Pair<>(field.getName(), ExcelReadHeader.create(field));
  }

  @Override
  public Field from(Pair<String, ExcelReadHeader> stringExcelReadHeaderPair) {
    return null;
  }
}