package com.springrecipes.ch_02.recipe_2_3_vii.config;

import com.springrecipes.ch_02.recipe_2_3_vii.SequenceGenerator;
import com.springrecipes.ch_02.recipe_2_3_vii.PrefixGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(PrefixConfiguration.class)
public class SequenceConfiguration {

    @Value("#{datePrefixGenerator}")
    private PrefixGenerator prefixGenerator;

    @Bean
    public SequenceGenerator sequenceGenerator() {
        SequenceGenerator sequence = new SequenceGenerator();
        sequence.setInitial(100000);
        sequence.setSuffix("A");
        sequence.setPrefixGenerator(prefixGenerator);
        return sequence;
    }
}
