package com.team4.backend.controller;

import com.team4.backend.dto.request.DriverRequestDto;
import com.team4.backend.dto.response.ApiResponse;
import com.team4.backend.dto.response.DriverResponseDto;
import com.team4.backend.dto.response.PagedResponse;
import com.team4.backend.entities.Driver;
import com.team4.backend.mapper.DriverMapper;
import com.team4.backend.repository.DriverRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<ApiResponse<PagedResponse<DriverResponseDto>>> getAllDrivers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Driver> driverPage = driverRepository.findAll(pageable);

        List<DriverResponseDto> driverDtos = driverPage.getContent()
                .stream()
                .map(driverMapper::toResponseDto)
                .toList();

        PagedResponse<DriverResponseDto> pagedResponse = new PagedResponse<>(
                driverDtos,
                driverPage.getNumber(),
                driverPage.getSize(),
                driverPage.getTotalElements(),
                driverPage.getTotalPages(),
                driverPage.isLast()
        );

        return ResponseEntity.ok(ApiResponse.success(pagedResponse));
    }


    @GetMapping("/id/{driver_id}")
    public ResponseEntity<ApiResponse<DriverResponseDto>> getDriverById(@PathVariable("driver_id") int driverId) {
        return driverRepository.findById(driverId)
                .map(driver -> ResponseEntity.ok(ApiResponse.success(driverMapper.toResponseDto(driver))))
                .orElseGet(() -> new ResponseEntity<>(ApiResponse.error(404, "Driver not found"), HttpStatus.NOT_FOUND));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse<List<DriverResponseDto>>> getDriverByName(@PathVariable String name) {
        List<Driver> drivers = driverRepository.findByName(name);
        if (drivers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(404, "No drivers found with name " + name));
        }
        List<DriverResponseDto> dtos = drivers.stream().map(driverMapper::toResponseDto).toList();
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @GetMapping("/office/{office_id}")
    public ResponseEntity<ApiResponse<List<DriverResponseDto>>> getDriverByOfficeId(@PathVariable("office_id") int officeId) {
        List<Driver> drivers = driverRepository.findByOfficeId(officeId);
        if (drivers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(404, "No drivers found for office ID " + officeId));
        }
        List<DriverResponseDto> dtos = drivers.stream().map(driverMapper::toResponseDto).toList();
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @GetMapping("/address/{address_id}")
    public ResponseEntity<ApiResponse<List<DriverResponseDto>>> getDriverByAddressId(@PathVariable("address_id") int addressId) {
        List<Driver> drivers = driverRepository.findByAddressId(addressId);
        if (drivers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(404, "No drivers found for address ID " + addressId));
        }
        List<DriverResponseDto> dtos = drivers.stream().map(driverMapper::toResponseDto).toList();
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<DriverResponseDto>> addDriver(@RequestBody DriverRequestDto driverRequestDto) {
        if (driverRequestDto.id() != null && driverRepository.existsById(driverRequestDto.id())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "Driver with ID " + driverRequestDto.id() + " already exists"));
        }

        Driver savedDriver = driverRepository.save(driverMapper.toEntity(driverRequestDto));
        DriverResponseDto responseDto = driverMapper.toResponseDto(savedDriver);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(HttpStatus.CREATED.value(), "Driver created", responseDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DriverResponseDto>> updateDriver(
            @PathVariable Integer id,
            @RequestBody DriverRequestDto driverRequestDto) {

        return driverRepository.findById(id)
                .map(existingDriver -> {
                    Driver updated = driverMapper.partialUpdate(driverRequestDto, existingDriver);
                    Driver saved = driverRepository.save(updated);
                    return ResponseEntity.ok(ApiResponse.success(
                            HttpStatus.OK.value(),
                            "Driver updated successfully",
                            driverMapper.toResponseDto(saved)
                    ));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error(404, "Driver not found")));
    }

}
