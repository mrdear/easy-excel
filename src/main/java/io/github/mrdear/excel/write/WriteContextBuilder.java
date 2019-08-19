package io.github.mrdear.excel.write;

import lombok.Getter;

import io.github.mrdear.excel.domain.WriterHeader;
import io.github.mrdear.excel.exception.DocumentException;
import io.github.mrdear.excel.internal.util.DocBeanUtils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * 写入上下文构造器
 * @author Quding Ding
 * @since 2018/6/28
 */
public class WriteContextBuilder {
  /**
   * 写入偏移
   */
  @Getter
  private int startRow = 0;

  /**
   * 写入表头
   */
  @Getter
  private LinkedHashMap<String, WriterHeader> headers;

  /**
   * 数据源
   */
  @Getter
  private List<Map<String, Object>> dataSource;

  /**
   * 写入钩子
   */
  private BiConsumer<Sheet, WriteContext> createSheetHook;

  /**
   * 入口
   */
  public static WriteContextBuilder builder() {
    return new WriteContextBuilder();
  }


  public WriteContextBuilder startRow(int startRow) {
    this.startRow = startRow;
    return this;
  }

  public WriteContextBuilder createSheetHook(BiConsumer<Sheet, WriteContext> createSheetHook) {
    this.createSheetHook = createSheetHook;
    return this;
  }

  public WriteContextBuilder headers(LinkedHashMap<String, WriterHeader> headers) {
    this.headers = headers;
    return this;
  }

  public <T> WriteContextBuilder dataSource(List<T> dataSource) {
    if (null == dataSource || dataSource.isEmpty()) {
      this.dataSource = Collections.emptyList();
    } else {
      this.dataSource = DocBeanUtils.beanToMap(dataSource);
    }
    // 确定header
    if (null == this.headers && CollectionUtils.isNotEmpty(dataSource)) {
      this.headers(DocBeanUtils.beanToWriterHeaders(dataSource.get(0).getClass()));
    }
    return this;
  }

  public ExcelWriteContext buildForExcel() {
    beforeBuildCheck();

    ExcelWriteContext context = new ExcelWriteContext();
    context.setDatasource(this.dataSource);
    if (null != this.createSheetHook) {
      context.setCreateSheetHook(this.createSheetHook);
    }
    context.setHeaders(null == this.headers ? new LinkedHashMap<>(0) : this.headers);
    context.setStartRow(this.startRow);
    return context;
  }

  public ExcelWriteContext buildForExcel(String sheetName) {
    beforeBuildCheck();

    ExcelWriteContext context = new ExcelWriteContext();
    context.setSheetName(sheetName);
    if (null != this.createSheetHook) {
      context.setCreateSheetHook(this.createSheetHook);
    }
    context.setDatasource(this.dataSource);
    context.setStartRow(this.startRow);
    context.setHeaders(null == this.headers ? new LinkedHashMap<>(0) : this.headers);
    return context;
  }

  private void beforeBuildCheck() {
    if (null == dataSource) {
      throw new DocumentException("datasource can't be null");
    }
  }

}
