package io.github.mrdear.excel;

import io.github.mrdear.excel.internal.restrain.DocOperate;
import io.github.mrdear.excel.read.DocReader;
import io.github.mrdear.excel.read.ExcelDocReader;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;

/**
 * 标识文档类型
 *
 * @author Quding Ding
 * @since 2018/2/6
 */
public enum DocType implements DocOperate {
    /**
     * xls
     */
    XLS {
        @Override
        public Workbook workbook() {
            return new HSSFWorkbook();
        }

        @Override
        public DocReader createReader(InputStream in) {
            return new ExcelDocReader(in);
        }
    },
    /**
     * xlsx
     */
    XLSX {
        @Override
        public Workbook workbook() {
            XSSFWorkbook workbook = new XSSFWorkbook();
            return new SXSSFWorkbook(workbook, 100);
        }

        @Override
        public DocReader createReader(InputStream in) {
            return new ExcelDocReader(in);
        }
    },
    ;

    /**
     * 根据后缀自动选择对应类型
     * @param suffix 后缀
     * @return 类型
     */
    public static DocType autoSelectBySuffix(String suffix) {
        return DocType.valueOf(suffix.toUpperCase());
    }
}
