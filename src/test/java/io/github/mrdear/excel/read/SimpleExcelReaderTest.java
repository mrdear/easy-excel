package io.github.mrdear.excel.read;

import io.github.mrdear.excel.EasyExcel;
import io.github.mrdear.excel.domain.ExcelReadContext;
import io.github.mrdear.excel.model.Id;
import io.github.mrdear.excel.model.User;
import io.github.mrdear.excel.model.UserWithAnnotation;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * @author Quding Ding
 * @since 2018/6/30
 */
public class SimpleExcelReaderTest {


  @Test
  public void testRead() {
    InputStream inputStream = SimpleExcelReaderTest.class
        .getClassLoader().getResourceAsStream("user.xlsx");

    try (ExcelReader reader = EasyExcel.read(inputStream)) {
      List<User> result = reader.resolve(ExcelReadContext.<User>builder()
          .clazz(User.class)
          .build());

      Assert.assertEquals(result.size(), 5);
      Assert.assertEquals(result.get(0).getPasswd(), "9277656f-b228-4d53-a35b-b6cffc26fc9e");
      Assert.assertEquals(result.get(1).getUsername(), "张三1");
    }

  }


  @Test
  public void testReadEmpty() {
    InputStream inputStream = SimpleExcelReaderTest.class
        .getClassLoader().getResourceAsStream("userempty.xlsx");
    ExcelReader reader = EasyExcel.read(inputStream);

    List<User> result = reader.resolve(ExcelReadContext.<User>builder()
        .clazz(User.class)
        .build());
    Assert.assertEquals(result.size(), 0);
  }


  @Test
  public void testRead2() {
    InputStream inputStream = SimpleExcelReaderTest.class
        .getClassLoader().getResourceAsStream("user2.xlsx");
    ExcelReader reader = EasyExcel.read(inputStream);

    List<UserWithAnnotation> result = reader.resolve(ExcelReadContext.<UserWithAnnotation>builder()
        .clazz(UserWithAnnotation.class)
        .build());

    Assert.assertEquals(result.size(), 5);
    Assert.assertEquals(result.get(0).getPasswd(), "0b6df627-5975-417b-abc9-1f2bad5ca1e2");
    Assert.assertEquals(result.get(1).getUsername(), "张三1");

    reader.close();
  }


  @Test
  public void testRead3() {
    InputStream inputStream = SimpleExcelReaderTest.class
        .getClassLoader().getResourceAsStream("ids.xlsx");
    ExcelReader reader = EasyExcel.read(inputStream);

    List<Id> result = reader.resolve(ExcelReadContext.<Id>builder()
        .clazz(Id.class)
        .build());

    Assert.assertEquals(1332, result.size());
    Assert.assertTrue(!result.isEmpty());
    reader.close();
  }

}
