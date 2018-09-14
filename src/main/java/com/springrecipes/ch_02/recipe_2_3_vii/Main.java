package com.springrecipes.ch_02.recipe_2_3_vii;

import com.springrecipes.ch_02.recipe_2_3_vii.config.SequenceConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(SequenceConfiguration.class);
        context.refresh();

        SequenceGenerator generator = (SequenceGenerator)context.getBean("sequenceGenerator");

        System.out.println(generator.getSequence());
        System.out.println(generator.getSequence());

    }
}
