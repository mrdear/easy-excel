package io.github.mrdear.excel.domain.convert;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

public class DateToStringConvertTest {

    @Test
    public void apply() throws ParseException {
        Date now = new Date();
        DateToStringConvert convert = new DateToStringConvert();
        StringToDateConvert convert1 = new StringToDateConvert();

        String apply = convert.apply(now);
        Date parse = convert1.apply(apply);
        Assert.assertEquals(now.getTime()/1000, parse.getTime()/1000);
    }

}