package cn.ifreehub.excel.write;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.ifreehub.excel.ExcelException;
import cn.ifreehub.excel.domain.ExcelWriterHeader;
import cn.ifreehub.excel.domain.ExcelType;
import cn.ifreehub.excel.domain.ExcelWriteContext;
import cn.ifreehub.excel.internal.util.Assert;
import cn.ifreehub.excel.internal.util.ExcelBeanHelper;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Quding Ding
 * @since 2018/6/28
 */
public class DefaultExcelWriter implements ExcelWriter {

  private static Logger logger = LoggerFactory.getLogger(DefaultExcelWriter.class);

  /**
   * 工作簿
   */
  private final Workbook workbook;
  /**
   * 输出目标
   */
  private OutputStream outputStream;

  public DefaultExcelWriter(ExcelType excelType, OutputStream outputStream) {
    Assert.notNull(excelType, "excelType can't be null");
    Assert.notNull(outputStream, "outputStream can't be null");

    // 创建excel
    this.workbook = excelType.workbook(1);
    this.outputStream = outputStream;
  }

  /**
   * 把数据写入该表中
   *
   * @return 该实例
   */
  @Override
  public DefaultExcelWriter export(ExcelWriteContext context) {
    Sheet sheet = StringUtils.isEmpty(context.getSheetName())
        ? workbook.createSheet()
        : workbook.createSheet(context.getSheetName());

    // custom 处理
    context.getCreateSheetHook().accept(sheet, context);

    int startRow = context.getStartRow();


    // 写表头
    Row headerRow = sheet.createRow(startRow++);
    int[] tempCol = {0};
    LinkedHashMap<String, ExcelWriterHeader> headers = context.getHeaders();
    headers.forEach((k, v) -> {
      Cell cell = headerRow.createCell(tempCol[0]++);
      cell.setCellValue(v.getName());
    });

    // 写数据
    for (Map<String, Object> rowData : context.getDatasource()) {
      Row row = sheet.createRow(startRow++);
      tempCol[0] = 0;
      headers.forEach((k, v) -> {
        Cell cell = row.createCell(tempCol[0]++);
        Object value = rowData.get(k);
        ExcelBeanHelper.autoFitCell(cell, v.getConvert().apply(value));
      });
    }

    return this;
  }


  @Override
  public void write() {
    try {
      this.workbook.write(outputStream);
    } catch (IOException e) {
      throw new ExcelException(e);
    } finally {
      // 释放资源
      try {
        this.workbook.close();
        outputStream.flush();
        outputStream.close();
      } catch (IOException e) {
        logger.error("write fail", e);
      }
    }
  }
}
