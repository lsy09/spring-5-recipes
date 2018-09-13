package com.springrecipes.ch_02.recipe_2_2.config;

import com.springrecipes.ch_02.recipe_2_2.Battery;
import com.springrecipes.ch_02.recipe_2_2.Disc;
import com.springrecipes.ch_02.recipe_2_2.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ShopConfiguration {

    @Bean
    public Product aaa() {
        Battery p1 = new Battery("AAA", 2.5);
        p1.setRechargeable(true);
        return p1;
    }

    @Bean
    public Product cdrw() {
        Disc p2 = new Disc("CD-RW", 1.5);
        p2.setCapacity(700);
        return p2;
    }
}
