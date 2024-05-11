package com.hackaton.hackatonv100;

import com.hackaton.hackatonv100.preview.Preview;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	public static boolean previewMode = false;

	public static void main(String[] args)  {
		for(var arg: args) {
			System.out.println(arg);
			if(arg.equals("-preview")) {
				System.out.println("PREVIEW");
				previewMode = true;
			}
		}

		SpringApplication.run(Application.class, args);

	}


}
