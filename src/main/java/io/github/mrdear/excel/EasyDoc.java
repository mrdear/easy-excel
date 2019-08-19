package io.github.mrdear.excel;

import io.github.mrdear.excel.exception.DocumentException;
import io.github.mrdear.excel.read.DocReader;
import io.github.mrdear.excel.write.DocWriter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 整个操作的入口类
 *
 * @author Quding Ding
 * @since 2018/6/27
 */
public class EasyDoc {

  /**
   * 快速模式,直接给定数据导出
   * 支持数据使用注解控制表单
   *
   * @return DocWriter
   */
  public static DocWriter export(DocType type, String fullFileName) {
    try {
      return type.createWriter(new FileOutputStream(fullFileName));
    } catch (FileNotFoundException e) {
      throw new DocumentException(e);
    }
  }


  /**
   * 导出,并写到对应的输出流中
   *
   * @param outputStream 输出流
   * @return DocWriter
   */
  public static DocWriter export(DocType type, OutputStream outputStream) {
    return type.createWriter(outputStream);
  }


  /**
   * 从输入流中读取对应的文件
   *
   * @param inputStream 输入流
   * @return 读取服务
   */
  public static DocReader read(DocType docType, InputStream inputStream) {
    return docType.createReader(inputStream);
  }

  /**
   * 从文件路径中读取对应的文件
   *
   * @param fullFilePath 文件全路径名
   * @return 读取服务
   */
  public static DocReader read(String fullFilePath) {
    FileInputStream inputStream;
    try {
      inputStream = new FileInputStream(fullFilePath);
    } catch (FileNotFoundException e) {
      throw new DocumentException(e);
    }
    int index = fullFilePath.lastIndexOf('.');
    DocType docType = DocType.autoSelectBySuffix(fullFilePath.substring(index + 1));

    return docType.createReader(inputStream);
  }


}
