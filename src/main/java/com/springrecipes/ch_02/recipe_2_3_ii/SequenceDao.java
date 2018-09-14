package com.springrecipes.ch_02.recipe_2_3_ii;

public interface SequenceDao {

    public Sequence getSequence(String sequenceId);

    public int getNextValue(String sequenceId);
} 
