package com.team4.backend.controller;

import com.team4.backend.dto.AddressDto;

import com.team4.backend.entities.Address;

import com.team4.backend.mapper.AddressMapper;
import com.team4.backend.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/address")
public class AddressController {

    @Autowired
    public AddressRepository addressRepository;

    @Autowired
    public AddressMapper addressMapper;

    @GetMapping("")
    public ResponseEntity<List<AddressDto>> getAllAddresses() {
        List<Address> addresses = addressRepository.findAll();
        List<AddressDto> addressDtos = addresses.stream()
                .map(addressMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(addressDtos);
    }

    @GetMapping("/{address_id}")
    public ResponseEntity<AddressDto> getAddressById(@PathVariable("address_id") Integer addressId) {
        Optional<Address> addresse = addressRepository.findById(addressId);
        return addresse.map(addressMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<AddressDto>> getAddressesByCity(@PathVariable("city") String city) {
        List<Address> addresses = addressRepository.findByCity(city);
        List<AddressDto> addressDtos = addresses.stream()
                .map(addressMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(addressDtos);
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<List<AddressDto>> getAddressesByState(@PathVariable("state") String state) {
        List<Address> addresses = addressRepository.findByState(state);
        List<AddressDto> addressDtos = addresses.stream()
                .map(addressMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(addressDtos);
    }

    @GetMapping("/zipCode/{zipCode}")
    public ResponseEntity<List<AddressDto>> getAddressesByZipCode(@PathVariable("zipCode") String zipCode) {
        List<Address>addresses = addressRepository.findByZipCode(zipCode);
        List<AddressDto> addressDtos = addresses.stream()
                .map(addressMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(addressDtos);
    }
}
