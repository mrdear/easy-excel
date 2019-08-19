package io.github.mrdear.excel.write;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import io.github.mrdear.excel.domain.WriterHeader;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * excel上下文
 * 针对每一个sheet
 *
 * @author Quding Ding
 * @since 2018/6/27
 */
@Setter(AccessLevel.PACKAGE)
public abstract class WriteContext {
  /**
   * 数据源
   */
  @Getter
  private List<Map<String, Object>> datasource;
  /**
   * excel头
   */
  @Getter
  private LinkedHashMap<String, WriterHeader> headers;

}
