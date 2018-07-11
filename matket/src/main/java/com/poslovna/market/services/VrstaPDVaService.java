package com.poslovna.market.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poslovna.market.models.VrstaPDVa;
import com.poslovna.market.repositories.VrstaPDVaRepository;

@Service
public class VrstaPDVaService {
	
	@Autowired
	private VrstaPDVaRepository vrstaPDVaRepository;
	
	public VrstaPDVa save(VrstaPDVa vrstaPDVa) {
		return vrstaPDVaRepository.save(vrstaPDVa);
	}
	
	public Collection<VrstaPDVa> getAll() {
		return vrstaPDVaRepository.findAll();
	}
	
	public VrstaPDVa findVrstu(Integer id){
		return vrstaPDVaRepository.findOne(id);
	}
	
	public void deleteVrstu(VrstaPDVa vrstaPDVa){
		vrstaPDVaRepository.delete(vrstaPDVa);
	}

}
