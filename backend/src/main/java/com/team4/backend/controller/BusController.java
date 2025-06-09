package com.team4.backend.controller;

import com.team4.backend.dto.request.BusRequestDto;
import com.team4.backend.dto.response.ApiResponse;
import com.team4.backend.entities.Bus;
import com.team4.backend.mapper.BusMapper;
import com.team4.backend.repository.BusRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/buses")
public class BusController {

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private BusMapper busMapper;

    @GetMapping()
    public ResponseEntity<ApiResponse<List<BusRequestDto>>> getAllBusesDetails(){
        List<Bus> buses = busRepository.findAll();
        List<BusRequestDto> busRequestDtos =  buses.stream()
                .map(busMapper::toDto)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(busRequestDtos));
    }

    @GetMapping("/id/{busId}")
    public ResponseEntity<ApiResponse<BusRequestDto>> getBusById(@PathVariable int busId){
        Optional<Bus> result = busRepository.findById(busId);
        if(result.isPresent()){
            BusRequestDto busRequestDto = busMapper.toDto(result.get());
            return ResponseEntity.ok(ApiResponse.success(busRequestDto));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "No bus with id " + busId));
    }

    @GetMapping("/capacity/{capacity}")
    public ResponseEntity<ApiResponse<List<BusRequestDto>>> getBusByCapacity(@PathVariable int capacity){
        List<Bus> buses = busRepository.findByCapacity(capacity);
        List<BusRequestDto> busRequestDtos = buses.stream().map(busMapper::toDto).toList();
        if (busRequestDtos.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "No buses with capacity "+capacity));
        }
        return ResponseEntity.ok(ApiResponse.success(busRequestDtos));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<ApiResponse<List<BusRequestDto>>> getBusByType(@PathVariable String type){
        List<Bus> buses = busRepository.findByTypeIgnoreCase(type);
        List<BusRequestDto> busRequestDtos = buses.stream().map(busMapper::toDto).toList();
        if(busRequestDtos.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "No buses with type "+type));
        }
        return ResponseEntity.ok(ApiResponse.success(busRequestDtos));
    }

    @GetMapping("/registration-number/{registrationNumber}")
    public ResponseEntity<ApiResponse<BusRequestDto>> getBusByRegistrationNumber(@PathVariable String registrationNumber){
        Optional<Bus> bus = busRepository.findByRegistrationNumber(registrationNumber);
        if(bus.isPresent()){
            BusRequestDto busRequestDto = busMapper.toDto(bus.get());
            return ResponseEntity.ok(ApiResponse.success(busRequestDto));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(HttpStatus.NOT_FOUND.value(),"Bus with registration number "+registrationNumber+" not found"));
    }

    @GetMapping("/office-id/{officeId}")
    public ResponseEntity<ApiResponse<List<BusRequestDto>>> getBusByOfficeId(@PathVariable int officeId){
        List<Bus> buses = busRepository.findByOfficeId(officeId);
        List<BusRequestDto> busRequestDtos = buses.stream().map(busMapper::toDto).toList();
        if (busRequestDtos.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "No buses with office id "+officeId));
        }
        return ResponseEntity.ok(ApiResponse.success(busRequestDtos));
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<BusRequestDto>> addBus(@RequestBody @Valid BusRequestDto busRequestDto) {
        try {
            if (busRequestDto.id() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "Bus id cannot be null"));
            }

            if (busRepository.existsById(busRequestDto.id())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(ApiResponse.error(HttpStatus.CONFLICT.value(), "Bus with id " + busRequestDto.id() + " already exists"));
            }

            Bus bus = busMapper.toEntity(busRequestDto);
            Bus savedBus = busRepository.save(bus);
            BusRequestDto savedBusRequestDto = busMapper.toDto(savedBus);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(HttpStatus.CREATED.value(), "Bus added successfully", savedBusRequestDto));

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }


    @PutMapping("/{busId}")
    public ResponseEntity<ApiResponse<BusRequestDto>> updateBus(@PathVariable Integer busId,
                                                                @Valid @RequestBody BusRequestDto busRequestDto) {
        Optional<Bus> existingBusOpt = busRepository.findById(busId);

        if (existingBusOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(404, "Bus not found"));
        }

        Bus updatedBus = busMapper.toEntity(busRequestDto);
        updatedBus.setId(busId);

        Bus savedBus = busRepository.save(updatedBus);

        return ResponseEntity.ok(ApiResponse.success(busMapper.toDto(savedBus)));
    }

}
