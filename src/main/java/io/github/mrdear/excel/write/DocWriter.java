package io.github.mrdear.excel.write;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author Quding Ding
 * @since 2018/6/28
 */
public interface DocWriter extends Closeable {

  /**
   * 导出该表,此方法可以多次执行,每一次执行都是一张新表
   *
   * @param context 该表所需要的上下文信息
   * @return DocWriter
   */
  DocWriter export(WriteContext context);

  /**
   * 终结操作,写入对应的输出流汇总
   */
  void writeAndFlush() throws IOException;

  /**
   * 关闭输出流
   */
  @Override
  default void close() throws IOException {

  }
}
