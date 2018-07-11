package com.poslovna.market.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poslovna.market.models.JedinicaMere;

public interface JedinicaMereRepository extends JpaRepository<JedinicaMere, Integer>{
	
	<E extends JedinicaMere> List<E> findByCompanyId(Integer id);

}
