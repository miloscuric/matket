package com.poslovna.market.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poslovna.market.models.Company;
import com.poslovna.market.models.GrupaArtikala;

public interface GrupaArtikalaRepository extends JpaRepository<GrupaArtikala, Integer>{
	
	public Collection<GrupaArtikala> findByCompany(Company company);

}
