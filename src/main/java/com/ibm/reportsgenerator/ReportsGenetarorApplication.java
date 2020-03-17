package com.ibm.reportsgenerator;

import com.ibm.reportsgenerator.service.ReportsGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ReportsGenetarorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportsGenetarorApplication.class, args);
	}

}
