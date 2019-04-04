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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author rxliuli
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DateFieldTest {
  private final String currentPath = DateFieldTest.class.getClassLoader().getResource(".").getPath();

  @Test
  @Order(1)
  public void exportVoList() {
    final List<Person> users = mockUser(5);
    EasyExcel.export(currentPath + "/test.xlsx")
        .export(ExcelWriteContext.builder()
            .datasource(users)
            .sheetName("user")
            .build())
        .write();
  }

  @Test
  @Order(2)
  public void importVoList() {
    try (ExcelReader reader = EasyExcel.read(new FileInputStream(currentPath + "/test.xlsx"))) {
      List<Person> result = reader.resolve(ExcelReadContext.<Person>builder()
          .clazz(Person.class)
          .build());
      System.out.println(result);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void LocalDateTime() {
    final String str = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    System.out.println(str);
  }

  private List<Person> mockUser(int count) {
    return IntStream.range(0, count)
        .mapToObj(i -> new Person("姓名 " + count, LocalDateTime.now(), new Date()))
        .collect(Collectors.toList());
  }
}
