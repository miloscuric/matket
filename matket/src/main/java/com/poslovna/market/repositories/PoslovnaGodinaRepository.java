package com.poslovna.market.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poslovna.market.models.Company;
import com.poslovna.market.models.PoslovnaGodina;

public interface PoslovnaGodinaRepository extends JpaRepository<PoslovnaGodina, Integer> {

	public PoslovnaGodina findByPreduzeceAndBrojGodine(Company company, Integer brojGodine);
	
	public Collection<PoslovnaGodina> findAllByPreduzece(Company company);
	
	public PoslovnaGodina findByPreduzeceAndAktivna(Company company, Boolean aktivna);
}
