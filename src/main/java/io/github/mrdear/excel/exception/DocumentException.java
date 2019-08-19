package io.github.mrdear.excel.exception;

/**
 * @author Quding Ding
 * @since 2018/6/28
 */
public class DocumentException extends RuntimeException {

  public DocumentException(Throwable cause) {
    super(cause);
  }

  public DocumentException(String message) {
    super(message);
  }

  public DocumentException(String message, Object... args) {
    super(String.format(message, args));
  }

  public DocumentException(Throwable cause, String message, Object... args) {
    super(String.format(message, args), cause);
  }

}
