package com.poslovna.market.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poslovna.market.models.BusinessPartner;
import com.poslovna.market.models.Cenovnik;
import com.poslovna.market.models.Company;
import com.poslovna.market.repositories.CenovnikRepository;

@Service
public class CenovnikService {
	
	@Autowired
	private CenovnikRepository cenovnikRepository;
	
	public Cenovnik add(Cenovnik cenovnik){
		return cenovnikRepository.save(cenovnik);
	}
	
	public Collection<Cenovnik> getAll(){
		return cenovnikRepository.findAll();
	}
	
	public Cenovnik findOne(Integer cenovnikId) {
		return cenovnikRepository.findOne(cenovnikId);
	}

	public Collection<Cenovnik> findByCompany(Company company) {
		return cenovnikRepository.findByCompany(company);
	}
	
	public Cenovnik nadjiPoslednjiAktivniCenovnik(Company company, Boolean aktivan) {
		return cenovnikRepository.findByCompanyAndAktivan(company, aktivan);
	}
	
	public Collection<Cenovnik> findByCompanyAndDatumVazenjaPocetakBetween(Company company, String datumPocetakVazenja, String datumKrajVazenja){
		
		return cenovnikRepository.findByCompanyAndDatumVazenjaPocetakBetween(company, datumPocetakVazenja, datumKrajVazenja);
	}
	
	public Collection<Cenovnik> findByCompanyAndDatumVazenjaKrajBetween(Company company, String datumPocetakVazenja, String datumKrajVazenja){
		
		return cenovnikRepository.findByCompanyAndDatumVazenjaKrajBetween(company, datumPocetakVazenja, datumKrajVazenja);
	}
	
	public Collection<Cenovnik> findByCompanyAndDatumVazenjaPocetak(Company company, String datumPocetakVazenja){
		
		return cenovnikRepository.findByCompanyAndDatumVazenjaPocetak(company, datumPocetakVazenja);
	}
	
	public Collection<Cenovnik> findByCompanyAndDatumVazenjaPocetakLessThanOrderByDatumVazenjaPocetakDesc(Company company, String mojDatum)	{
		
		return cenovnikRepository.findByCompanyAndDatumVazenjaPocetakLessThanOrderByDatumVazenjaPocetakDesc(company, mojDatum);
	}

	
	
}
