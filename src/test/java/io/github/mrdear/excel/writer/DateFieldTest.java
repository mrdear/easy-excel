package io.github.mrdear.excel.writer;

import io.github.mrdear.excel.EasyExcel;
import io.github.mrdear.excel.domain.ExcelReadContext;
import io.github.mrdear.excel.domain.ExcelWriteContext;
import io.github.mrdear.excel.model.Person;
import io.github.mrdear.excel.read.ExcelReader;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author rxliuli
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DateFieldTest {
  private final String currentPath = DateFieldTest.class.getClassLoader().getResource(".").getPath();
  private final int count = 5;

  @Test
  @Order(1)
  public void exportDateList() {
    final List<Person> users = mockUser(count);
    EasyExcel.export(currentPath + "/test.xlsx")
        .export(ExcelWriteContext.builder()
            .datasource(users)
            .sheetName("user")
            .build())
        .write();
  }

  @Test
  @Order(2)
  public void importDateList() {
    try (ExcelReader reader = EasyExcel.read(new FileInputStream(currentPath + "/test.xlsx"))) {
      List<Person> result = reader.resolve(ExcelReadContext.<Person>builder()
          .clazz(Person.class)
          .build())
          .getData();
      assertThat(result)
          .hasSize(count);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private List<Person> mockUser(int count) {
    return IntStream.range(0, count)
        .mapToObj(i -> new Person("姓名 " + count, LocalDateTime.now(), new Date(), LocalDate.now(), LocalTime.now()))
        .collect(Collectors.toList());
  }
}
