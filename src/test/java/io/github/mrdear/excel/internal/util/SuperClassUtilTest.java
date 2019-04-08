package io.github.mrdear.excel.internal.util;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author rxliuli
 */
class SuperClassUtilTest {

  @Test
  void getAllDeclaredField() {
    final Set<Field> fieldSet = SuperClassUtil.getAllDeclaredField(Demo2.class).stream()
        .filter(x -> !StringUtils.endsWith(x.getName(), "this$0"))
        .collect(Collectors.toSet());
    assertThat(fieldSet)
        .hasSize(3);
    final Set<String> set = fieldSet.stream()
        .map(Field::getName)
        .collect(Collectors.toSet());
    assertThat(set)
        .contains("name", "age");
  }


  class Demo1 {
    private String name;

    public String getName() {
      return name;
    }

    public Demo1 setName(String name) {
      this.name = name;
      return this;
    }
  }

  class Demo2 extends Demo1 {
    private static final long serialVersionUID = 1;
    private Integer age;

    public Integer getAge() {
      return age;
    }

    public Demo2 setAge(Integer age) {
      this.age = age;
      return this;
    }
  }
}