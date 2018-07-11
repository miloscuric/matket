package com.poslovna.market.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poslovna.market.models.Cenovnik;
import com.poslovna.market.models.Company;
import com.poslovna.market.models.GrupaArtikala;
import com.poslovna.market.repositories.GrupaArtikalaRepository;

@Service
public class GrupaAtrikalaService {
	
	@Autowired
	private GrupaArtikalaRepository grupaArtikalaRepository;
	
	public Collection<GrupaArtikala> findByCompany(Company company) {
		return grupaArtikalaRepository.findByCompany(company);
	}
	
	public GrupaArtikala add(GrupaArtikala grupa){
		return grupaArtikalaRepository.save(grupa);
	}
	
	public GrupaArtikala findOne(Integer id){
		return grupaArtikalaRepository.findOne(id);
	}
	
	public void remove(GrupaArtikala g){
		grupaArtikalaRepository.delete(g);
	}
	

}
