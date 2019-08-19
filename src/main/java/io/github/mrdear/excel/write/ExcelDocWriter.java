package io.github.mrdear.excel.write;

import io.github.mrdear.excel.DocType;
import io.github.mrdear.excel.domain.WriterHeader;
import io.github.mrdear.excel.exception.DocumentException;
import io.github.mrdear.excel.internal.util.Assert;
import io.github.mrdear.excel.internal.util.DocBeanUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Excel 写入实现
 *
 * @author Quding Ding
 * @since 2018/6/28
 */
public class ExcelDocWriter implements DocWriter {

    /**
     * 工作簿
     */
    private Workbook workbook;
    /**
     * 输出目标
     */
    private OutputStream outputStream;

    public ExcelDocWriter(DocType docType, OutputStream outputStream) {
        Assert.notNull(docType, "docType can't be null");
        Assert.notNull(outputStream, "outputStream can't be null");
        this.workbook = docType.workbook();
        this.outputStream = outputStream;
    }

    /**
     * 把数据写入该表中
     *
     * @return 该实例
     */
    @Override
    @SuppressWarnings("unchecked")
    public ExcelDocWriter export(WriteContext context) {
        if (!(context instanceof ExcelWriteContext)) {
            throw new DocumentException("ExcelDocWriter only support ExcelWriteContext");
        }

        ExcelWriteContext writeContext = (ExcelWriteContext) context;

        Sheet sheet = StringUtils.isEmpty(writeContext.getSheetName())
            ? workbook.createSheet()
            : workbook.createSheet(writeContext.getSheetName());

        // custom 处理
        writeContext.getCreateSheetHook().accept(sheet, writeContext);

        // 写表头
        int startRow = writeContext.getStartRow();
        Row headerRow = sheet.createRow(startRow++);
        int[] tempCol = {0};
        LinkedHashMap<String, WriterHeader> headers = writeContext.getHeaders();
        headers.forEach((k, v) -> {
            Cell cell = headerRow.createCell(tempCol[0]++);
            cell.setCellValue(v.getName());
        });

        // 依次写入每一行
        for (Map<String, Object> rowData : writeContext.getDatasource()) {
            Row row = sheet.createRow(startRow++);
            tempCol[0] = 0;
            headers.forEach((k, v) -> {
                Cell cell = row.createCell(tempCol[0]++);
                Object value = rowData.get(k);

                String realValue = null;
                try {
                    realValue = value == null ? null : v.getConvert().to(value);
                } catch (Exception e) {
                    throw new DocumentException(e, "convert fail in header=%s, value=%s, convert=%s",
                        k, value, v.getConvert().getClass().getSimpleName());
                }
                DocBeanUtils.autoFitCell(cell, realValue);
            });
        }

        return this;
    }


    @Override
    public void writeAndFlush() {
        try {
            this.workbook.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            throw new DocumentException(e);
        }
    }

    @Override
    public void close() throws IOException {
        if (null != workbook) {
            this.workbook.close();
        }

        if (null != outputStream) {
            // 释放资源
            outputStream.flush();
            outputStream.close();
        }

    }

}
