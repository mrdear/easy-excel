package io.github.mrdear.excel.read;

import io.github.mrdear.excel.ExcelException;
import io.github.mrdear.excel.domain.ExcelImportError;
import io.github.mrdear.excel.domain.ExcelReadContext;
import io.github.mrdear.excel.domain.ExcelReadHeader;
import io.github.mrdear.excel.domain.ImportDomain;
import io.github.mrdear.excel.internal.util.ExcelBeanHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
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
  public <T> ImportDomain<T> resolve(ExcelReadContext<T> context) {
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
    final LinkedList<ExcelImportError> errorList = new LinkedList<>();

    // 依次解析每一行
    for (; startRow <= lastRowNum; startRow++) {
      Row row = sheet.getRow(startRow);
      T instance = ExcelBeanHelper.newInstance(context.getClazz());
      final int i = startRow;
      row.cellIterator().forEachRemaining(x -> {
        final int columnIndex = x.getColumnIndex();
        final ExcelReadHeader tempHeader = configHeaders.get(realHeaders.get(columnIndex));
        final String columnValue = ExcelBeanHelper.getColumnValue(x);
        // 如果字段值为空字符串则直接跳过
        if (null == tempHeader || StringUtils.isEmpty(columnValue)) {
          return;
        }
        Object value = null;
        try {
          value = tempHeader.getConvert().from(columnValue);
        } catch (Exception e) {
          // 如果解析错误则记录下来
          errorList.add(new ExcelImportError(i, columnIndex, tempHeader.getField().getName(), columnValue, e));
        }
        ExcelBeanHelper.fieldSetValue(tempHeader.getField(), instance, value);
      });

      resultContainer.add(instance);
    }
    return new ImportDomain<>(resultContainer, errorList);
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
