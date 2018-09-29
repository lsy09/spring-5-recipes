package com.springrecipes.ch_02.recipe_2_5_ii.config;

import com.springrecipes.ch_02.recipe_2_5_ii.Battery;
import com.springrecipes.ch_02.recipe_2_5_ii.Disc;
import com.springrecipes.ch_02.recipe_2_5_ii.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.springrecipes.ch_02.recipe_2_5_ii")
public class ShopConfiguration {

    @Bean
    public Product aaa() {
        Battery p1 = new Battery();
        p1.setName("AAA");
        p1.setPrice(2.5);
        p1.setRechargeable(true);
        return p1;
    }

    @Bean
    public Product cdrw(){
        Disc p2 = new Disc("CD-RW", 1.5);
        p2.setCapacity(700);
        return p2;
    }

    @Bean
    public Product dvdrw(){
        Disc p2 = new Disc("DVD-RW", 3.0);
        p2.setCapacity(700);
        return p2;
    }

}
