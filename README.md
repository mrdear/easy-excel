## 快速导出报表工具

造个轮子，便于导入和导出对应的excel报表。


## maven
```xml

<dependency>
  <groupId>io.github.mrdear</groupId>
  <artifactId>excel</artifactId>
  <version>0.0.4</version>
</dependency>

```

## 核心类

- **EasyExcel** : 入口类，所有对外的操作都是由该类发起，主要有export与read两个操作。
- **ExcelWriter**：导出类，其方法分为非终端操作与终端操作，终端操作会输出并关闭该流，非终端操作则可以继续接着读取，应对一张excel中含有多个sheet的情况。
- **ExcelReader**：读取类，与上述`ExcelWriter`一样的操作。
- **ExcelField**：修饰实体类注解，Excel中最麻烦的是header，因此提倡每一张报表单独对应一个POJO类，使用注解标识相应字段。
- **ExcelWriteContext**：针对导出过程中一张sheet的配置，使用Builder模式构建。
- **ExcelReadContext**：针对读取过程中一张sheet的配置，使用Builder模式构建。
- **ExcelIgnore**: 修饰字段,可以指定忽略某一字段

## Example

**实体类**
实体类使用注解标识字段，不使用的话则属性名会作为对应的`columnName`。
```java
public class UserWithAnnotation {

  @ExcelField(columnName = "用户名")
  private String username;

  @ExcelField(columnName = "用户密码")
  private String passwd;

  @ExcelField(columnName = "登录日期",
      writerConvert = DateToStringConvert.class,
      readerConvert = StringToDateConvert.class)
  private Date date;
}
```

### 单张表
![](http://oobu4m7ko.bkt.clouddn.com/1530326628.png?imageMogr2/thumbnail/!100p)

**export**

```java
 @Test
  public void testSimpleWithAnnotationExport() {
    List<UserWithAnnotation> users = mockUserWithAnnotation(5);
    EasyExcel.export("/tmp/test.xlsx")
        .export(ExcelWriteContext.builder()
            .datasource(users)
            .sheetName("user")
            .build())
        .write();
  }
```
**import**

```java
 @Test
  public void testRead2() {
    InputStream inputStream = SimpleExcelReaderTest.class
        .getClassLoader().getResourceAsStream("user2.xlsx");
    ExcelReader reader = EasyExcel.read(inputStream);

    List<UserWithAnnotation> result = reader.resolve(ExcelReadContext.<UserWithAnnotation>builder()
        .clazz(UserWithAnnotation.class)
        .build());

    Assert.assertEquals(result.size(), 5);
    Assert.assertEquals(result.get(0).getPasswd(), "0b6df627-5975-417b-abc9-1f2bad5ca1e2");
    Assert.assertEquals(result.get(1).getUsername(), "张三1");

    reader.close();
  }
```

### 多张表+自定义header
sheet1最顶部有自定义的title

![](http://oobu4m7ko.bkt.clouddn.com/1530326869.png?imageMogr2/thumbnail/!100p)

sheet2为普通表格

![](http://oobu4m7ko.bkt.clouddn.com/1530326912.png?imageMogr2/thumbnail/!100p)

**export**
由于自定义的title往往非常复杂且多变，很难做到通用，因此这里是直接抛出一个钩子，可以自己实现自己想要的任何操作。
```java
 @Test
  public void testCustom() {
    List<UserWithAnnotation> users = mockUserWithAnnotation(5);
    EasyExcel.export("/tmp/test.xlsx")
        .export(ExcelWriteContext.builder()
            .datasource(users)
            .sheetName("user1")
            .createSheetHook((sheet, context) -> {
              Row row = sheet.createRow(0);
              sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
              Cell cell = row.createCell(0);
              cell.setCellValue("custom header");
            })
            .startRow(1)
            .build())
        .export(ExcelWriteContext.builder()
            .datasource(users)
            .sheetName("user2")
            .build())
        .write();
  }
```

**import**
```java
 @Test
  public void testCustom() {
    InputStream inputStream = SimpleExcelReaderTest.class
        .getClassLoader().getResourceAsStream("user3.xlsx");
    ExcelReader reader = EasyExcel.read(inputStream);

    List<UserWithAnnotation> sheet1Result = reader.resolve(ExcelReadContext.<UserWithAnnotation>builder()
        .clazz(UserWithAnnotation.class)
        .headerStart(1)
        .sheetIndex(0)
        .readSheetHook((sheet, context) -> {
          Row row = sheet.getRow(0);
          Assert.assertEquals(row.getCell(0).getStringCellValue(), "custom header");
        })
        .build());

    Assert.assertEquals(sheet1Result.size(), 5);
    Assert.assertEquals(sheet1Result.get(1).getUsername(), "张三1");


    List<UserWithAnnotation> sheet2Result = reader.resolve(ExcelReadContext.<UserWithAnnotation>builder()
        .clazz(UserWithAnnotation.class)
        .sheetIndex(1)
        .build());

    Assert.assertEquals(sheet2Result.size(), 5);
    Assert.assertEquals(sheet2Result.get(1).getUsername(), "张三1");

  }
```

### 写入HttpServletResponse
提供`ResponseHelper`从`HttpServletResponse`获取对应的输出流，然后放入
```java
OutputStream outputStream = ResponseHelper.wrapper(response, "order.xlsx");
EasyExcel.export(outputStream)....
```