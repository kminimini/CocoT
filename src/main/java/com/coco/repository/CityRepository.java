package com.coco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coco.domain.City;
@Repository
public interface CityRepository extends JpaRepository<City, Long> {

}
