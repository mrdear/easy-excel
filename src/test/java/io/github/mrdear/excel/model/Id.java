package io.github.mrdear.excel.model;

import lombok.Data;

import io.github.mrdear.excel.annotation.DocField;

/**
 * @author Quding Ding
 * @since 2018/8/2
 */
@Data
public class Id {

  @DocField(columnName = "id")
  private Long id;

}
