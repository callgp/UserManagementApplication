package com.example.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.CityEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


public interface CityRepo extends JpaRepository<CityEntity, Integer> {
    // You can define custom query methods here if needed
	
	public List<CityEntity> findByStateId(Integer stateId);
	
}
	
