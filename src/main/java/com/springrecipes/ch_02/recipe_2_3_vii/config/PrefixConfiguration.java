package com.springrecipes.ch_02.recipe_2_3_vii.config;

import com.springrecipes.ch_02.recipe_2_3_vii.DatePrefixGenerator;
import org.springframework.context.annotation.Bean;

public class PrefixConfiguration {

    @Bean
    public DatePrefixGenerator datePrefixGenerator(){
        DatePrefixGenerator dpg = new DatePrefixGenerator();
        dpg.setPattern("yyyyMMdd");
        return dpg;
    }
}
