package com.springrecipes.ch_02.recipe_2_3_iv;

import com.springrecipes.ch_02.recipe_2_3_iv.config.SequenceConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(SequenceConfiguration.class);

        SequenceGenerator generator  = context.getBean(SequenceGenerator.class);

        System.out.println(generator.getSequence());
        System.out.println(generator.getSequence());
    }
}
