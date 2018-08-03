package io.github.mrdear.excel.read;

import io.github.mrdear.excel.ExcelException;
import io.github.mrdear.excel.domain.ExcelReadContext;
import io.github.mrdear.excel.domain.ExcelReadHeader;
import io.github.mrdear.excel.internal.util.ExcelBeanHelper;

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

/**
 * @author Quding Ding
 * @since 2018/6/29
 */
public class DefaultExcelReader implements ExcelReader {


  private Workbook workbook;

  private InputStream inputStream;


  public DefaultExcelReader(InputStream inputStream) {
    try {
      this.workbook = WorkbookFactory.create(inputStream);
      this.inputStream = inputStream;
    } catch (IOException | InvalidFormatException e) {
      throw new ExcelException(e);
    }

  }


  @Override
  public <T> List<T> resolve(ExcelReadContext<T> context) {
    Sheet sheet = this.workbook.getSheetAt(context.getSheetIndex());

    // 读取之前的操作
    context.getReadSheetHook().accept(sheet, context);

    // 解析header
    int startRow = context.getHeaderStart();
    Row header = sheet.getRow(startRow++);
    List<String> realHeaders = getHeaders(header);

    int lastRowNum = sheet.getLastRowNum();
    int totalCount = lastRowNum - header.getRowNum();
    List<T> resultContainer = new ArrayList<>(totalCount);
    Map<String, ExcelReadHeader> configHeaders = context.getHeaders();

    // 依次解析每一行
    for (; startRow <= lastRowNum; startRow++) {
      Row row = sheet.getRow(startRow);
      T instance = ExcelBeanHelper.newInstance(context.getClazz());

      row.cellIterator().forEachRemaining(x -> {
        String columnName = realHeaders.get(x.getColumnIndex());
        ExcelReadHeader tempHeader = configHeaders.get(columnName);
        if (null == tempHeader) {
          return;
        }
        Object value = tempHeader.getConvert().apply(ExcelBeanHelper.getColumnValue(x));
        ExcelBeanHelper.fieldSetValue(tempHeader.getField(), instance, value);
      });

      resultContainer.add(instance);
    }
    return resultContainer;
  }


  @Override
  public void close() {
    try {
      inputStream.close();
      workbook.close();
    } catch (IOException e) {
      throw new ExcelException(e);
    }
  }


  /**
   * 获取到表头
   * @param header header表头
   * @return 表头,有序
   */
  private List<String> getHeaders(Row header) {
    List<String> headers = new ArrayList<>();
    header.cellIterator().forEachRemaining(x -> headers.add(x.getStringCellValue()));
    return headers;
  }

}
