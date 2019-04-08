package io.github.mrdear.excel.domain;

import java.util.List;

/**
 * 导入的得到的数据
 *
 * @author rxliuli
 */
public class ImportDomain<T> {
  /**
   * 数据列表
   */
  private List<T> data;
  /**
   * 错误列表
   */
  private List<ExcelImportError> errorList;

  public ImportDomain() {
  }

  public ImportDomain(List<T> data, List<ExcelImportError> errorList) {
    this.data = data;
    this.errorList = errorList;
  }

  public List<T> getData() {
    return data;
  }

  public ImportDomain<T> setData(List<T> data) {
    this.data = data;
    return this;
  }

  public List<ExcelImportError> getErrorList() {
    return errorList;
  }

  public ImportDomain<T> setErrorList(List<ExcelImportError> errorList) {
    this.errorList = errorList;
    return this;
  }
}
