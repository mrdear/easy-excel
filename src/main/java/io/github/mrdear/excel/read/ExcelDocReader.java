package io.github.mrdear.excel.read;

import io.github.mrdear.excel.ExcelException;
import io.github.mrdear.excel.domain.ImportDomain;
import io.github.mrdear.excel.internal.util.DocBeanUtils;
import io.github.mrdear.excel.internal.util.PoiUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * excel读取实现
 * @author Quding Ding
 * @since 2018/6/29
 */
public class ExcelDocReader implements DocReader {


  /**
   * excel文档
   */
  private Workbook workbook;

  /**
   * 读取输入流
   */
  private InputStream inputStream;


  public ExcelDocReader(InputStream inputStream) {
    try {
      this.workbook = WorkbookFactory.create(inputStream);
      this.inputStream = inputStream;
    } catch (IOException | InvalidFormatException e) {
      throw new ExcelException(e);
    }

  }

  @Override
  public <T> ImportDomain<T> resolve(ReadContext<T> readContext) {
    if (!(readContext instanceof ExcelReadContext)) {
      throw new ExcelException("ExcelDocReader only support ExcelReadContext");
    }

    ExcelReadContext<T> context = (ExcelReadContext<T>) readContext;

    Sheet sheet = this.workbook.getSheetAt(context.getSheetIndex());

    // 读取之前,执行钩子函数
    context.getReadSheetHook().accept(sheet, context);

    // 解析header
    int startRow = context.getHeaderStart();
    Row header = sheet.getRow(startRow++);
    List<String> realHeaders = getHeaders(header);

    int lastRowNum = sheet.getLastRowNum();
    Map<String, ReadHeader> configHeaders = context.getHeaders();

    List<T> result = IntStream.rangeClosed(startRow, lastRowNum)
        .parallel()
        .mapToObj(x -> {
          Row row = sheet.getRow(x);
          T instance = DocBeanUtils.newInstance(context.getClazz());

          final int i = x;
          row.cellIterator().forEachRemaining(cell -> {
            int columnIndex = cell.getColumnIndex();
            ReadHeader tempHeader = configHeaders.get(realHeaders.get(columnIndex));
            String columnValue = PoiUtils.getColumnValue(cell);
            // 如果字段值为空字符串则直接跳过
            if (null == tempHeader || StringUtils.isEmpty(columnValue)) {
              return;
            }

            Object value = null;
            try {
              value = tempHeader.getConvert().from(columnValue);
            } catch (Exception e) {
              throw new ExcelException(e, "convert fail, row=%s, column=%s, filed=%s, value=%s",
                  i+1,columnIndex+1,tempHeader.getField().getName(), columnValue);
            }

            try {
              DocBeanUtils.fieldSetValue(tempHeader.getField(), instance, value);
            } catch (Exception e) {
              throw new ExcelException(e, "field set value fail, row=%s, column=%s, filed=%s, value=%s",
                  i+1, columnIndex+1, tempHeader.getField().getName(), value);
            }
          });
          return instance;
        })
        .collect(Collectors.toList());

    // 依次解析每一行

    return new ImportDomain<>(result);
  }


  @Override
  public void close() throws Exception  {
    if (null != workbook) {
      workbook.close();
    }

    if (null != inputStream) {
      inputStream.close();
    }

  }


  /**
   * 获取到表头
   *
   * @param header header表头
   * @return 表头, 有序
   */
  private List<String> getHeaders(Row header) {
    List<String> headers = new ArrayList<>();
    header.cellIterator().forEachRemaining(x -> headers.add(x.getStringCellValue()));
    return headers;
  }

}
