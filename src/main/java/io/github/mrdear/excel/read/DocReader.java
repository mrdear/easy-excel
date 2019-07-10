package io.github.mrdear.excel.read;

import io.github.mrdear.excel.domain.ImportDomain;

/**
 * 文档读取入口
 * @author Quding Ding
 * @since 2018/6/29
 */
public interface DocReader extends AutoCloseable {

  /**
   * 解析一张sheet
   *
   * @param context 该sheet对应的上下文
   * @param <T>     要解析出来的类型
   * @return 结果，包含正确数据与错误数据（格式/非业务相关）
   */
  <T> ImportDomain<T> resolve(ReadContext<T> context);

  /**
   * 读取完释放资源
   */
  @Override
  default void close() throws Exception {

  }

}
