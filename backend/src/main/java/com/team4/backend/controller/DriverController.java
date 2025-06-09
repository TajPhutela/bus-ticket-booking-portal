package com.team4.backend.controller;

import com.team4.backend.dto.DriverDto;
import com.team4.backend.entities.Driver;
import com.team4.backend.mapper.DriverMapper;
import com.team4.backend.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/drivers")
public class DriverController {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private DriverMapper driverMapper;

    @GetMapping("")
    public ResponseEntity<List<DriverDto>> getAllDrivers() {
        List<Driver> drivers = driverRepository.findAll();
        List<DriverDto> driverDtos = drivers.stream()
                .map(driverMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(driverDtos, HttpStatus.OK);
    }

    @GetMapping("/id/{driver_id}")
    public ResponseEntity<DriverDto> getDriverById(@PathVariable("driver_id") int driverId) {
        Optional<Driver> optionalDriver = driverRepository.findById(driverId);
        return optionalDriver
                .map(driver -> new ResponseEntity<>(driverMapper.toDto(driver), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<DriverDto>> getDriverByName(@PathVariable("name") String name) {
        List<Driver> drivers = driverRepository.findByName(name);
        if (drivers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<DriverDto> driverDtos = drivers.stream()
                .map(driverMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(driverDtos, HttpStatus.OK);
    }

    @GetMapping("/office/{office_id}")
    public ResponseEntity<List<DriverDto>> getDriverByOfficeId(@PathVariable("office_id") int officeId) {
        List<Driver> drivers = driverRepository.findByOfficeId(officeId);
        if (drivers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<DriverDto> driverDtos = drivers.stream()
                .map(driverMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(driverDtos, HttpStatus.OK);
    }

    @GetMapping("/address/{address_id}")
    public ResponseEntity<List<DriverDto>> getDriverByAddressId(@PathVariable("address_id") int addressId) {
        List<Driver> drivers = driverRepository.findByAddressId(addressId);
        if (drivers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<DriverDto> driverDtos = drivers.stream()
                .map(driverMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(driverDtos, HttpStatus.OK);
    }
}

