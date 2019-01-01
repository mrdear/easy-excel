package io.github.mrdear.excel.read;

import io.github.mrdear.excel.domain.ExcelReadContext;

import java.util.List;

/**
 * @author Quding Ding
 * @since 2018/6/29
 */
public interface ExcelReader extends AutoCloseable {

  /**
   * 解析一张sheet
   * @param context 该sheet对应的上下文
   * @param <T> 要解析出来的类型
   * @return 结果
   */
  <T> List<T> resolve(ExcelReadContext<T> context);

  /**
   * 读取完释放资源
   */
  @Override
  void close();
}
