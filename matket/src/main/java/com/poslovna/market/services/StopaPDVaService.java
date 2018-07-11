package com.poslovna.market.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poslovna.market.models.StopaPDVa;
import com.poslovna.market.repositories.StopaPDVaRepository;
@Service
public class StopaPDVaService {
	
	@Autowired
	private StopaPDVaRepository stopaPDVaRepository;
	
	public StopaPDVa save(StopaPDVa stopaPDVa) {
		return stopaPDVaRepository.save(stopaPDVa);
	}
	
	public Collection<StopaPDVa> getAll() {
		return stopaPDVaRepository.findAll();
	}
	public StopaPDVa findStopu(Integer id){
		return stopaPDVaRepository.findOne(id);
	}
	
	public void deleteStopu(StopaPDVa stopaPDVa){
		stopaPDVaRepository.delete(stopaPDVa);
	}

}
