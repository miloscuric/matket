package com.poslovna.market.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poslovna.market.models.Dokument;
import com.poslovna.market.repositories.DokumentRepository;

@Service
public class DokumentService {

	@Autowired
	private DokumentRepository fakturaRepository;

	public Integer nadjiSlececiBrojDokumenta() {
		return fakturaRepository.getMaxBrojDokumenta();
	}

	public Dokument save(Dokument faktura) {
		return fakturaRepository.save(faktura);
	}

	public Dokument findOne(Integer fakturaId) {
		return fakturaRepository.findOne(fakturaId);
	}

	public Collection<Dokument> getAll() {
		return fakturaRepository.findAll();
	}
	
	public Collection<Dokument> sveNarudzbenice() {
		return fakturaRepository.findByVrstaDokumenta("NAR");
	}
	
	public Collection<Dokument> sveNarudzbeniceFirme(Integer companyId){
		return fakturaRepository.findByIzdavaocRacunaIdAndVrstaDokumenta(companyId, "NAR");
	}
	
	public Integer setStatusDokumentaForFaktura(String status, Integer id) {
		return fakturaRepository.setStatusDokumentaForFaktura(status, id);
	}
	
	public Collection<Dokument> findByBrojDokumenta(Integer brojDokumenta) {
		return fakturaRepository.findByBrojDokumenta(brojDokumenta);
	}

	public Collection<Dokument> findByBrojDokumentaAndVrstaDokumenta(Integer brojDokumenta) {
		return fakturaRepository.findByBrojDokumentaAndVrstaDokumenta(brojDokumenta, "NAR");
	}
	
	public Collection<Dokument> getByCompanyId(Integer companyId){
		return fakturaRepository.getByIzdavaocRacunaId(companyId);
	}
	
	public void delete(Dokument dokument) {
		Integer id = dokument.getId();
		fakturaRepository.delete(id);
	}
	
	
	
}
