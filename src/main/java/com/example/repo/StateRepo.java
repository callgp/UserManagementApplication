package com.example.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entities.CityEntity;
import com.example.entities.CountryEntity;
import com.example.entities.StateEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;



	public interface StateRepo extends JpaRepository<StateEntity, Integer> {
	    // You can define custom query methods here if needed
		
	@Query(value="select * from state_master where country_id=:countryId", nativeQuery=true)
	public List<StateEntity> findByCountryId(Integer countryId);
		
}
