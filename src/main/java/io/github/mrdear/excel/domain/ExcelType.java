package io.github.mrdear.excel.domain;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import io.github.mrdear.excel.internal.restrain.WorkbookCreate;

/**
 * @author Quding Ding
 * @since 2018/2/6
 */
public enum ExcelType implements WorkbookCreate {
  /**
   * xls
   */
  XLS {
    @Override
    public Workbook workbook(int mayRowCount) {
      return new HSSFWorkbook();
    }
  },
  /**
   * xlsx
   */
  XLSX {
    @Override
    public Workbook workbook(int mayRowCount) {
      XSSFWorkbook workbook = new XSSFWorkbook();
      return new SXSSFWorkbook(workbook, 100);
    }
  };

}
