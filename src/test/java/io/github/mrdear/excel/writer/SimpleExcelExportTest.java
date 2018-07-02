package io.github.mrdear.excel.writer;

import org.junit.Ignore;
import org.junit.Test;

import io.github.mrdear.excel.EasyExcel;
import io.github.mrdear.excel.domain.ExcelWriteContext;
import io.github.mrdear.excel.model.User;
import io.github.mrdear.excel.model.UserWithAnnotation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Quding Ding
 * @since 2018/6/29
 */
@Ignore
public class SimpleExcelExportTest {


  @Test
  public void testSimpleExport() {
    List<User> users = mockUser(5);
    EasyExcel.export("/tmp/test.xlsx")
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
    EasyExcel.export("/tmp/test.xlsx")
        .export(ExcelWriteContext.builder()
            .datasource(Collections.emptyList())
            .sheetName("user")
            .build())
        .write();
  }


  @Test
  public void testSimpleWithAnnotationExport() {
    List<UserWithAnnotation> users = mockUserWithAnnotation(5);
    EasyExcel.export("/tmp/test.xlsx")
        .export(ExcelWriteContext.builder()
            .datasource(users)
            .sheetName("user")
            .build())
        .write();
  }


  private List<User> mockUser(int count) {
    List<User> result = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      User user = new User("张三"+i, UUID.randomUUID().toString());
      result.add(user);
    }
    return result;
  }

  private List<UserWithAnnotation> mockUserWithAnnotation(int count) {
    List<UserWithAnnotation> result = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      UserWithAnnotation user =
          new UserWithAnnotation("张三" + i, UUID.randomUUID().toString(), new Date());
      result.add(user);
    }
    return result;
  }


}
