package io.github.mrdear.excel.domain.convert;

/**
 * 转换功能接口，默认什么都不做
 *
 * @param <Form> 字段的类型，可以是任何类型
 * @param <To>   单元格的值，一定是 String 类型
 * @author rxliuli
 */
public interface IConverter<Form, To> {
  /**
   * 将对象字段转换为数据表格单元格的值
   *
   * @param form 字段的值，不一定是 String
   * @return 单元格的值，一定是 String 类型的值
   */
  To to(Form form);

  /**
   * 将表格单元格的值转换为对象字段
   *
   * @param to 单元格的值
   * @return 字段的值
   */
  Form from(To to);
}
