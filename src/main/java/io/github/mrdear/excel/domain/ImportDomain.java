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
  private List<ExcelImportError> errors;

  public ImportDomain() {
  }

  public ImportDomain(List<T> data, List<ExcelImportError> errors) {
    this.data = data;
    this.errors = errors;
  }

  public List<T> getData() {
    return data;
  }

  public ImportDomain<T> setData(List<T> data) {
    this.data = data;
    return this;
  }

  public List<ExcelImportError> getErrors() {
    return errors;
  }

  public ImportDomain<T> setErrors(List<ExcelImportError> errors) {
    this.errors = errors;
    return this;
  }
}
