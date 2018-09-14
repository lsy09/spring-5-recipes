package com.springrecipes.ch_02.recipe_2_3_iv;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.atomic.AtomicInteger;

public class SequenceGenerator {
    private PrefixGenerator prefixGenerator;
    private String suffix;
    private int initial;
    private int counter;

    public SequenceGenerator() {
    }

    public SequenceGenerator(PrefixGenerator prefixGenerator, String suffix, int initial) {
        this.prefixGenerator = prefixGenerator;
        this.suffix = suffix;
        this.initial = initial;
    }

//    @Autowired(required = false)
    public void setPrefixGenerator(PrefixGenerator prefixGenerator) {
        this.prefixGenerator = prefixGenerator;
    }

    @Autowired
    public void myOwnCustomInjectionName(PrefixGenerator prefixGenerator) {
        this.prefixGenerator = prefixGenerator;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void setInitial(int initial) {
        this.initial = initial;
    }

    public synchronized String getSequence() {
        StringBuilder builder = new StringBuilder();
        builder.append(prefixGenerator.getPrefix());
        builder.append(initial + counter++);
        builder.append(suffix);
        return builder.toString();
    }
}
