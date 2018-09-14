package com.springrecipes.ch_02.recipe_2_3_v;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context =
                new GenericXmlApplicationContext("appContext.xml");

        SequenceGenerator generator =
                (SequenceGenerator) context.getBean("sequenceGenerator");

        System.out.println(generator.getSequence());
        System.out.println(generator.getSequence());
    }
}
