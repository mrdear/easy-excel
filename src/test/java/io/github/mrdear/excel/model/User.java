package io.github.mrdear.excel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.github.mrdear.excel.annotation.DocField;

/**
 * @author Quding Ding
 * @since 2018/6/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

  private String username;

  @DocField
  private String passwd;


  @Override
  public String toString() {
    return "User{" +
        "username='" + username + '\'' +
        ", passwd='" + passwd + '\'' +
        '}';
  }
}
