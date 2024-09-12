package com.example.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.CityEntity;
import com.example.entities.CountryEntity;
import com.example.entities.StateEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Repository
public interface CountryRepo extends JpaRepository<CountryEntity, Integer> {
    // You can define custom query methods here if needed
//	public List<StateEntity> findByCountryCountryId(Integer countryId);
}
	
