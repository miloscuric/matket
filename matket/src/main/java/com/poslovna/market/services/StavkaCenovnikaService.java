package com.poslovna.market.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poslovna.market.models.StavkaCenovnika;
import com.poslovna.market.repositories.StavkaCenovnikaRepository;

@Service
public class StavkaCenovnikaService {

	
	@Autowired
	private StavkaCenovnikaRepository stavkaCenovnikaRepository;
	
	public  StavkaCenovnika save( StavkaCenovnika stavkaCenovnika) {
		return stavkaCenovnikaRepository.save(stavkaCenovnika);
	}
}
