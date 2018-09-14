package com.springrecipes.ch_02.recipe_2_3_ii;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext("com.springrecipes.ch_02.recipe_2_3_ii");

        SequenceService sequenceService =
                context.getBean(SequenceService.class);

        System.out.println(sequenceService.generate("IT"));
        System.out.println(sequenceService.generate("IT"));
    }
}
