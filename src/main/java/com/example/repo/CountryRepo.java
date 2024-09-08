package com.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.CityEntity;
import com.example.entities.CountryEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


public interface CountryRepo extends JpaRepository<CountryEntity, Integer> {
    // You can define custom query methods here if needed
}
	
