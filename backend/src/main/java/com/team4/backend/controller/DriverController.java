package com.team4.backend.controller;

import com.team4.backend.dto.DriverDto;
import com.team4.backend.dto.response.ApiResponse;
import com.team4.backend.entities.Driver;
import com.team4.backend.mapper.DriverMapper;
import com.team4.backend.repository.DriverRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/drivers")
public class DriverController {

    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;

    public DriverController(DriverRepository driverRepository, DriverMapper driverMapper) {
        this.driverRepository = driverRepository;
        this.driverMapper = driverMapper;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<DriverDto>>> getAllDrivers() {
        List<DriverDto> driverDtos = driverRepository.findAll()
                .stream().map(driverMapper::toDto).toList();
        return ResponseEntity.ok(ApiResponse.success(driverDtos));
    }

    @GetMapping("/id/{driver_id}")
    public ResponseEntity<ApiResponse<DriverDto>> getDriverById(@PathVariable("driver_id") int driverId) {
        return driverRepository.findById(driverId)
                .map(driver -> ResponseEntity.ok(ApiResponse.success(driverMapper.toDto(driver))))
                .orElseGet(() -> new ResponseEntity<>(ApiResponse.error(404, "Driver not found"), HttpStatus.NOT_FOUND));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse<List<DriverDto>>> getDriverByName(@PathVariable("name") String name) {
        List<Driver> drivers = driverRepository.findByName(name);
        if (drivers.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error(404, "No drivers found with name " + name), HttpStatus.NOT_FOUND);
        }
        List<DriverDto> driverDtos = drivers.stream().map(driverMapper::toDto).toList();
        return ResponseEntity.ok(ApiResponse.success(driverDtos));
    }

    @GetMapping("/office/{office_id}")
    public ResponseEntity<ApiResponse<List<DriverDto>>> getDriverByOfficeId(@PathVariable("office_id") int officeId) {
        List<Driver> drivers = driverRepository.findByOfficeId(officeId);
        if (drivers.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error(404, "No drivers found for office ID " + officeId), HttpStatus.NOT_FOUND);
        }
        List<DriverDto> driverDtos = drivers.stream().map(driverMapper::toDto).toList();
        return ResponseEntity.ok(ApiResponse.success(driverDtos));
    }

    @GetMapping("/address/{address_id}")
    public ResponseEntity<ApiResponse<List<DriverDto>>> getDriverByAddressId(@PathVariable("address_id") int addressId) {
        List<Driver> drivers = driverRepository.findByAddressId(addressId);
        if (drivers.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error(404, "No drivers found for address ID " + addressId), HttpStatus.NOT_FOUND);
        }
        List<DriverDto> driverDtos = drivers.stream().map(driverMapper::toDto).toList();
        return ResponseEntity.ok(ApiResponse.success(driverDtos));
    }


    @PostMapping("")
    public ResponseEntity<ApiResponse<DriverDto>> addDriver(@RequestBody DriverDto driverDto) {
        Driver savedDriver = driverRepository.save(driverMapper.toEntity(driverDto));
        return new ResponseEntity<>(
                ApiResponse.success(HttpStatus.CREATED.value(), "Driver created", driverMapper.toDto(savedDriver)),
                HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DriverDto>> updateDriver(
            @PathVariable Integer id,
            @RequestBody DriverDto driverDto) {

        return driverRepository.findById(id)
                .map(existingDriver -> {
                    Driver updated = driverMapper.partialUpdate(driverDto, existingDriver);
                    Driver saved = driverRepository.save(updated);
                    return ResponseEntity.ok(
                            ApiResponse.success(HttpStatus.OK.value(), "Driver updated successfully", driverMapper.toDto(saved))
                    );
                })
                .orElseGet(() -> new ResponseEntity<>(
                        ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Driver not found"),
                        HttpStatus.NOT_FOUND));
    }

}
