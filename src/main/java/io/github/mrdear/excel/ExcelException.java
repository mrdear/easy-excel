package io.github.mrdear.excel;

/**
 * @author Quding Ding
 * @since 2018/6/28
 */
public class ExcelException extends RuntimeException {

  public ExcelException(Throwable cause) {
    super(cause);
  }

  public ExcelException(String message) {
    super(message);
  }

  public ExcelException(String message, Throwable cause) {
    super(message, cause);
  }
}
