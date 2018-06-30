package cn.ifreehub.excel;

import cn.ifreehub.excel.domain.ExcelType;
import cn.ifreehub.excel.read.DefaultExcelReader;
import cn.ifreehub.excel.read.ExcelReader;
import cn.ifreehub.excel.write.ExcelWriter;
import cn.ifreehub.excel.write.DefaultExcelWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 整个操作的入口类
 * @author Quding Ding
 * @since 2018/6/27
 */
public class EasyExcel {

  /**
   * 快速模式,直接给定数据导出
   * 支持数据使用注解控制表单
   *
   * @return ExcelWriter
   */
  public static ExcelWriter export(ExcelType type, String fullFileName) {
    try {
      return new DefaultExcelWriter(type, new FileOutputStream(fullFileName));
    } catch (FileNotFoundException e) {
      throw new ExcelException(e);
    }
  }

  /**
   * 得到excel写入类
   * @param fullFileName 输出文件地址
   * @return ExcelWriter
   */
  public static ExcelWriter export(String fullFileName) {
    try {
      return new DefaultExcelWriter(ExcelType.XLSX, new FileOutputStream(fullFileName));
    } catch (FileNotFoundException e) {
      throw new ExcelException(e);
    }
  }

  /**
   * 导出,并写到对应的输出流中
   * @param outputStream 输出流
   * @return ExcelWriter
   */
  public static ExcelWriter export(ExcelType type, OutputStream outputStream) {
    return new DefaultExcelWriter(type, outputStream);
  }

  /**
   * 导出XLSX,并写到对应的输出流中
   * @param outputStream 输出流
   * @return ExcelWriter
   */
  public static ExcelWriter export(OutputStream outputStream) {
    return new DefaultExcelWriter(ExcelType.XLSX, outputStream);
  }

  /**
   * 从输入流中读取对应的文件
   * @param inputStream 输入流
   * @return 读取服务
   */
  public static ExcelReader read(InputStream inputStream) {
    return new DefaultExcelReader(inputStream);
  }
}
