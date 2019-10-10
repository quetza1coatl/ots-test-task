package com.quetzalcoatl.otstesttask.rest.web;

import com.quetzalcoatl.otstesttask.rest.model.City;
import com.quetzalcoatl.otstesttask.rest.repository.CrudCityRepository;
import com.quetzalcoatl.otstesttask.rest.exceptions.CityIdMismatchException;
import com.quetzalcoatl.otstesttask.rest.exceptions.CityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(CityRestController.REST_URL)
public class CityRestController {
    static final String REST_URL = "/cities";
    private final CrudCityRepository repository;

    public CityRestController(CrudCityRepository repository) {
        this.repository = repository;
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<City> getAllCities() {
        return repository.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<City> createCity(@Valid @RequestBody City city) {
        if(city.getId() != null){
            city.setId(null);
        }
        City created = repository.save(city);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateCity(@Valid @RequestBody City city, @PathVariable("id") int id) {
        if (city.getId() == null || city.getId() != id) {
            throw new CityIdMismatchException("Mismatch id or id is not presented in a body");
        }
        repository.findById(id).orElseThrow(() -> new CityNotFoundException("Not found entity with id=" + id));
        repository.save(city);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCity(@PathVariable("id") int id) {
        repository.findById(id).orElseThrow(() -> new CityNotFoundException("Not found entity with id=" + id));
        repository.deleteById(id);
    }

}
