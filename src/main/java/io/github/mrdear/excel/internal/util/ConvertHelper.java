package io.github.mrdear.excel.internal.util;

import io.github.mrdear.excel.ExcelException;
import io.github.mrdear.excel.domain.convert.IConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Quding Ding
 * @since 2018/5/28
 */
public class ConvertHelper {

  private static final Map<String, IConverter> CONVERT_BEAN_MAP = new HashMap<>(10);

  /**
   * get writerConvert, if not exist, new instance
   *
   * @param clazz writerConvert class
   * @return writerConvert instance
   */
  @SuppressWarnings("unchecked")
  public static <R extends IConverter> R getConvert(Class<? extends IConverter> clazz) {
    IConverter bean = CONVERT_BEAN_MAP.get(clazz.getName());
    if (null == bean) {
      synchronized (CONVERT_BEAN_MAP) {
        bean = CONVERT_BEAN_MAP.get(clazz.getName());
        if (null == bean) {
          try {
            bean = clazz.newInstance();
            CONVERT_BEAN_MAP.put(clazz.getName(), bean);
          } catch (InstantiationException | IllegalAccessException e) {
            throw new ExcelException(e);
          }
        }
      }
    }
    return (R) bean;
  }

}
