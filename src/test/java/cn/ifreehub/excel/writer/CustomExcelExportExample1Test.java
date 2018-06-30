package cn.ifreehub.excel.writer;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Ignore;
import org.junit.Test;

import cn.ifreehub.excel.EasyExcel;
import cn.ifreehub.excel.domain.ExcelWriteContext;
import cn.ifreehub.excel.model.UserWithAnnotation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Quding Ding
 * @since 2018/6/29
 */
@Ignore
public class CustomExcelExportExample1Test {

  @Test
  public void testCustom() {
    List<UserWithAnnotation> users = mockUserWithAnnotation(5);
    EasyExcel.export("/tmp/test.xlsx")
        .export(ExcelWriteContext.builder()
            .datasource(users)
            .sheetName("user1")
            .createSheetHook((sheet, context) -> {
              Row row = sheet.createRow(0);
              sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
              Cell cell = row.createCell(0);
              cell.setCellValue("custom header");
            })
            .startRow(1)
            .build())
        .export(ExcelWriteContext.builder()
            .datasource(users)
            .sheetName("user2")
            .build())
        .write();
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
