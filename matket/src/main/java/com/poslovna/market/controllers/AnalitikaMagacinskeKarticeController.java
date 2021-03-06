package com.poslovna.market.controllers;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.poslovna.market.models.AnalitikaMagacinskeKartice;
import com.poslovna.market.models.Artikal;
import com.poslovna.market.models.Company;
import com.poslovna.market.models.Magacin;
import com.poslovna.market.models.MagacinskaKartica;
import com.poslovna.market.models.StavkaCenovnika;
import com.poslovna.market.models.StavkaDokumenta;
import com.poslovna.market.services.AnalitikaMagacinskeKarticeService;
import com.poslovna.market.services.ArtikalService;
import com.poslovna.market.services.CompanyService;
import com.poslovna.market.services.MagacinService;
import com.poslovna.market.services.MagacinskaKarticaService;
import com.poslovna.market.services.StavkaDokumentaService;

@RestController
public class AnalitikaMagacinskeKarticeController {

	@Autowired
	private AnalitikaMagacinskeKarticeService amkService;
	
	@Autowired
	private MagacinskaKarticaService mkService;
	
	@Autowired
	private StavkaDokumentaService stavkaDokumentaService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private MagacinService magacinService;
	
	@Autowired
	private ArtikalService artikalService;
	
	@Autowired
	private MagacinskaKarticaService magKarticaService;
	
	@RequestMapping(
			value = "/api/amk/addAMK/{artikalId}/{pib}/{pib2}/{stavkaDokumentaId}",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AnalitikaMagacinskeKartice> addAMK(@RequestBody AnalitikaMagacinskeKartice amk, @PathVariable Integer artikalId,
			@PathVariable BigInteger pib, @PathVariable BigInteger pib2, @PathVariable Integer stavkaDokumentaId) {
		Company company = companyService.findByPib(pib);
		Company company2 = companyService.findByPib(pib2);
		
		//ovde treba findAllByPreduzece ako se u bazi nalazi vise magacina u okviru preduzeca
		Magacin magacin  = magacinService.findByPreduzece(company);
		Magacin magacin2 = magacinService.findByPreduzece(company2);
		Artikal artikal = artikalService.findOne(artikalId);
		
		MagacinskaKartica magacinskaKartica = mkService.nadjiMagacinskuKarticuArtikla(magacin, artikal);
		MagacinskaKartica magacinskaKartica2 = mkService.nadjiMagacinskuKarticuArtikla(magacin2, artikal);
		
		if(magacinskaKartica2 == null){
			//ovde uzimam stavku cenovnika iz cenovnika prve firme!!!
			MagacinskaKartica novaKartica = new MagacinskaKartica(0, 0, 0, 0, 0, 0, artikal, magacin2, magacinskaKartica.getStavkaCenovnika());
			magKarticaService.saveKartica(novaKartica);
			
			AnalitikaMagacinskeKartice amk2 = new AnalitikaMagacinskeKartice();
			amk2.setSmer("U");
			
			if (amk.getVrstaPrometa().equals("PR")){
				amk2.setVrstaPrometa("PR");
			}
			else {
				amk2.setVrstaPrometa("FO");
			}
			
			StavkaDokumenta stavkaDokumenta = stavkaDokumentaService.findOne(stavkaDokumentaId);
			amk.setStavkaDokumenta(stavkaDokumenta);	
			amk2.setStavkaDokumenta(stavkaDokumenta);
			
			magacinskaKartica.setPrometIzKol(magacinskaKartica.getPrometIzKol() + stavkaDokumenta.getKolicina());
			magacinskaKartica.setPrometIzVred(magacinskaKartica.getPrometIzVred() + stavkaDokumenta.getKolicina() * stavkaDokumenta.getCena());
			amk.setMagacinskaKartica(magacinskaKartica);
			
			novaKartica.setPrometUlKol(novaKartica.getPrometUlKol() + stavkaDokumenta.getKolicina());
			novaKartica.setPrometUlVred(novaKartica.getPrometUlVred() + stavkaDokumenta.getKolicina() * stavkaDokumenta.getCena());
			//cena jos treba u stavci
			amk2.setMagacinskaKartica(novaKartica);
			amkService.save(amk2);
			
			AnalitikaMagacinskeKartice amk1 = amkService.save(amk);
	        return new ResponseEntity<AnalitikaMagacinskeKartice>(amk1, HttpStatus.OK);
	        
		}else{
		
			AnalitikaMagacinskeKartice amk2 = new AnalitikaMagacinskeKartice();
			amk2.setSmer("U");
			
			if (amk.getVrstaPrometa().equals("PR")){
				amk2.setVrstaPrometa("PR");
			}
			else {
				amk2.setVrstaPrometa("FO");
			}
			
			StavkaDokumenta stavkaDokumenta = stavkaDokumentaService.findOne(stavkaDokumentaId);
			amk.setStavkaDokumenta(stavkaDokumenta);	
			amk2.setStavkaDokumenta(stavkaDokumenta);
			
			magacinskaKartica.setPrometIzKol(magacinskaKartica.getPrometIzKol() + stavkaDokumenta.getKolicina());
			magacinskaKartica.setPrometIzVred(magacinskaKartica.getPrometIzVred() + stavkaDokumenta.getKolicina() * stavkaDokumenta.getCena());
			amk.setMagacinskaKartica(magacinskaKartica);
			
			magacinskaKartica2.setPrometUlKol(magacinskaKartica2.getPrometUlKol() + stavkaDokumenta.getKolicina());
			magacinskaKartica2.setPrometUlVred(magacinskaKartica2.getPrometUlVred() + stavkaDokumenta.getKolicina() * stavkaDokumenta.getCena());
			//cena jos treba u stavci
			amk2.setMagacinskaKartica(magacinskaKartica2);
			amkService.save(amk2);
			
			AnalitikaMagacinskeKartice amk1 = amkService.save(amk);
	        return new ResponseEntity<AnalitikaMagacinskeKartice>(amk1, HttpStatus.OK);
		}
    }
	
}
