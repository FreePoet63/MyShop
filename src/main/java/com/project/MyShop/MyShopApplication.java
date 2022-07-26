package com.project.MyShop;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class MyShopApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(MyShopApplication.class)
				.bannerMode(Banner.Mode.LOG)
				.run(args);
	}

}
