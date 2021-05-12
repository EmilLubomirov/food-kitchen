package com.example.foodkitchen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FoodKitchenBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodKitchenBackendApplication.class, args);
    }

}
