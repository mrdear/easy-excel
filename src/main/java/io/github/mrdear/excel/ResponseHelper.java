package io.github.mrdear.excel;

import org.apache.commons.codec.Charsets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

/**
 * 方便操作HttpServletResponse的工具类
 */
public class ResponseHelper {

  public static OutputStream wrapper(HttpServletResponse resp, String fileName) {
    try {
      resp.setContentType("application/octet-stream");
      resp.setCharacterEncoding(Charsets.UTF_8.name());
      resp.setHeader("Content-Disposition", "attachment; filename=" + fileName);
      return resp.getOutputStream();
    } catch (IOException e) {
      throw new ExcelException(e);
    }
  }

}
