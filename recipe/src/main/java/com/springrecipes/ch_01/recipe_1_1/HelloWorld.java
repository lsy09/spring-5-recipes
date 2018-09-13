package com.springrecipes.ch_01.recipe_1_1;

import java.util.List;

public class HelloWorld {

    private List<Holiday> holidays;
    private String message;
   
    public void setMessage(String message) {
        this.message = message;
    }

    public void setHolidays(List<Holiday> holidays) {
        this.holidays = holidays;
    }

    public List<Holiday> getHolidays() { 
    	return holidays; 
    }
    
    public void hello() {
        System.out.println("Hello! " + message);
    }
}
