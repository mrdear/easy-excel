package cn.ifreehub.excel.read;

import org.apache.poi.ss.usermodel.Row;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import cn.ifreehub.excel.EasyExcel;
import cn.ifreehub.excel.domain.ExcelReadContext;
import cn.ifreehub.excel.model.UserWithAnnotation;

import java.io.InputStream;
import java.util.List;

/**
 * @author Quding Ding
 * @since 2018/6/29
 */
@Ignore
public class CustomExcelReadExample1Test {

  @Test
  public void testCustom() {
    InputStream inputStream = SimpleExcelReaderTest.class
        .getClassLoader().getResourceAsStream("user3.xlsx");
    ExcelReader reader = EasyExcel.read(inputStream);

    List<UserWithAnnotation> sheet1Result = reader.resolve(ExcelReadContext.<UserWithAnnotation>builder()
        .clazz(UserWithAnnotation.class)
        .headerStart(1)
        .sheetIndex(0)
        .readSheetHook((sheet, context) -> {
          Row row = sheet.getRow(0);
          Assert.assertEquals(row.getCell(0).getStringCellValue(), "custom header");
        })
        .build());

    Assert.assertEquals(sheet1Result.size(), 5);
    Assert.assertEquals(sheet1Result.get(1).getUsername(), "张三1");


    List<UserWithAnnotation> sheet2Result = reader.resolve(ExcelReadContext.<UserWithAnnotation>builder()
        .clazz(UserWithAnnotation.class)
        .sheetIndex(1)
        .build());

    Assert.assertEquals(sheet2Result.size(), 5);
    Assert.assertEquals(sheet2Result.get(1).getUsername(), "张三1");

  }


}
