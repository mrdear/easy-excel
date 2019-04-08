package io.github.mrdear.excel.domain;

import io.github.mrdear.excel.internal.util.ExcelBeanHelper;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Map;
import java.util.function.BiConsumer;

/**
 * 读取上下文
 *
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
  private BiConsumer<Sheet, ExcelReadContext> readSheetHook = (w, v) -> {
  };
  /**
   * 表头与类属性之间的映射
   */
  private Map<String, ExcelReadHeader> headers;

  public static <T> ExcelReadContextBuilder<T> builder() {
    return new ExcelReadContextBuilder<>(new ExcelReadContext<>());
  }


  // package set

  public Class<T> getClazz() {
    return clazz;
  }

  ExcelReadContext<T> setClazz(Class<T> clazz) {
    this.clazz = clazz;
    return this;
  }

  public int getSheetIndex() {
    return sheetIndex;
  }

  ExcelReadContext<T> setSheetIndex(int sheetIndex) {
    this.sheetIndex = sheetIndex;
    return this;
  }

  public int getHeaderStart() {
    return headerStart;
  }

  ExcelReadContext<T> setHeaderStart(int headerStart) {
    this.headerStart = headerStart;
    return this;
  }

  public BiConsumer<Sheet, ExcelReadContext> getReadSheetHook() {
    return readSheetHook;
  }


  // get

  ExcelReadContext<T> setReadSheetHook(BiConsumer<Sheet, ExcelReadContext> readSheetHook) {
    this.readSheetHook = readSheetHook;
    return this;
  }

  public Map<String, ExcelReadHeader> getHeaders() {
    return headers;
  }

  ExcelReadContext<T> setHeaders(Map<String, ExcelReadHeader> headers) {
    this.headers = headers;
    return this;
  }

  ExcelReadContext<T> setHeaders(Class<T> clazz) {
    // 使用默认Header转换器
    this.headers = ExcelBeanHelper.beanToReaderHeaders(clazz);
    return this;
  }
}
