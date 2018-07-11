package com.poslovna.market.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poslovna.market.models.BusinessPartner;
import com.poslovna.market.models.Company;
import com.poslovna.market.repositories.BusinessPartnerRepository;
import com.poslovna.market.repositories.CompanyRepository;

@Service
public class BusinessPartnerService {
	
	@Autowired 
	private BusinessPartnerRepository businessPartnerRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	public Collection<BusinessPartner> findByCompany1(Integer id){
		Company company1 = companyRepository.findOne(id);
		return businessPartnerRepository.findByCompany1(company1);
	}

	public BusinessPartner findOne(Integer kupId) {
		return businessPartnerRepository.findOne(kupId);
	}
	
	public BusinessPartner save(BusinessPartner bp) {
		return businessPartnerRepository.save(bp);
	}
	
	public BusinessPartner findByCompany1AndCompany2(Company company1, Company company2){
		return businessPartnerRepository.findByCompany1AndCompany2(company1, company2);
	}
	
	

}
