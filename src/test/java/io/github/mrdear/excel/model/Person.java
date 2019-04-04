package io.github.mrdear.excel.model;

import io.github.mrdear.excel.annotation.ExcelField;
import io.github.mrdear.excel.domain.convert.DateConverter;
import io.github.mrdear.excel.domain.convert.LocalDateTimeConverter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author rxliuli
 */
public class Person {
  @ExcelField(columnName = "姓名")
  private String username;
  @ExcelField(columnName = "生日", convert = LocalDateTimeConverter.class)
  private LocalDateTime birthday;
  @ExcelField(columnName = "日期", convert = DateConverter.class)
  private Date date;

  public Person() {
  }

  public Person(String username, LocalDateTime birthday, Date date) {
    this.username = username;
    this.birthday = birthday;
    this.date = date;
  }

  public Date getDate() {
    return date;
  }

  public Person setDate(Date date) {
    this.date = date;
    return this;
  }

  public String getUsername() {
    return username;
  }

  public Person setUsername(String username) {
    this.username = username;
    return this;
  }

  public LocalDateTime getBirthday() {
    return birthday;
  }

  public Person setBirthday(LocalDateTime birthday) {
    this.birthday = birthday;
    return this;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("username", username)
        .append("birthday", birthday)
        .append("date", date)
        .toString();
  }
}
