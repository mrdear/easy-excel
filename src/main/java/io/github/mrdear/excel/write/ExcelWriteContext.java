package io.github.mrdear.excel.write;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import org.apache.poi.ss.usermodel.Sheet;

import java.util.function.BiConsumer;

/**
 * Excel读写时上下文
 * @author Quding Ding
 * @since 2019-08-19
 */
@Setter(AccessLevel.PACKAGE)
public class ExcelWriteContext extends WriteContext {

    /**
     * 创建工作目录后的hook
     */
    @Getter
    private BiConsumer<Sheet, WriteContext> createSheetHook = (w, v) -> { };
    /**
     * 起始行
     */
    @Getter
    private int startRow = 0;

    /**
     * 表名
     */
    @Getter
    private String sheetName;



}
