package com.spring.flightbooking;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.retry.annotation.Retry;

@SpringBootApplication
@RestController
@RequestMapping("/flight")
public class FlightBookingApplication {
	
	@Autowired
	private RestTemplate template;
	
	private static final String FLIGHT_BOOKING="flightBooking";
	
	int attempt=1;
	
	@GetMapping("/book")
	//@CircuitBreaker(name = FLIGHT_BOOKING,fallbackMethod ="flightBookingFallBack" )
	@Retry(name = FLIGHT_BOOKING,fallbackMethod ="flightBookingFallBack" )
	public String flightBooking() {
		
		System.out.println("Retry method called "+attempt++ +" times at "+new Date());	
		String response=template.getForObject("http://localhost:8081/passenger/save", String.class);
		return response;
		
	}
	
	/*
	 * Fallback method
	 * 
	 * same return type and must have Exception argument
	 * 
	 */
	public String flightBookingFallBack(Exception e) {
		return "service gateway failed...";
		
	}

	public static void main(String[] args) {
		SpringApplication.run(FlightBookingApplication.class, args);
	}
	
	

}
