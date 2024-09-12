package com.example.service;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.dto.QuoteApiResponseDTO;

@Component
public class DashboardServiceImpl implements DashboardService {
	private String quoteApiUrl="https://dummyjson.com/quotes/random";
	@Override
	public QuoteApiResponseDTO getQuote() {
		// TODO Auto-generated method stub
		
		RestTemplate rt=new RestTemplate();
		
		
		ResponseEntity<QuoteApiResponseDTO> forEntity = rt.getForEntity(quoteApiUrl, QuoteApiResponseDTO.class);
		QuoteApiResponseDTO body = forEntity.getBody();
		return body;
	}

}
