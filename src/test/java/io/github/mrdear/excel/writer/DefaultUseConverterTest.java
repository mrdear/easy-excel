package io.github.mrdear.excel.writer;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.mrdear.excel.DocType;
import io.github.mrdear.excel.EasyDoc;
import io.github.mrdear.excel.annotation.DocField;
import io.github.mrdear.excel.read.DocReader;
import io.github.mrdear.excel.read.ReadContextBuilder;
import io.github.mrdear.excel.write.DocWriter;
import io.github.mrdear.excel.write.WriteContextBuilder;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author rxliuli
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DefaultUseConverterTest {
    private final String currentPath = DefaultUseConverterTest.class.getClassLoader().getResource(".").getPath();
    private final int count = 5;
    private String fileName = currentPath + "/DefaultUseConverterTest.xlsx";

    @Test
    @Order(1)
    void exportDateList() {
        final List<Person> users = mockUser(count);
        try (DocWriter writer = EasyDoc.export(DocType.XLSX, fileName)) {
            writer
                .export(WriteContextBuilder.builder()
                    .dataSource(users)
                    .buildForExcel("user"))
                .writeAndFlush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(2)
    void importDateList() {
        try (DocReader reader = EasyDoc.read(DocType.XLSX, new FileInputStream(fileName))) {
            List<Person> result = reader.resolve(ReadContextBuilder.<Person>builder()
                .clazz(Person.class)
                .buildForExcel())
                .getData();
            assertThat(result)
                .hasSize(count);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Person> mockUser(int count) {
        return IntStream.range(0, count)
            .mapToObj(i -> new Person("姓名 " + i, new Date(), LocalDate.now(), LocalTime.now()))
            .collect(Collectors.toList());
    }

    public static class Person {
        @DocField(columnName = "姓名")
        private String username;
        @DocField(columnName = "日期")
        private Date date;
        @DocField(columnName = "本地日期")
        private LocalDate localDate;
        @DocField(columnName = "本地时间")
        private LocalTime localTime;

        public Person() {
        }

        public Person(String username, Date date, LocalDate localDate, LocalTime localTime) {
            this.username = username;
            this.date = date;
            this.localDate = localDate;
            this.localTime = localTime;
        }

        public Date getDate() {
            return date;
        }

        public Person setDate(Date date) {
            this.date = date;
            return this;
        }

        public String getUsername() {
            return username;
        }

        public Person setUsername(String username) {
            this.username = username;
            return this;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                .append("username", username)
                .append("date", date)
                .append("localDate", localDate)
                .append("localTime", localTime)
                .toString();
        }
    }

}
