package io.github.mrdear.excel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.github.mrdear.excel.annotation.DocField;
import io.github.mrdear.excel.annotation.DocIgnore;
import io.github.mrdear.excel.convert.YmdDateConverter;

import java.util.Date;

/**
 * @author Quding Ding
 * @since 2018/6/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWithAnnotation {

  @DocField(columnName = "用户名")
  private String username;

  @DocField(columnName = "用户密码")
  private String passwd;

  @DocIgnore
  private String nickName;

  @DocField(columnName = "登录日期", convert = YmdDateConverter.class)
  private Date date;
}
