package com.springrecipes.ch_02.recipe_2_3_iii;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.atomic.AtomicInteger;

public class SequenceGenerator {
    @Autowired
    private PrefixGenerator[] prefixGenerator;
    private String suffix;
    private int initial;
    private AtomicInteger counter = new AtomicInteger();

    public SequenceGenerator() {
    }

    public SequenceGenerator(PrefixGenerator[] prefixGenerator, String suffix, int initial) {
        this.prefixGenerator = prefixGenerator;
        this.suffix = suffix;
        this.initial = initial;
    }

    public void setPrefixGenerator(PrefixGenerator[] prefixGenerator) {
        this.prefixGenerator = prefixGenerator;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void setInitial(int initial) {
        this.initial = initial;
    }

    public String getSequence(){
        StringBuilder builder = new StringBuilder();
        for(PrefixGenerator prefix : prefixGenerator){
            builder.append(prefix.getPrefix());
            builder.append("-");
        }
        builder.append(initial).append(counter.getAndIncrement()).append(suffix);
        return builder.toString();
    }
}
