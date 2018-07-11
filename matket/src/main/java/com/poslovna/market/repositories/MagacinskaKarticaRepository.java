package com.poslovna.market.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poslovna.market.models.Artikal;
import com.poslovna.market.models.Magacin;
import com.poslovna.market.models.MagacinskaKartica;

public interface MagacinskaKarticaRepository extends JpaRepository<MagacinskaKartica, Integer>{

	public Collection<MagacinskaKartica> findByMagacin(Magacin magacin);
	
	public MagacinskaKartica findByMagacinAndArtikal(Magacin magacin, Artikal artikal);
	
}
