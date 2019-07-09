package io.github.mrdear.excel.writer;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.mrdear.excel.EasyExcel;
import io.github.mrdear.excel.annotation.ExcelField;
import io.github.mrdear.excel.annotation.ExcelIgnore;
import io.github.mrdear.excel.domain.ExcelReadContext;
import io.github.mrdear.excel.domain.ExcelWriteContext;
import io.github.mrdear.excel.domain.ImportDomain;
import io.github.mrdear.excel.read.ExcelReader;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 测试默认忽略的字段
 *
 * @author rxliuli
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DefaultIgnoreNotSetExcelFieldTest {
  private final String currentPath = DateFieldTest.class.getClassLoader().getResource(".").getPath();
  private final int count = 10;

  @Test
  @Order(1)
  void testIgnoreFieldExport() {
    final List<User> list = IntStream.range(0, count)
        .mapToObj(i -> new User("姓名" + i, i, i % 2 == 0))
        .collect(Collectors.toList());
    EasyExcel.export(currentPath + "/test.xlsx")
        .export(ExcelWriteContext.builder()
            .datasource(list)
            .sheetName("用户表")
            .build()
        )
        .write();
  }

  @Test
  @Order(2)
  void testIgnoreFieldImport() {
    try (final ExcelReader reader = EasyExcel.read(currentPath + "/test.xlsx");) {
      final ImportDomain<User> domain = reader.resolve(
          ExcelReadContext.<User>builder()
              .clazz(User.class)
              .build()
      );
      assertThat(domain.getData())
          .hasSize(count)
          .allMatch(user -> user.getSex() == null);
    }
  }

  public static class User {
    @ExcelField(columnName = "姓名")
    private String name;
    @ExcelField(columnName = "年龄")
    private Integer age;

    @ExcelIgnore
    private Boolean sex;

    public User() {
    }

    public User(String name, Integer age, Boolean sex) {
      this.name = name;
      this.age = age;
      this.sex = sex;
    }

    public String getName() {
      return name;
    }

    public User setName(String name) {
      this.name = name;
      return this;
    }

    public Integer getAge() {
      return age;
    }

    public User setAge(Integer age) {
      this.age = age;
      return this;
    }

    public Boolean getSex() {
      return sex;
    }

    public User setSex(Boolean sex) {
      this.sex = sex;
      return this;
    }

    @Override
    public String toString() {
      return new ToStringBuilder(this)
          .append("name", name)
          .append("age", age)
          .append("sex", sex)
          .toString();
    }
  }
}
