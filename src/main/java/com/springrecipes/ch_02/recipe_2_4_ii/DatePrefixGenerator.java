package com.springrecipes.ch_02.recipe_2_4_ii;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@DatePrefixAnnotation
public class DatePrefixGenerator implements PrefixGenerator {

    private DateFormat formatter;

    public void setPattern(String pattern) {
        this.formatter = new SimpleDateFormat(pattern);
    }

    @Override
    public String getPrefix() {
        return formatter.format(new Date());
    }
}
