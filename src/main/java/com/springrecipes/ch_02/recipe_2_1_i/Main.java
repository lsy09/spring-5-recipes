package com.springrecipes.ch_02.recipe_2_1_i;

import com.springrecipes.ch_02.recipe_2_1_i.config.SequenceGeneratorConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {

    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(SequenceGeneratorConfiguration.class);

        SequenceGenerator generator = context.getBean(SequenceGenerator.class);

        System.out.println(generator.getSequence());
        System.out.println(generator.getSequence());
    }
}