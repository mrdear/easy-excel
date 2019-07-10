package io.github.mrdear.excel.internal.util;

import io.github.mrdear.excel.ExcelException;
import io.github.mrdear.excel.annotation.ExcelField;
import io.github.mrdear.excel.annotation.ExcelIgnore;
import io.github.mrdear.excel.read.ReadHeader;
import io.github.mrdear.excel.write.WriterHeader;
import io.github.mrdear.excel.domain.convert.ConverterFactory;
import io.github.mrdear.excel.domain.convert.IConverter;
import io.github.mrdear.excel.domain.convert.NotSpecifyConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 导表过程中对Bean的一些处理
 *
 * @author Quding Ding
 * @since 2018/6/28
 */
public class DocBeanUtils {

    /**
     * bean转Map函数,支持使用自定义注解
     *
     * @param bean 对应的bean
     * @return map中 key 属性名  属性值
     */
    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> beanToMap(List<?> bean) {
        if (null == bean || bean.isEmpty()) {
            return Collections.emptyList();
        }
        // 处理已经为map
        Object tempBean = bean.get(0);
        if (tempBean instanceof Map) {
            return (List<Map<String, Object>>) bean;
        }
        // 处理bean
        return bean.parallelStream()
            .map(DocBeanUtils::toMap)
            .collect(Collectors.toList());
    }

    /**
     * 通过bean拿到对应的excel header
     *
     * @param bean 实例
     * @param <T>  bean类型,支持LinkedHashMap 与 class
     * @return LinkedHashMap key field name  value WriterHeader
     */
    @SuppressWarnings("unchecked")
    public static <T> LinkedHashMap<String, WriterHeader> beanToWriterHeaders(T bean) {
        if (bean instanceof LinkedHashMap) {
            return ((Map<String, ?>) bean)
                .keySet()
                .stream()
                .collect(LinkedHashMap::new,
                    (l, v) -> l.put(v, WriterHeader.create(v)),
                    Map::putAll);
        }

        Class clazz = bean instanceof Class ? (Class) bean : bean.getClass();

        // 为bean情况 获取到所有字段
        return SuperClassUtil.getAllDeclaredField(clazz).stream()
            // 过滤掉指定忽略的字段
            .filter(x -> Objects.isNull(x.getAnnotation(ExcelIgnore.class)))
            .sorted(Comparator.comparing(x -> {
                if (null == x.getAnnotation(ExcelField.class)) {
                    return 0;
                }
                return x.getAnnotation(ExcelField.class).order();
            }))
            .map(x -> {
                final Pair<String, ? extends IConverter> pair = castHeaderNameAndConverter(x);
                return new Pair<>(x.getName(), WriterHeader.create(pair.getKey(), pair.getValue()));
            })
            .collect(LinkedHashMap::new, (l, v) -> l.put(v.getKey(), v.getValue()), HashMap::putAll);
    }

    /**
     * bean转为对应的读操作header
     *
     * @param clazz 实体类型
     * @param <T>   试题类型
     * @return 读操作header, key columnName value ReadHeader
     */
    public static <T> Map<String, ReadHeader> beanToReaderHeaders(Class<T> clazz) {
        return SuperClassUtil.getAllDeclaredField(clazz).stream()
            // 过滤掉指定忽略的字段
            .filter(x -> Objects.isNull(x.getAnnotation(ExcelIgnore.class)))
            .sorted(Comparator.comparing(x -> {
                if (null == x.getAnnotation(ExcelField.class)) {
                    return 0;
                }
                return x.getAnnotation(ExcelField.class).order();
            }))
            .map(x -> {
                final Pair<String, ? extends IConverter> pair = castHeaderNameAndConverter(x);
                return new Pair<>(pair.getKey(), ReadHeader.create(x, pair.getValue()));
            })
            .collect(HashMap::new, (l, v) -> l.put(v.getKey(), v.getValue()), HashMap::putAll);
    }

    /**
     * 从字段中获取到对应的表头名字与转换器
     *
     * @param field 字段
     * @return 使用 {@link Pair} 封装的两个字段
     */
    private static Pair<String, ? extends IConverter> castHeaderNameAndConverter(Field field) {
        field.setAccessible(true);
        ExcelField excelField = field.getAnnotation(ExcelField.class);
        // 不存在则返回字段名以及默认转换器
        if (null == excelField) {
            return new Pair<>(field.getName(), ConverterFactory.get(field.getType()));
        }

        // 如果 convertClass 未指定，则根据字段类型获取对应的默认转换器
        Class<? extends IConverter> convertClass = excelField.convert();
        IConverter<?> convert = null;
        if (NotSpecifyConverter.class.equals(convertClass)) {
            convert = ConverterFactory.get(field.getType());
        } else {
            convert = ConverterFactory.get(convertClass);
        }

        String columnName = excelField.columnName();
        String name = StringUtils.isEmpty(columnName) ? field.getName() : columnName;
        return new Pair<>(name, convert);
    }

    /**
     * 根据自身值类型自动填入表单对应的类型
     *
     * @param cell  表单格子
     * @param value 值类型
     */
    public static void autoFitCell(Cell cell, Object value) {
        if (null == value) {
            return;
        }
        cell.setCellValue(String.valueOf(value));
    }

    /**
     * 创建一个bean
     *
     * @param clazz 创建bean
     * @param <T>   bean类型
     * @return 实例
     */
    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ExcelException(e);
        }
    }

    public static void fieldSetValue(Field field, Object target, Object value) {
        // 如果字段值为空则不进行设置
        if (value == null) {
            return;
        }

        try {
            field.setAccessible(true);
            field.set(target, value);
        } catch (IllegalAccessException e) {
            throw new ExcelException(e);
        }
    }

    /**
     * bean to map
     *
     * @param bean bean
     * @return map key is bean filed name,value is the filed value
     */
    private static <T> Map<String, Object> toMap(T bean) {
        // 获取到所有字段
        final Class<?> beanClass = bean.getClass();
        final Field[] fields = beanClass.getDeclaredFields();
        return Arrays.stream(fields)
            .map(x -> {
                x.setAccessible(true);
                try {
                    return new Pair<>(x.getName(), x.get(bean));
                } catch (IllegalAccessException e) {
                    // do nothing
                }
                return null;
            })
            .filter(x -> x != null && !Objects.equals(x.getKey(), "this$0"))
            .collect(HashMap::new, (l, v) -> l.put(v.getKey(), v.getValue()), HashMap::putAll);
    }

}
