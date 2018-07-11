package com.poslovna.market.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poslovna.market.models.Cenovnik;
import com.poslovna.market.models.Company;

public interface CenovnikRepository extends JpaRepository<Cenovnik, Integer> {
	
	public Collection<Cenovnik> findByCompany(Company company);
	
	public Cenovnik findByCompanyAndAktivan(Company company, Boolean aktivan);
	
	public Collection<Cenovnik> findByCompanyAndDatumVazenjaPocetakBetween(Company company, String datumPocetakVazenja, String datumKrajVazenja);

	public Collection<Cenovnik> findByCompanyAndDatumVazenjaKrajBetween(Company company, String datumPocetakVazenja, String datumKrajVazenja);
	
	public Collection<Cenovnik> findByCompanyAndDatumVazenjaPocetak(Company company, String datumPocetakVazenja);

	public Collection<Cenovnik> findByCompanyAndDatumVazenjaPocetakLessThanOrderByDatumVazenjaPocetakDesc(Company company, String mojDatum);
}
