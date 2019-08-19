package io.github.mrdear.excel.read;

import lombok.AccessLevel;
import lombok.Setter;

import org.apache.poi.ss.usermodel.Sheet;

import java.util.function.BiConsumer;

/**
 * excel读取时参数上下文
 * @author Quding Ding
 * @since 2019-07-09
 */
@Setter(AccessLevel.PACKAGE)
public class ExcelReadContext<T> extends ReadContext<T> {

    /**
     * 所在sheet索引
     */
    private int sheetIndex = 0;

    /**
     * 读取sheet之后的操作
     */
    private BiConsumer<Sheet, ReadContext> readSheetHook = (w, v) -> { };


    public int getSheetIndex() {
        return sheetIndex;
    }

    public BiConsumer<Sheet, ReadContext> getReadSheetHook() {
        return readSheetHook;
    }
}
