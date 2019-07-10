package io.github.mrdear.excel.writer;

import io.github.mrdear.excel.EasyExcel;
import io.github.mrdear.excel.write.ExcelWriteContext;
import io.github.mrdear.excel.model.User;
import io.github.mrdear.excel.model.UserWithAnnotation;
import org.junit.Test;

import java.util.*;

/**
 * @author Quding Ding
 * @since 2018/6/29
 */
public class SimpleExcelExportTest {
  private final String currentPath = SimpleExcelExportTest.class.getClassLoader().getResource(".").getPath();

  @Test
  public void testSimpleExport() {
    List<User> users = mockUser(5);
    EasyExcel.export(currentPath + "/test.xlsx")
        .export(ExcelWriteContext.builder()
            .datasource(users)
            .sheetName("user")
            .build())
        .write();
  }

  /**
   * 空数据应该导出空表
   */
  @Test
  public void testEmptyExcel() {
    EasyExcel.export(currentPath + "/test.xlsx")
        .export(ExcelWriteContext.builder()
            .datasource(Collections.emptyList())
            .sheetName("user")
            .build())
        .write();
  }


  @Test
  public void testSimpleWithAnnotationExport() {
    List<UserWithAnnotation> users = mockUserWithAnnotation(5);
    EasyExcel.export(currentPath + "/test.xlsx")
        .export(ExcelWriteContext.builder()
            .datasource(users)
            .sheetName("user")
            .build())
        .write();
  }


  private List<User> mockUser(int count) {
    List<User> result = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      User user = new User("张三" + i, UUID.randomUUID().toString());
      result.add(user);
    }
    return result;
  }

  private List<UserWithAnnotation> mockUserWithAnnotation(int count) {
    List<UserWithAnnotation> result = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      UserWithAnnotation user =
          new UserWithAnnotation("张三" + i, UUID.randomUUID().toString(), "ignore nickname", new Date());
      result.add(user);
    }
    return result;
  }


}
