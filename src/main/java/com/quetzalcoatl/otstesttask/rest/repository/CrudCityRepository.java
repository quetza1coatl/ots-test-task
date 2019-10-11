package com.quetzalcoatl.otstesttask.rest.repository;

import com.quetzalcoatl.otstesttask.rest.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CrudCityRepository extends JpaRepository<City, Integer> {

    List<City> findByNameIgnoreCase(String name);

    @Query("SELECT c.name from City c ORDER BY c.name")
    List<String> findAllCityNamesOrdered();
}
