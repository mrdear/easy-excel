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

  public ImportDomain() {
  }

  public ImportDomain(List<T> data) {
    this.data = data;
  }

  public List<T> getData() {
    return data;
  }

  public ImportDomain<T> setData(List<T> data) {
    this.data = data;
    return this;
  }

}
