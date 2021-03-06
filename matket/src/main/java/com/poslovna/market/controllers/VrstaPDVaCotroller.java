package com.poslovna.market.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.poslovna.market.models.StopaPDVa;
import com.poslovna.market.models.VrstaPDVa;
import com.poslovna.market.repositories.StopaPDVaRepository;
import com.poslovna.market.services.VrstaPDVaService;

@RestController
public class VrstaPDVaCotroller {
	
	@Autowired
	private VrstaPDVaService vrstaPDVaService;
	
	@Autowired
	private StopaPDVaRepository stopaPDVaRepository;
	
	@RequestMapping(
            value    = "/api/vrste/nadjiSveVrste/",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Collection<VrstaPDVa>> findAllStope() {
		Collection<VrstaPDVa> sveVrste = vrstaPDVaService.getAll();
        return new ResponseEntity<Collection<VrstaPDVa>>(sveVrste, HttpStatus.OK);
    }
	
	@RequestMapping(
			value = "api/vrste/kreirajVrstu/{id}",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VrstaPDVa> addStopu(@RequestBody VrstaPDVa vrstaPDVa, @PathVariable Integer id) {
        VrstaPDVa vrsta = new VrstaPDVa();
        StopaPDVa stopaPDVa = stopaPDVaRepository.findOne(id);
        vrsta.setStopPDVa(stopaPDVa);
        vrsta.setNazivVrstePDVa(vrstaPDVa.getNazivVrstePDVa());
        vrstaPDVaService.save(vrsta);
        return new ResponseEntity<VrstaPDVa>(vrsta, HttpStatus.OK);
    }
	
	@RequestMapping(
            value    = "api/vrste/nadjiVrstu/{id}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<VrstaPDVa> findVrstu(@PathVariable Integer id) {
		VrstaPDVa vrsta = vrstaPDVaService.findVrstu(id);
        return new ResponseEntity<VrstaPDVa>(vrsta, HttpStatus.OK);
    }
	
	@RequestMapping(
			value = "api/vrste/obrisiVrstu/{id}",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<VrstaPDVa>> removeVrsta(@PathVariable Integer id){
		VrstaPDVa vrsta = vrstaPDVaService.findVrstu(id);
		vrstaPDVaService.deleteVrstu(vrsta);
		Collection<VrstaPDVa> sveVrste = vrstaPDVaService.getAll();
		return new ResponseEntity<Collection<VrstaPDVa>>(sveVrste, HttpStatus.OK);
	}
	
	
	@RequestMapping(
			value = "api/vrste/izmeniVrstu/{id}",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VrstaPDVa> updateVrstu(@RequestBody VrstaPDVa vrstaPDVa, @PathVariable Integer id) {
        VrstaPDVa vrsta = vrstaPDVaService.findVrstu(vrstaPDVa.getId());
        vrsta.setNazivVrstePDVa(vrstaPDVa.getNazivVrstePDVa());
        StopaPDVa stopa = stopaPDVaRepository.findOne(id);
        vrsta.setStopPDVa(stopa);
        vrstaPDVaService.save(vrsta);
        return new ResponseEntity<VrstaPDVa>(vrsta, HttpStatus.OK);
    }

}
