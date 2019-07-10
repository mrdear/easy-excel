package io.github.mrdear.excel.read;

import io.github.mrdear.excel.DocType;
import io.github.mrdear.excel.EasyExcel;
import io.github.mrdear.excel.model.UserWithAnnotation;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * @author Quding Ding
 * @since 2018/6/29
 */
public class CustomExcelReadExample1Test {

  @Test
  public void testCustomExcel() {
    InputStream inputStream = DocReaderTest.class
        .getClassLoader().getResourceAsStream("testCustomExcel.xlsx");
    DocReader reader = EasyExcel.read(inputStream, DocType.XLSX);

    List<UserWithAnnotation> sheet1Result = reader.resolve(ReadContextBuilder.<UserWithAnnotation>builder()
        .clazz(UserWithAnnotation.class)
        .headerStart(1)
        .excelReadSheetHook((sheet, context) -> {
          Row row = sheet.getRow(0);
          Assert.assertEquals(row.getCell(0).getStringCellValue(), "custom header");
        })
        .buildForExcel(0))
        .getData();

    Assert.assertEquals(sheet1Result.size(), 5);
    Assert.assertEquals(sheet1Result.get(1).getUsername(), "张三1");


    List<UserWithAnnotation> sheet2Result = reader.resolve(ReadContextBuilder.<UserWithAnnotation>builder()
        .clazz(UserWithAnnotation.class)
        .buildForExcel(1))
        .getData();

    Assert.assertEquals(sheet2Result.size(), 5);
    Assert.assertEquals(sheet2Result.get(1).getUsername(), "张三1");

  }


}
