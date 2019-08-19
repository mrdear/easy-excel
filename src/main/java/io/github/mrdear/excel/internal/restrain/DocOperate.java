package io.github.mrdear.excel.internal.restrain;

import io.github.mrdear.excel.read.DocReader;
import io.github.mrdear.excel.write.DocWriter;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Quding Ding
 * @since 2018/6/28
 */
public interface DocOperate {

  /**
   * 创建对应的workbook
   * @return workbook
   */
  Workbook workbook();

  /**
   * 创建文档读取实例
   * @param in 输入流
   * @return 对应实例
   */
  DocReader createReader(InputStream in);

  /**
   * 创建写入数据辅助类
   * @param stream 输出流
   * @return 创建结果
   */
  DocWriter createWriter(OutputStream stream);

}

