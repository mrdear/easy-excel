package io.github.mrdear.excel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.github.mrdear.excel.annotation.ExcelField;
import io.github.mrdear.excel.annotation.ExcelIgnore;
import io.github.mrdear.excel.domain.convert.DateToStringConvert;
import io.github.mrdear.excel.domain.convert.StringToDateConvert;

import java.util.Date;

/**
 * @author Quding Ding
 * @since 2018/6/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWithAnnotation {

  @ExcelField(columnName = "用户名")
  private String username;

  @ExcelField(columnName = "用户密码")
  private String passwd;

  @ExcelIgnore
  private String nickName;

  @ExcelField(columnName = "登录日期",
      writerConvert = DateToStringConvert.class,
      readerConvert = StringToDateConvert.class)
  private Date date;
}
