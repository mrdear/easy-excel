package io.github.mrdear.excel.internal.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 父类相关的反射工具
 *
 * @author rxliuli
 */
public class ClassUtil {
  /**
   * 获取到当前类的所有父类
   * 默认不返回包含 {@link Object} 并且不包含自身的列表
   *
   * @param clazz 当前类型
   * @return 所有父类的列表
   */
  public static List<Class<?>> getSuperClassList(Class<?> clazz) {
    return getSuperClassList(clazz, false, false);
  }

  /**
   * 获取到当前类的所有父类
   *
   * @param clazz           当前类型
   * @param isContainIfSelf 是否包含本身类型
   * @param isContainObject 是否包含 {@link Object} 类
   * @return 所有父类的列表
   */
  public static List<Class<?>> getSuperClassList(Class<?> clazz, boolean isContainIfSelf, boolean isContainObject) {
    List<Class<?>> listSuperClass = new ArrayList<>();
    if (isContainIfSelf) {
      listSuperClass.add(clazz);
    }
    Class<?> superclass = clazz.getSuperclass();
    while (superclass != null) {
      if (!isContainObject && Object.class.getName().equals(superclass.getName())) {
        break;
      }
      listSuperClass.add(superclass);
      superclass = superclass.getSuperclass();
    }
    return listSuperClass;
  }

  /**
   * 递归获取父类或者接口类
   * 最后返回不重复的 {@link Class} 对象集合
   *
   * @param clazz 当前类型
   * @return 所有父类/接口的集合
   */
  public static Set<Class<?>> getSuperList(Class<?> clazz) {
    final Set<Class<?>> set = new LinkedHashSet<>();
    final Class<?> superclass = clazz.getSuperclass();
    final Class<?>[] interfaces = clazz.getInterfaces();
    // 如果父类不为空就追加到集合中
    if (superclass != null && !Object.class.equals(superclass)) {
      set.add(superclass);
      set.addAll(getSuperList(superclass));
    }
    // 如果父类接口不为空就追加到集合中
    if (interfaces.length != 0) {
      final List<Class<?>> interfaceList = Arrays.asList(interfaces);
      set.addAll(interfaceList);
      set.addAll(interfaceList.stream()
          .flatMap(ac -> getSuperList(ac).stream())
          .collect(Collectors.toSet()));
    }
    return set;
  }

  /**
   * 获取两个类的最小公共父类型
   * 默认返回包含 Object 并且包含自身的最小父类(至少有一个是 Object)
   *
   * @param classA 类型 A
   * @param classB 类型 B
   * @return 最小公共父类
   */
  public static Class<?> getMiniParent(Class<?> classA, Class<?> classB) {
    return getMiniParent(classA, classB, true, true);
  }

  /**
   * 获取两个类的最小公共父类型
   *
   * @param classA          类型 A
   * @param classB          类型 B
   * @param isContainIfSelf 是否包含本身类型
   * @param isContainObject 是否包含 Object 类
   * @return 最小公共父类
   */
  public static Class<?> getMiniParent(Class<?> classA, Class<?> classB, boolean isContainIfSelf, boolean isContainObject) {
    List<Class<?>> classAParents = getSuperClassList(classA, isContainIfSelf, isContainObject);
    Set<Class<?>> classBParents = new HashSet<>(getSuperClassList(classB, isContainIfSelf, isContainObject));
    for (Class<?> classAParent : classAParents) {
      if (classBParents.contains(classAParent)) {
        return classAParent;
      }
    }
    return null;
  }

