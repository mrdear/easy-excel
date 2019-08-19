package io.github.mrdear.excel.read;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.mrdear.excel.DocType;
import io.github.mrdear.excel.EasyDoc;
import io.github.mrdear.excel.annotation.DocField;
import io.github.mrdear.excel.convert.ConverterFactory;
import io.github.mrdear.excel.convert.IConverter;
import io.github.mrdear.excel.write.WriteContextBuilder;
import io.github.mrdear.excel.writer.SimpleExcelExportTest;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * @author rxliuli
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomConverterTest {
  private final String currentPath = SimpleExcelExportTest.class.getClassLoader().getResource(".").getPath();
  private final int count = 5;
  private String fileName = currentPath + "/CustomConverterTest.xlsx";

  @BeforeEach
  void before() {
    // 全局注册指定类型的转换器
    ConverterFactory.register(LocalTime.class, new CustomLocalTimeConverter());
  }

  @Test
  @Order(1)
  void exportDateList() throws IOException {
    final List<Person> users = mockUser(count);
    EasyDoc.export(DocType.XLSX,fileName)
        .export(WriteContextBuilder.builder()
            .dataSource(users)
            .buildForExcel("user"))
        .writeAndFlush();
  }

  @Test
  @Order(2)
  void importDateList() throws FileNotFoundException {
    try (DocReader reader = EasyDoc.read(DocType.XLSX, new FileInputStream(fileName))) {
      List<Person> result = reader.resolve(ReadContextBuilder.<Person>builder()
          .clazz(Person.class)
          .buildForExcel())
          .getData();
      assertThat(result).hasSize(count);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private List<Person> mockUser(int count) {
    return IntStream.range(0, count)
        .mapToObj(i -> new Person("姓名 " + i, new Date(), LocalDate.now(), LocalTime.now()))
        .collect(Collectors.toList());
  }

  public static class CustomLocalDateConverter implements IConverter<LocalDate> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");

    @Override
    public String to(LocalDate localDate) {
      return localDate.format(formatter);
    }

    @Override
    public LocalDate from(String to) {
      return LocalDate.parse(to, formatter);
    }
  }

  public static class CustomLocalTimeConverter implements IConverter<LocalTime> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH时mm分ss秒");

    @Override
    public String to(LocalTime localDate) {
      return localDate.format(formatter);
    }

    @Override
    public LocalTime from(String to) {
      return LocalTime.parse(to, formatter);
    }
  }


  public static class Person {
    @DocField(columnName = "姓名", order = 1)
    private String username;
    @DocField(columnName = "日期", order = 2)
    private Date date;
    @DocField(columnName = "本地日期", order = 3, convert = CustomLocalDateConverter.class)
    private LocalDate localDate;
    @DocField(columnName = "本地时间", order = 4)
    private LocalTime localTime;

    public Person() {
    }

    public Person(String username, Date date, LocalDate localDate, LocalTime localTime) {
      this.username = username;
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

    @Override
    public String toString() {
      return new ToStringBuilder(this)
          .append("username", username)
          .append("date", date)
          .append("localDate", localDate)
          .append("localTime", localTime)
          .toString();
    }
  }
}
