package io.github.mrdear.excel;

import io.github.mrdear.excel.exception.DocumentException;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletResponse;

/**
 * 方便操作HttpServletResponse的工具类
 */
public class ResponseHelper {

  public static OutputStream wrapper(HttpServletResponse resp, String fileName) {
    try {
      resp.setContentType("application/octet-stream");
      resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
      resp.setHeader("Content-Disposition", "attachment; filename=" + fileName);
      return resp.getOutputStream();
    } catch (IOException e) {
      throw new DocumentException(e);
    }
  }

}
