package io.github.mrdear.excel.domain.convert;

import io.github.mrdear.excel.internal.util.ExcelBeanUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 类型转换器工厂
 *
 * @author rxliuli
 */
public class ConverterFactory {

    private static final Map<Class<?>, IConverter<?>> CONVERT_MAP = new ConcurrentHashMap<>();

    static {
        CONVERT_MAP.put(IntegerConverter.class, new IntegerConverter());
        CONVERT_MAP.put(int.class, CONVERT_MAP.get(IntegerConverter.class));
        CONVERT_MAP.put(Integer.class, CONVERT_MAP.get(IntegerConverter.class));

        CONVERT_MAP.put(LongConverter.class, new LongConverter());
        CONVERT_MAP.put(long.class, CONVERT_MAP.get(LongConverter.class));
        CONVERT_MAP.put(Long.class, CONVERT_MAP.get(LongConverter.class));

        CONVERT_MAP.put(DoubleConverter.class, new DoubleConverter());
        CONVERT_MAP.put(double.class, CONVERT_MAP.get(DoubleConverter.class));
        CONVERT_MAP.put(Double.class, CONVERT_MAP.get(DoubleConverter.class));

        CONVERT_MAP.put(FloatConverter.class, new FloatConverter());
        CONVERT_MAP.put(float.class, CONVERT_MAP.get(FloatConverter.class));
        CONVERT_MAP.put(Float.class, CONVERT_MAP.get(FloatConverter.class));

        CONVERT_MAP.put(FloatConverter.class, new FloatConverter());
        CONVERT_MAP.put(float.class, CONVERT_MAP.get(FloatConverter.class));
        CONVERT_MAP.put(Float.class, CONVERT_MAP.get(FloatConverter.class));

        CONVERT_MAP.put(BooleanConverter.class, new BooleanConverter());
        CONVERT_MAP.put(boolean.class, CONVERT_MAP.get(BooleanConverter.class));
        CONVERT_MAP.put(Boolean.class, CONVERT_MAP.get(BooleanConverter.class));

        CONVERT_MAP.put(YmdDateConverter.class, new YmdDateConverter());
        CONVERT_MAP.put(Date.class, CONVERT_MAP.get(YmdDateConverter.class));

        CONVERT_MAP.put(LocalDateTimeConverter.class, new LocalDateTimeConverter());
        CONVERT_MAP.put(LocalDateTime.class, CONVERT_MAP.get(LocalDateTimeConverter.class));

        CONVERT_MAP.put(LocalDateConverter.class, new LocalDateConverter());
        CONVERT_MAP.put(LocalDate.class, CONVERT_MAP.get(LocalDateConverter.class));

        CONVERT_MAP.put(LocalTimeConverter.class, new LocalTimeConverter());
        CONVERT_MAP.put(LocalTime.class, CONVERT_MAP.get(LocalTimeConverter.class));

        CONVERT_MAP.put(BigDecimalConverter.class, new BigDecimalConverter());
        CONVERT_MAP.put(BigDecimal.class, CONVERT_MAP.get(BigDecimalConverter.class));

        CONVERT_MAP.put(BigIntegerConverter.class, new BigIntegerConverter());
        CONVERT_MAP.put(BigInteger.class, CONVERT_MAP.get(BigIntegerConverter.class));

    }

    /**
     * 根据类名称获取指定转换器
     * @param clazz 类名称
     * @param <T> 转换类型
     * @return 转换器,不存在则返回默认转换器
     */
    @SuppressWarnings("unchecked")
    public static <T> IConverter<T> get(Class<T> clazz) {
        IConverter<?> converter = CONVERT_MAP.get(clazz);

        if (null != converter) {
            return (IConverter<T>) converter;
        }

        // 不存在则主动创建
        boolean isConverter = false;
        for (Class<?> anInterface : clazz.getInterfaces()) {
            isConverter = anInterface.equals(IConverter.class);
            if (isConverter) {
                break;
            }
        }
        if (isConverter) {
            T t = ExcelBeanUtils.newInstance(clazz);
            CONVERT_MAP.putIfAbsent(clazz, (IConverter<?>) t);
            return (IConverter<T>) CONVERT_MAP.get(clazz);
        }

        return (IConverter<T>) DefaultConverter.getInstance();
    }

    /**
     * 注册对应的convert
     * @param clazz 对应的类
     * @param converter 对应的转换器
     * @param <T> 注册类
     */
    public static <T> void register(Class<T> clazz, IConverter<?> converter) {
        CONVERT_MAP.put(clazz, converter);
        CONVERT_MAP.putIfAbsent(converter.getClass(), converter);
    }

}