  /**
   * 循环向上转型, 获取对象的 DeclaredMethod
   *
   * @param object         : 子类对象
   * @param methodName     : 父类中的方法名
   * @param parameterTypes : 父类中的方法参数类型
   * @return 父类中的方法对象
   */
  public static Method getDeclaredMethod(Object object, String methodName, Class<?>... parameterTypes) {
    Method method;
    for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
      try {
        method = clazz.getDeclaredMethod(methodName, parameterTypes);
        return method;
      } catch (Exception e) {
        // 这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
        // 如果这里的异常打印或者往外抛，则就不会执行 clazz = clazz.getSuperclass(), 最后就不会进入到父类中了
      }
    }
    return null;
  }

  /**
   * 直接调用对象方法, 而忽略修饰符 (private, protected, default)
   *
   * @param object         : 子类对象
   * @param methodName     : 父类中的方法名
   * @param parameterTypes : 父类中的方法参数类型
   * @param parameters     : 父类中的方法参数
   * @return 父类中方法的执行结果
   */
  public static <T> T invokeMethod(Object object, String methodName, Class<?>[] parameterTypes, Object[] parameters) {
    // 根据 对象、方法名和对应的方法参数 通过反射 调用上面的方法获取 Method 对象
    Method method = getDeclaredMethod(object, methodName, parameterTypes);

    try {
      if (method != null) {
        // 抑制 Java 对方法进行检查, 主要是针对私有方法而言
        method.setAccessible(true);
        // 调用 object 的 method 所代表的方法，其方法的参数是 parameters
        final Object result = method.invoke(object, parameters);
        return (T) result;
      }
    } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException ignored) {
    }
    return null;
  }

  /**
   * 循环向上转型, 获取对象的 DeclaredField
   *
   * @param object    : 子类对象
   * @param fieldName : 父类中的属性名
   * @return 父类中的属性对象
   */
  public static Field getDeclaredField(Object object, String fieldName) {
    Class<?> clazz = object.getClass();
    for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
      try {
        return clazz.getDeclaredField(fieldName);
      } catch (Exception e) {
        // 这里什么都不要做！并且这里的异常必须这样写，不能抛出去。
        // 如果这里的异常打印或者往外抛，则就不会执行 clazz = clazz.getSuperclass(), 最后就不会进入到父类中了
      }
    }
    return null;
  }

  /**
   * 直接设置对象属性值, 忽略 private/protected 修饰符, 也不经过 setter
   *
   * @param object    : 子类对象
   * @param fieldName : 父类中的属性名
   * @param value     : 将要设置的值
   */
  public static boolean setFieldValue(Object object, String fieldName, Object value) {
    // 根据 对象和属性名通过反射 调用上面的方法获取 Field 对象
    Field field = getDeclaredField(object, fieldName);
    try {
      if (field != null) {
        // 抑制 Java 对其的检查
        field.setAccessible(true);
        // 将 object 中 field 所代表的值 设置为 value
        field.set(object, value);
      }
      return true;
    } catch (IllegalArgumentException | IllegalAccessException e) {
      return false;
    }
  }

  /**
   * 直接读取对象的属性值, 忽略 private/protected 修饰符, 也不经过 getter
   *
   * @param object    : 子类对象
   * @param fieldName : 父类中的属性名
   * @return : 父类中的属性值
   */
  public static Object getFieldValue(Object object, String fieldName) {

    // 根据 对象和属性名通过反射 调用上面的方法获取 Field 对象
    Field field = getDeclaredField(object, fieldName);
    try {
      if (field != null) {
        // 抑制 Java 对其的检查
        field.setAccessible(true);
        // 获取 object 中 field 所代表的属性值
        return field.get(object);
      }
    } catch (Exception ignored) {
    }
    return null;
  }

  /**
   * 获取类型以及父类型中全部的字段
   * 注: 默认消除重复字段
   *
   * @param clazz 要获取的类型
   * @return 类型以及父类型中全部的字段
   */
  public static Set<Field> getAllDeclaredField(Class<?> clazz) {
    return getSuperClassList(clazz, true, false).stream()
        .flatMap(tClass -> Arrays.stream(tClass.getDeclaredFields()))
        .collect(Collectors.toSet());
  }

  /**
   * 获取类型以及父类型中全部的实例方法
   * 注: 默认消除重复方法
   *
   * @param clazz 要获取的类型
   * @return 类型以及父类型中全部的方法
   */
  public static Set<Method> getAllDeclaredMethod(Class<?> clazz) {
    return getSuperClassList(clazz, true, false).stream()
        .flatMap(tClass -> Arrays.stream(tClass.getDeclaredMethods()))
        .collect(Collectors.toSet());
  }
}
