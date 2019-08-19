package io.github.mrdear.excel.model;

import io.github.mrdear.excel.annotation.DocField;
import io.github.mrdear.excel.convert.YmdDateConverter;
import io.github.mrdear.excel.convert.LocalDateConverter;
import io.github.mrdear.excel.convert.LocalDateTimeConverter;
import io.github.mrdear.excel.convert.LocalTimeConverter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * @author rxliuli
 */
public class Person {
  @DocField(columnName = "姓名")
  private String username;
  @DocField(columnName = "生日", convert = LocalDateTimeConverter.class)
  private LocalDateTime birthday;
  @DocField(columnName = "日期", convert = YmdDateConverter.class)
  private Date date;
  @DocField(columnName = "本地日期", convert = LocalDateConverter.class)
  private LocalDate localDate;
  @DocField(columnName = "本地时间", convert = LocalTimeConverter.class)
  private LocalTime localTime;

  public Person() {
  }

  public Person(String username, LocalDateTime birthday, Date date, LocalDate localDate, LocalTime localTime) {
    this.username = username;
    this.birthday = birthday;
    this.date = date;
    this.localDate = localDate;
    this.localTime = localTime;
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
