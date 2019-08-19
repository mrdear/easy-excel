package io.github.mrdear.excel.writer;

import io.github.mrdear.excel.DocType;
import io.github.mrdear.excel.EasyDoc;
import io.github.mrdear.excel.model.UserWithAnnotation;
import io.github.mrdear.excel.read.DocReader;
import io.github.mrdear.excel.read.ReadContextBuilder;
import io.github.mrdear.excel.read.ReaderResolveResult;
import io.github.mrdear.excel.write.DocWriter;
import io.github.mrdear.excel.write.WriteContextBuilder;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Quding Ding
 * @since 2018/6/29
 */
public class CustomExcelExportExampleTest {
    private final String currentPath = CustomExcelExportExampleTest.class.getClassLoader().getResource(".").getPath();

    @Test
    public void testCustomMoreSheetExport() {
        List<UserWithAnnotation> users = mockUserWithAnnotation(5);

        String fullFileName = currentPath + "/testCustomMoreSheetExport.xlsx";

        try (DocWriter writer = EasyDoc.export(DocType.XLSX, fullFileName)){

            writer.export(WriteContextBuilder.builder()
                    .dataSource(users)
                    .createSheetHook((sheet, context) -> {
                        Row row = sheet.createRow(0);
                        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
                        Cell cell = row.createCell(0);
                        cell.setCellValue("custom header");
                    })
                    .startRow(1)
                    .buildForExcel("user1"))
                .export(WriteContextBuilder.builder()
                    .dataSource(users)
                    .buildForExcel("user2"))
                .writeAndFlush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (DocReader reader = EasyDoc.read(fullFileName)) {
            ReaderResolveResult<UserWithAnnotation> user = reader.resolve(ReadContextBuilder
                .<UserWithAnnotation>builder()
                .clazz(UserWithAnnotation.class)
                .headerStart(1)
                .buildForExcel(0)
            );

            Assert.assertEquals(5, user.size());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private List<UserWithAnnotation> mockUserWithAnnotation(int count) {
        List<UserWithAnnotation> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            UserWithAnnotation user =
                new UserWithAnnotation("张三" + i, UUID.randomUUID().toString(), "ignore nickname", null);
            result.add(user);
        }
        return result;
    }


}
