package com.poslovna.market.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poslovna.market.models.Korisnik;
import com.poslovna.market.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public Korisnik findByUsername(String username){
		return userRepository.findByUsername(username);
	}
	
	public Korisnik findByUsernameAndPassword(String username, String password){
		return userRepository.findByUsernameAndPassword(username, password);
	}

	public List<Korisnik> findAll() {
		return userRepository.findAll();
	}
	
	public void save(Korisnik korisnik) {
		 userRepository.save(korisnik);
	}

}
