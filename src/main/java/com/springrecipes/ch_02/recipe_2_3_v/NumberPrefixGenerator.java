package com.springrecipes.ch_02.recipe_2_3_v;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class NumberPrefixGenerator implements PrefixGenerator{
    @Override
    public String getPrefix() {
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(100);
        return String.format("%03d", randomInt);
    }
}
