package com.quetzalcoatl.otstesttask.rest.repository;

import com.quetzalcoatl.otstesttask.rest.model.City;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CrudCityRepository extends JpaRepository<City, Integer> {

}
