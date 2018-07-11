package com.poslovna.market.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poslovna.market.models.AnalitikaMagacinskeKartice;
import com.poslovna.market.repositories.AnalitikaMagacinskeKarticeRepository;

@Service
public class AnalitikaMagacinskeKarticeService {

	@Autowired
	private AnalitikaMagacinskeKarticeRepository analitikaMagacinskeKarticeRepository;
	
	public AnalitikaMagacinskeKartice save(AnalitikaMagacinskeKartice analitikaMagacinskeKartice) {
		return analitikaMagacinskeKarticeRepository.save(analitikaMagacinskeKartice);
	}
	
}
