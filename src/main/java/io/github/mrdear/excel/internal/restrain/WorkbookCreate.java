package io.github.mrdear.excel.internal.restrain;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author Quding Ding
 * @since 2018/6/28
 */
public interface WorkbookCreate {

  /**
   * 创建对应的workbook
   * @param mayRowCount 预测的数据量
   * @return workbook
   */
  Workbook workbook(int mayRowCount);

}

