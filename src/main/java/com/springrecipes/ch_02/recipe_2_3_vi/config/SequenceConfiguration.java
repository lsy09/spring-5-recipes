package com.springrecipes.ch_02.recipe_2_3_vi.config;

import com.springrecipes.ch_02.recipe_2_3_vi.DatePrefixGenerator;
import com.springrecipes.ch_02.recipe_2_3_vi.NumberPrefixGenerator;
import com.springrecipes.ch_02.recipe_2_3_vi.SequenceGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SequenceConfiguration {
    @Bean
    public DatePrefixGenerator datePrefixGenerator() {
        DatePrefixGenerator dpg = new DatePrefixGenerator();
        dpg.setPattern("yyyyMMdd");
        return dpg;
    }

    @Bean
    public NumberPrefixGenerator numberPrefixGenerator() {
        NumberPrefixGenerator npg = new NumberPrefixGenerator();
        return npg;
    }

    @Bean
    public SequenceGenerator sequenceGenerator() {
        SequenceGenerator sequence = new SequenceGenerator();
        sequence.setInitial(100000);
        sequence.setSuffix("A");
        return sequence;
    }
}
