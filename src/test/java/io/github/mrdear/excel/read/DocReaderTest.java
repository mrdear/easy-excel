package io.github.mrdear.excel.read;

import io.github.mrdear.excel.DocType;
import io.github.mrdear.excel.EasyDoc;
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
public class DocReaderTest {


  @Test
  public void testNormalRead() {
    InputStream inputStream = DocReaderTest.class
        .getClassLoader().getResourceAsStream("testNormalRead.xlsx");

    try (DocReader reader = EasyDoc.read(DocType.XLSX, inputStream)) {
      List<User> result = reader.resolve(ReadContextBuilder.<User>builder()
          .clazz(User.class)
          .buildForExcel())
          .getData();

      Assert.assertEquals(result.size(), 5);
      Assert.assertEquals(result.get(0).getPasswd(), "9277656f-b228-4d53-a35b-b6cffc26fc9e");
      Assert.assertEquals(result.get(1).getUsername(), "张三1");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  @Test
  public void testReadEmpty() {
    InputStream inputStream = DocReaderTest.class
        .getClassLoader().getResourceAsStream("testReadEmpty.xlsx");
    DocReader reader = EasyDoc.read(DocType.XLSX, inputStream);

    List<User> result = reader.resolve(ReadContextBuilder.<User>builder()
        .clazz(User.class)
        .buildForExcel())
        .getData();
    Assert.assertEquals(result.size(), 0);
  }


  @Test
  public void testReadWithAnnotation() throws Exception {
    InputStream inputStream = DocReaderTest.class
        .getClassLoader().getResourceAsStream("testReadWithAnnotation.xlsx");

    try (DocReader reader = EasyDoc.read(DocType.XLSX, inputStream)){
      List<UserWithAnnotation> result = reader.resolve(ReadContextBuilder.<UserWithAnnotation>builder()
          .clazz(UserWithAnnotation.class)
          .buildForExcel())
          .getData();

      Assert.assertEquals(result.size(), 5);
      Assert.assertEquals(result.get(0).getPasswd(), "0b6df627-5975-417b-abc9-1f2bad5ca1e2");
      Assert.assertEquals(result.get(1).getUsername(), "张三1");
      Assert.assertNull(result.get(0).getNickName());
    }

  }


  @Test
  public void testReadIdsCount() throws Exception {
    InputStream inputStream = DocReaderTest.class
        .getClassLoader().getResourceAsStream("testReadIdsCount.xlsx");
    try (DocReader reader = EasyDoc.read(DocType.XLSX, inputStream)){
      List<Id> result = reader.resolve(ReadContextBuilder.<Id>builder()
          .clazz(Id.class)
          .buildForExcel())
          .getData();

      Assert.assertEquals(1332, result.size());
      Assert.assertFalse(result.isEmpty());
    }

  }

}
