package com.spring.passenger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
@RequestMapping("/passenger")
public class PassengerApplication {
	
	@Autowired
	private RestTemplate template;
	
	@GetMapping("/save")
	public String savePassenger() {
		String payment=template.getForObject("http://localhost:8081/passenger/save", String.class);
		return "Passenger service called...."+payment;
	}

	public static void main(String[] args) {
		SpringApplication.run(PassengerApplication.class, args);
	}

}
