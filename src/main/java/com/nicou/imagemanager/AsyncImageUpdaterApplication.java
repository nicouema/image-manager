package com.nicou.imagemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AsyncImageUpdaterApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsyncImageUpdaterApplication.class, args);
	}

}
