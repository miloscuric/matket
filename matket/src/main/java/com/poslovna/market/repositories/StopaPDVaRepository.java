package com.poslovna.market.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poslovna.market.models.Company;
import com.poslovna.market.models.StopaPDVa;

public interface StopaPDVaRepository extends JpaRepository<StopaPDVa, Integer> {
	
	

}
