package io.github.mrdear.excel.write;

import io.github.mrdear.excel.domain.ExcelWriteContext;

/**
 * @author Quding Ding
 * @since 2018/6/28
 */
public interface ExcelWriter {

  /**
   * 导出该表,此方法可以多次执行,每一次执行都是一张新表
   *
   * @param context 该表所需要的上下文信息
   * @return ExcelWriter
   */
  ExcelWriter export(ExcelWriteContext context);

  /**
   * 终结操作,写入对应的输出流汇总
   */
  void write();
}
