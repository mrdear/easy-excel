package io.github.mrdear.excel.read;

import io.github.mrdear.excel.exception.DocumentException;
import io.github.mrdear.excel.domain.ReadHeader;
import io.github.mrdear.excel.internal.util.DocBeanUtils;
import io.github.mrdear.excel.internal.util.PoiUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

  private static Logger LOGGER = LoggerFactory.getLogger(ExcelDocReader.class);

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
      throw new DocumentException(e);
    }

  }

  @Override
  public <T> ReaderResolveResult<T> resolve(ReadContext<T> readContext) {
    if (!(readContext instanceof ExcelReadContext)) {
      throw new DocumentException("ExcelDocReader only support ExcelReadContext");
    }

    ExcelReadContext<T> context = (ExcelReadContext<T>) readContext;

    Sheet sheet = this.workbook.getSheetAt(context.getSheetIndex());

    if (null == sheet) {
      throw new DocumentException("no sheet found at sheetIndex=%s", context.getSheetIndex());
    }

    // 读取之前,执行钩子函数
    context.getReadSheetHook().accept(sheet, context);

    // 解析header
    int startRow = context.getHeaderStart();
    List<String> realHeaders = getHeaders(sheet.getRow(startRow++));
    Map<String, ReadHeader> configHeaders = context.getHeaders();
    int lastRowNum = sheet.getLastRowNum();
    Class<T> clazz = context.getClazz();

    List<T> result = IntStream.rangeClosed(startRow, lastRowNum)
        .parallel()
        .mapToObj(x -> {
          T instance = DocBeanUtils.newInstance(clazz);
          Row row = sheet.getRow(x);

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
              throw new DocumentException(e, "convert fail, row=%s, column=%s, filed=%s, value=%s",
                  x+1,columnIndex+1,tempHeader.getField().getName(), columnValue);
            }

            try {
              DocBeanUtils.fieldSetValue(tempHeader.getField(), instance, value);
            } catch (Exception e) {
              throw new DocumentException(e, "field set value fail, row=%s, column=%s, filed=%s, value=%s",
                  x+1, columnIndex+1, tempHeader.getField().getName(), value);
            }
          });
          return instance;
        })
        .collect(Collectors.toList());

    // 依次解析每一行
    ReaderResolveResult<T> resolveResult = new ReaderResolveResult<>();
    resolveResult.setData(result);
    return resolveResult;
  }


  @Override
  public void close() throws IOException  {
    if (null != inputStream) {
      inputStream.close();
      LOGGER.debug("close read inputStream success");
    }

    if (null != workbook) {
      workbook.close();
      LOGGER.debug("close read workbook success");
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
    LOGGER.debug("using header={}", header);
    return headers;
  }

}
