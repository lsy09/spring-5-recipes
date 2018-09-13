package com.springrecipes.ch_02.recipe_2_1_ii.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
    includeFilters = {
        @ComponentScan.Filter(
            type = FilterType.REGEX,
            pattern = {"com.springrecipes.ch_02.*.*Dao", "com.springrecipes.ch_02.*.*Service"})
    },
    excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.ANNOTATION,
            classes = {org.springframework.stereotype.Controller.class}) }
    )

public class SequenceGeneratorConfiguration {
}
