package com.poslovna.market.controllers;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poslovna.market.models.Artikal;
import com.poslovna.market.models.Cenovnik;
import com.poslovna.market.models.Company;
import com.poslovna.market.models.GrupaArtikala;
import com.poslovna.market.models.JedinicaMere;
import com.poslovna.market.models.StopaPDVa;
import com.poslovna.market.repositories.GrupaArtikalaRepository;
import com.poslovna.market.repositories.JedinicaMereRepository;
import com.poslovna.market.services.ArtikalService;
import com.poslovna.market.services.CompanyService;
import com.poslovna.market.services.StopaPDVaService;
import com.poslovna.market.services.VrstaPDVaService;

@RestController
public class ArtikalController {
	
	@Autowired
	private ArtikalService artikalService;
	
	@Autowired
	private GrupaArtikalaRepository grupaArtikalaRepository;
	
	@Autowired
	private JedinicaMereRepository jedinicaMereRepository;
	
	@Autowired 
	private CompanyService companyService;
	
	@Autowired
	private StopaPDVaService stopaPDVaservice;
	
	@Autowired
	private VrstaPDVaService vrstaPDVaService;
	
	
	@RequestMapping(
            value    = "/api/artikal/findBySifra/{sifra}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Artikal> findBySifra(@PathVariable String sifra) {
		Artikal artikal = artikalService.findBySifra(sifra);
        return new ResponseEntity<Artikal>(artikal, HttpStatus.OK);
    }
	
	@RequestMapping(
            value    = "api/artikal/findByGrupaId/{id}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HashSet<Artikal>> findByGrupaId(@PathVariable Integer id) {
		HashSet<Artikal> artikli = artikalService.findByGrupaArtikala(id);
        return new ResponseEntity<HashSet<Artikal>>(artikli, HttpStatus.OK);
    }
	
	@RequestMapping(
            value    = "api/artikal/findByArtikal/{id}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<StopaPDVa> findByArtikal(@PathVariable Integer id) {
		Artikal artikal = artikalService.findOne(id);
		StopaPDVa stopa = stopaPDVaservice.findStopu(vrstaPDVaService.findVrstu(grupaArtikalaRepository.findOne(artikal.getGrupaArtikala().getId()).getVrstaPDVa().getId()).getStopPDVa().getId());
        return new ResponseEntity<StopaPDVa>(stopa, HttpStatus.OK);
    }
	
	
	@RequestMapping(
            value    = "api/artikal/findByCompanyId/{companyId}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HashSet<Artikal>> findByCompanyId(@PathVariable Integer companyId) {
		Company company = companyService.findOne(companyId);
		Collection<GrupaArtikala> grupe = grupaArtikalaRepository.findByCompany(company);
		HashSet<Artikal> artikli = new HashSet<Artikal>();
		grupe.forEach(grupa -> artikli.addAll(artikalService.findByGrupaArtikala(grupa.getId())));
        return new ResponseEntity<HashSet<Artikal>>(artikli, HttpStatus.OK);
    }
	
	@RequestMapping(
			value = "api/artikal/kreirajArtikal/",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Artikal> addArtikal(@RequestBody Artikal artikal, @RequestParam("idGrupe") Integer idGrupe, @RequestParam("idJedinice") Integer idJedinice) {
		GrupaArtikala grupa = grupaArtikalaRepository.findOne(idGrupe);
		JedinicaMere jm = jedinicaMereRepository.findOne(idJedinice);
		Artikal a = new Artikal();
		a.setNaziv(artikal.getNaziv());
		a.setOpis(artikal.getOpis());
		a.setSifra(artikal.getSifra());
		a.setVrsta(artikal.getVrsta());
		a.setGrupaArtikala(grupa);
		a.setJedinicaMere(jm);
		artikalService.saveArtikal(a);
        return new ResponseEntity<Artikal>(a, HttpStatus.OK);
    }
	
	@RequestMapping(
            value    = "api/artikal/obrisiArtikal/{id}",
            method   = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HashSet<Artikal>> deleteArtikal(@PathVariable Integer id) {
		Artikal a = artikalService.findOne(id);
		GrupaArtikala g = a.getGrupaArtikala();
		artikalService.remove(a);
		HashSet<Artikal> sviArtikli = artikalService.findByGrupaArtikala(g.getId());
        return new ResponseEntity<HashSet<Artikal>>(sviArtikli, HttpStatus.OK);
    }
	
	@RequestMapping(
			value = "api/artikal/promeniArtikal/",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Artikal> updateArtikal(@RequestBody Artikal artikal) {
		Artikal a = artikalService.findOne(artikal.getId());
		a.setNaziv(artikal.getNaziv());
		a.setOpis(artikal.getOpis());
		a.setSifra(artikal.getSifra());
		a.setVrsta(artikal.getVrsta());
		artikalService.saveArtikal(a);
        return new ResponseEntity<Artikal>(a, HttpStatus.OK);
    }
	
	@RequestMapping(
            value    = "/api/artikal/nadjiSve",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Collection<Artikal>> findAll() {
		Collection<Artikal> sviArtikli = artikalService.findAll();
        return new ResponseEntity<Collection<Artikal>>(sviArtikli, HttpStatus.OK);
    }

}
