package com.springrecipes.ch_01.recipe_1_1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new GenericXmlApplicationContext("beans_1_1.xml");

        HelloWorld helloWorld = context.getBean(HelloWorld.class);
        helloWorld.hello();
        
        ((GenericXmlApplicationContext) context).close();
    }
}
