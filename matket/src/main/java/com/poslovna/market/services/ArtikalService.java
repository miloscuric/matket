package com.poslovna.market.services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poslovna.market.models.Artikal;
import com.poslovna.market.repositories.ArtikalRepository;

@Service
public class ArtikalService {
	
	@Autowired
	private ArtikalRepository artikalRepository;

	public Collection<Artikal> findByNazivContaining(String naziv) {
		return artikalRepository.findByNazivContaining(naziv);
	}

	public Artikal findBySifra(String sifra) {
		return artikalRepository.findBySifra(sifra);
	}

	public Artikal findOne(Integer artikalId) {
		return artikalRepository.findOne(artikalId);
	}
	
	public HashSet<Artikal> findByGrupaArtikala(Integer grupaArtikalaId){
		return artikalRepository.findByGrupaArtikalaId(grupaArtikalaId);
		
	}
	
	public Artikal saveArtikal(Artikal a){
		return artikalRepository.save(a);
	}
	
	public Collection<Artikal> findAll(){
		return artikalRepository.findAll();
	}
	
	public void remove(Artikal a){
		artikalRepository.delete(a);
	}
	
	

}
