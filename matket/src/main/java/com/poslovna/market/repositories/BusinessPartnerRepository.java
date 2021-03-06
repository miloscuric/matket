package com.poslovna.market.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poslovna.market.models.BusinessPartner;
import com.poslovna.market.models.Company;

public interface BusinessPartnerRepository extends JpaRepository<BusinessPartner, Integer> {

	public Collection<BusinessPartner> findByCompany1(Company company1);
	
	public BusinessPartner findByCompany1AndCompany2(Company company1, Company company2);
}
