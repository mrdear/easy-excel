## 快速导出报表工具

造个轮子，便于导入和导出对应的excel报表。


## maven

**注意: 0.2.0版本与之前的并不相兼容**

```xml

<dependency>
  <groupId>io.github.mrdear</groupId>
  <artifactId>excel</artifactId>
  <version>0.2.0-SNAPSHOT</version>
</dependency>

```

## 核心类

- **EasyDoc** : 入口类，所有对外的操作都是由该类发起，主要有export与read两个操作。
- **DocWriter**：导出类，其方法分为非终端操作与终端操作，终端操作会输出并关闭该流，非终端操作则可以继续接着读取，应对一张excel中含有多个sheet的情况。
- **DocReader**：读取类，与上述`ExcelWriter`一样的操作。
- **DocField**：修饰实体类注解，Excel中最麻烦的是header，因此提倡每一张报表单独对应一个POJO类，使用注解标识相应字段。
- **DocIgnore**: 修饰字段,可以指定忽略某一字段
- **WriteContext**：针对导出过程中一张sheet的配置，使用Builder模式构建。
- **ReadContext**：针对读取过程中一张sheet的配置，使用Builder模式构建。

## Example

**实体类**
实体类使用注解标识字段，不使用的话则属性名会作为对应的`columnName`。
```java
public class UserWithAnnotation {

  @DocField(columnName = "用户名")
  private String username;

  @DocField(columnName = "用户密码")
  private String passwd;

  @DocField(columnName = "登录日期", convert = YmdDateConverter.class)
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
      try (DocWriter writer = EasyDoc.export(DocType.XLSX, currentPath + "/testSimpleWithAnnotationExport.xlsx")) {
          writer
              .export(WriteContextBuilder.builder()
                  .dataSource(users)
                  .buildForExcel("user"))
              .writeAndFlush();
      } catch (IOException e) {
          e.printStackTrace();
      }
  }
```
**import**

```java
  @Test
   public void testReadWithAnnotation() throws Exception {
     InputStream inputStream = DocReaderTest.class
         .getClassLoader().getResourceAsStream("testReadWithAnnotation.xlsx");
 
     try (DocReader reader = EasyDoc.read(DocType.XLSX, inputStream)){
       List<UserWithAnnotation> result = reader.resolve(ReadContextBuilder.<UserWithAnnotation>builder()
           .clazz(UserWithAnnotation.class)
           .buildForExcel())
           .getData();
 
       Assert.assertEquals(result.size(), 5);
       Assert.assertEquals(result.get(0).getPasswd(), "0b6df627-5975-417b-abc9-1f2bad5ca1e2");
       Assert.assertEquals(result.get(1).getUsername(), "张三1");
       Assert.assertNull(result.get(0).getNickName());
     }
 
   }
```

### 多张表+自定义header
sheet1最顶部有自定义的title

![](http://oobu4m7ko.bkt.clouddn.com/1530326869.png?imageMogr2/thumbnail/!100p)

sheet2为普通表格

![](http://oobu4m7ko.bkt.clouddn.com/1530326912.png?imageMogr2/thumbnail/!100p)

由于自定义的title往往非常复杂且多变，很难做到通用，因此这里是直接抛出一个钩子，可以自己实现自己想要的任何操作。

**export**

```java
    @Test
    public void testCustomMoreSheetExport() {
        List<UserWithAnnotation> users = mockUserWithAnnotation(5);

        String fullFileName = currentPath + "/testCustomMoreSheetExport.xlsx";

        try (DocWriter writer = EasyDoc.export(DocType.XLSX, fullFileName)){

            writer.export(WriteContextBuilder.builder()
                    .dataSource(users)
                    .createSheetHook((sheet, context) -> {
                        Row row = sheet.createRow(0);
                        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
                        Cell cell = row.createCell(0);
                        cell.setCellValue("custom header");
                    })
                    .startRow(1)
                    .buildForExcel("user1"))
                .export(WriteContextBuilder.builder()
                    .dataSource(users)
                    .buildForExcel("user2"))
                .writeAndFlush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (DocReader reader = EasyDoc.read(fullFileName)) {
            ReaderResolveResult<UserWithAnnotation> user = reader.resolve(ReadContextBuilder
                .<UserWithAnnotation>builder()
                .clazz(UserWithAnnotation.class)
                .headerStart(1)
                .buildForExcel(0)
            );

            Assert.assertEquals(5, user.size());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
```

**import**
```java
  @Test
  public void testCustomExcel() {
    InputStream inputStream = DocReaderTest.class
        .getClassLoader().getResourceAsStream("testCustomExcel.xlsx");
    DocReader reader = EasyDoc.read(DocType.XLSX, inputStream);

    List<UserWithAnnotation> sheet1Result = reader.resolve(ReadContextBuilder.<UserWithAnnotation>builder()
        .clazz(UserWithAnnotation.class)
        .headerStart(1)
        .excelReadSheetHook((sheet, context) -> {
          Row row = sheet.getRow(0);
          Assert.assertEquals(row.getCell(0).getStringCellValue(), "custom header");
        })
        .buildForExcel(0))
        .getData();

    Assert.assertEquals(sheet1Result.size(), 5);
    Assert.assertEquals(sheet1Result.get(1).getUsername(), "张三1");


    List<UserWithAnnotation> sheet2Result = reader.resolve(ReadContextBuilder.<UserWithAnnotation>builder()
        .clazz(UserWithAnnotation.class)
        .buildForExcel(1))
        .getData();

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