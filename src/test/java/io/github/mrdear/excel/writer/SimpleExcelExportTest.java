package io.github.mrdear.excel.writer;

import io.github.mrdear.excel.DocType;
import io.github.mrdear.excel.EasyDoc;
import io.github.mrdear.excel.model.User;
import io.github.mrdear.excel.model.UserWithAnnotation;
import io.github.mrdear.excel.write.DocWriter;
import io.github.mrdear.excel.write.WriteContextBuilder;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Quding Ding
 * @since 2018/6/29
 */
public class SimpleExcelExportTest {
    private final String currentPath = SimpleExcelExportTest.class.getClassLoader().getResource(".").getPath();

    @Test
    public void testSimpleExport() {
        List<User> users = mockUser(5);
        try (DocWriter docWriter = EasyDoc.export(DocType.XLSX, currentPath + "/testSimpleExport.xlsx")) {
            docWriter
                .export(WriteContextBuilder.builder()
                    .dataSource(users)
                    .buildForExcel("user"))
                .writeAndFlush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 空数据应该导出空表
     */
  @Test
  public void testEmptyExcel() {
      try (DocWriter writer = EasyDoc.export(DocType.XLSX, currentPath + "/testEmptyExcel.xlsx")) {
          writer
              .export(WriteContextBuilder.builder()
                  .dataSource(Collections.emptyList())
                  .buildForExcel("user"))
              .writeAndFlush();
      } catch (IOException e) {
          e.printStackTrace();
      }
  }


  @Test
  public void testSimpleWithAnnotationExport() {
    List<UserWithAnnotation> users = mockUserWithAnnotation(5);
      try (DocWriter writer = EasyDoc.export(DocType.XLSX, currentPath + "/testSimpleWithAnnotationExport.xlsx")) {
          writer
              .export(WriteContextBuilder.builder()
                  .dataSource(users)
                  .buildForExcel("user"))
              .writeAndFlush();
      } catch (IOException e) {
          e.printStackTrace();
      }
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
