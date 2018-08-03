package io.github.mrdear.excel.model;

import lombok.Data;

import io.github.mrdear.excel.annotation.ExcelField;

/**
 * @author Quding Ding
 * @since 2018/8/2
 */
@Data
public class Id {

  @ExcelField(columnName = "id")
  private Long id;

}
