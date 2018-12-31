package io.github.mrdear.excel.domain;

import io.github.mrdear.excel.internal.restrain.DefaultHeaderConvert;
import io.github.mrdear.excel.internal.util.ConvertHelper;
import io.github.mrdear.excel.internal.util.ExcelBeanHelper;
import io.github.mrdear.excel.internal.util.Pair;

import org.apache.poi.ss.usermodel.Sheet;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * 读取上下文
 * @author Quding Ding
 * @since 2018/6/29
 */
public class ExcelReadContext<T> {

  /**
   * header读取出来的类
   */
  private Class<T> clazz;

  /**
   * 所在sheet索引
   */
  private int sheetIndex = 0;
  /**
   * header所开始的第一行
   */
  private int headerStart = 0;

  /**
   * 读取sheet之后的操作
   */
  private BiConsumer<Sheet, ExcelReadContext> readSheetHook = (w, v) -> { };
  /**
   * 表头与类属性之间的映射
   */
  private Map<String, ExcelReadHeader> headers;

  public static <T> ExcelReadContextBuilder<T> builder() {
    return new ExcelReadContextBuilder<>(new ExcelReadContext<>());
  }


  // package set

  ExcelReadContext<T> setClazz(Class<T> clazz) {
    this.clazz = clazz;
    return this;
  }

  ExcelReadContext<T> setSheetIndex(int sheetIndex) {
    this.sheetIndex = sheetIndex;
    return this;
  }

  ExcelReadContext<T> setHeaderStart(int headerStart) {
    this.headerStart = headerStart;
    return this;
  }

  ExcelReadContext<T> setReadSheetHook(BiConsumer<Sheet, ExcelReadContext> readSheetHook) {
    this.readSheetHook = readSheetHook;
    return this;
  }

  ExcelReadContext<T> setHeaders(Map<String, ExcelReadHeader> headers) {
    this.headers = headers;
    return this;
  }

  ExcelReadContext<T> setHeaders(Class<T> clazz) {
    // 使用默认Header转换器
    this.headers = ExcelBeanHelper.beanToReaderHeaders(clazz, ConvertHelper.getConvert(DefaultHeaderConvert.class));
    return this;
  }

  /**
   * 高级用法,开放指定class的field读取转换接口,用于自定义场景
   * @param clazz 对应的header类
   * @param convert 转换器
   * @return context
   */
  ExcelReadContext<T> setHeaders(Class<T> clazz, Function<Field, Pair<String, ExcelReadHeader>> convert) {
    // 使用自定义Header转换器
    this.headers = ExcelBeanHelper.beanToReaderHeaders(clazz, convert);
    return this;
  }


  // get

  public Class<T> getClazz() {
    return clazz;
  }

  public int getSheetIndex() {
    return sheetIndex;
  }

  public int getHeaderStart() {
    return headerStart;
  }

  public BiConsumer<Sheet, ExcelReadContext> getReadSheetHook() {
    return readSheetHook;
  }

  public Map<String, ExcelReadHeader> getHeaders() {
    return headers;
  }
}
