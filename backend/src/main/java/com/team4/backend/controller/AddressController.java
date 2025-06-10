package com.team4.backend.controller;

import com.team4.backend.dto.request.AddressRequestDto;
import com.team4.backend.dto.response.AddressResponseDto;
import com.team4.backend.dto.response.ApiResponse;
import com.team4.backend.entities.Address;
import com.team4.backend.mapper.AddressMapper;
import com.team4.backend.repository.AddressRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/address")
public class AddressController {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public AddressController(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<AddressResponseDto>> createAddress(@Valid @RequestBody AddressRequestDto addressRequestDto) {
        if (addressRequestDto.id() != null && addressRepository.existsById(addressRequestDto.id())) {
            return new ResponseEntity<>(
                    ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "Address with Id " + addressRequestDto.id() + " already exists"),
                    HttpStatus.BAD_REQUEST
            );
        }

        Address address = addressMapper.toEntity(addressRequestDto);
        Address saved = addressRepository.save(address);
        return new ResponseEntity<>(
                ApiResponse.success(
                        HttpStatus.CREATED.value(),
                        "Address created successfully",
                        addressMapper.toResponseDto(saved)),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AddressResponseDto>> updateAddress(@PathVariable Integer id, @Valid @RequestBody AddressRequestDto addressRequestDto) {
        if (!addressRepository.existsById(id)) {
            return new ResponseEntity<>(
                    ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Address not found"),
                    HttpStatus.NOT_FOUND
            );
        }

        Address address = addressMapper.toEntity(addressRequestDto);
        address.setId(id);
        Address updated = addressRepository.save(address);

        return new ResponseEntity<>(
                ApiResponse.success(HttpStatus.OK.value(), "Address updated successfully", addressMapper.toResponseDto(updated)),
                HttpStatus.OK
        );
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<AddressResponseDto>>> getAllAddresses() {
        List<Address> addresses = addressRepository.findAll();
        List<AddressResponseDto> dtos = addresses.stream()
                .map(addressMapper::toResponseDto)
                .toList();
        return new ResponseEntity<>(ApiResponse.success(dtos), HttpStatus.OK);
    }

    @GetMapping("/{address_id}")
    public ResponseEntity<ApiResponse<AddressResponseDto>> getAddressById(@PathVariable("address_id") Integer addressId) {
        Optional<Address> address = addressRepository.findById(addressId);
        if (address.isPresent()) {
            AddressResponseDto dto = addressMapper.toResponseDto(address.get());
            return new ResponseEntity<>(ApiResponse.success(dto), HttpStatus.OK);
        }
        return new ResponseEntity<>(ApiResponse.error(
                HttpStatus.NOT_FOUND.value(),
                "Address with ID " + addressId + " not found"), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<ApiResponse<List<AddressResponseDto>>> getAddressesByCity(@PathVariable("city") String city) {
        List<Address> addresses = addressRepository.findByCity(city);
        if (addresses.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error(
                    HttpStatus.NOT_FOUND.value(),
                    "No addresses found in city: " + city), HttpStatus.NOT_FOUND);
        }
        List<AddressResponseDto> dtos = addresses.stream().map(addressMapper::toResponseDto).toList();
        return new ResponseEntity<>(ApiResponse.success(dtos), HttpStatus.OK);
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<ApiResponse<List<AddressResponseDto>>> getAddressesByState(@PathVariable("state") String state) {
        List<Address> addresses = addressRepository.findByState(state);
        if (addresses.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error(
                    HttpStatus.NOT_FOUND.value(),
                    "No addresses found in state: " + state), HttpStatus.NOT_FOUND);
        }
        List<AddressResponseDto> dtos = addresses.stream().map(addressMapper::toResponseDto).toList();
        return new ResponseEntity<>(ApiResponse.success(dtos), HttpStatus.OK);
    }

    @GetMapping("/zipCode/{zipCode}")
    public ResponseEntity<ApiResponse<List<AddressResponseDto>>> getAddressesByZipCode(@PathVariable("zipCode") String zipCode) {
        List<Address> addresses = addressRepository.findByZipCode(zipCode);
        if (addresses.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error(
                    HttpStatus.NOT_FOUND.value(),
                    "No addresses found with zip code: " + zipCode), HttpStatus.NOT_FOUND);
        }
        List<AddressResponseDto> dtos = addresses.stream().map(addressMapper::toResponseDto).toList();
        return new ResponseEntity<>(ApiResponse.success(dtos), HttpStatus.OK);
    }
}
