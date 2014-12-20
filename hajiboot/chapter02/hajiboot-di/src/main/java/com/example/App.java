package com.example;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

import com.example.app.Calculator;

@EnableAutoConfiguration
@Import(AppConfig.class)
public class App {
	public static void main(String[] args) {
		try (ConfigurableApplicationContext context = SpringApplication.run(App.class, args)) {
			Scanner scanner = new Scanner(System.in);
			System.out.print("Enter 2 numbers like 'a b' : ");
			int a = scanner.nextInt();
			int b = scanner.nextInt();
			scanner.close();
			Calculator calculator = context.getBean(Calculator.class);
			int result = calculator.calc(a, b);
			System.out.println("result = " + result);
		}
	}
}
