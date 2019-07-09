package io.github.mrdear.excel.internal.util;

import org.apache.poi.ss.usermodel.Cell;

/**
 * POI操作相关工具类
 * @author Quding Ding
 * @since 2019-07-09
 */
public class PoiUtils {

    /**
     * 获取指定单元格的值
     * @param cell 指定单元格
     * @return 值
     */
    public static String getColumnValue(Cell cell) {
        switch (cell.getCellTypeEnum()) {
            case BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BLANK:
                return "";
            default:
                return cell.getStringCellValue();
        }

    }

}
