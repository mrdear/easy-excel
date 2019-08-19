package io.github.mrdear.excel.read;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Stream;

/**
 * 读取解析结果封装
 * @author Quding Ding
 * @since 2019-08-19
 */
public class ReaderResolveResult<T> {

    @Getter
    @Setter
    private List<T> data;

    /**
     * 获取解析结果大小
     * @return 获取行数
     */
    public int size() {
        return null == data ? 0 : data.size();
    }

    /**
     * 获取结果流
     * @return 流
     */
    public Stream<T> stream() {
        return data.stream();
    }

    /**
     * 获取结果
     * @param index 坐标
     * @return 结果
     */
    public T get(int index) {
        return data.get(index);
    }

}
