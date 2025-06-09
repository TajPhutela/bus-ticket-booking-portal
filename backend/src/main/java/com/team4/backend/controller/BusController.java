package com.team4.backend.controller;

import com.team4.backend.dto.BusDto;
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
    public ResponseEntity<ApiResponse<List<BusDto>>> getAllBusesDetails(){
        List<Bus> buses = busRepository.findAll();
        List<BusDto> busDtos =  buses.stream()
                .map(busMapper::toDto)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(busDtos));
    }

    @GetMapping("/id/{busId}")
    public ResponseEntity<ApiResponse<BusDto>> getBusById(@PathVariable int busId){
        Optional<Bus> result = busRepository.findById(busId);
        if(result.isPresent()){
            BusDto busDto = busMapper.toDto(result.get());
            return ResponseEntity.ok(ApiResponse.success(busDto));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "No bus with id " + busId));
    }

    @GetMapping("/capacity/{capacity}")
    public ResponseEntity<ApiResponse<List<BusDto>>> getBusByCapacity(@PathVariable int capacity){
        List<Bus> buses = busRepository.findByCapacity(capacity);
        List<BusDto> busDtos = buses.stream().map(busMapper::toDto).toList();
        if (busDtos.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "No buses with capacity "+capacity));
        }
        return ResponseEntity.ok(ApiResponse.success(busDtos));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<ApiResponse<List<BusDto>>> getBusByType(@PathVariable String type){
        List<Bus> buses = busRepository.findByTypeIgnoreCase(type);
        List<BusDto> busDtos = buses.stream().map(busMapper::toDto).toList();
        if(busDtos.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "No buses with type "+type));
        }
        return ResponseEntity.ok(ApiResponse.success(busDtos));
    }

    @GetMapping("/registration-number/{registrationNumber}")
    public ResponseEntity<ApiResponse<BusDto>> getBusByRegistrationNumber(@PathVariable String registrationNumber){
        Optional<Bus> bus = busRepository.findByRegistrationNumber(registrationNumber);
        if(bus.isPresent()){
            BusDto busDto = busMapper.toDto(bus.get());
            return ResponseEntity.ok(ApiResponse.success(busDto));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(HttpStatus.NOT_FOUND.value(),"Bus with registration number "+registrationNumber+" not found"));
    }

    @GetMapping("/office-id/{officeId}")
    public ResponseEntity<ApiResponse<List<BusDto>>> getBusByOfficeId(@PathVariable int officeId){
        List<Bus> buses = busRepository.findByOfficeId(officeId);
        List<BusDto> busDtos = buses.stream().map(busMapper::toDto).toList();
        if (busDtos.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "No buses with office id "+officeId));
        }
        return ResponseEntity.ok(ApiResponse.success(busDtos));
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<BusDto>> addBus(@RequestBody @Valid BusDto busDto) {
        try {
            if (busDto.id() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "Bus id cannot be null"));
            }

            if (busRepository.existsById(busDto.id())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(ApiResponse.error(HttpStatus.CONFLICT.value(), "Bus with id " + busDto.id() + " already exists"));
            }

            Bus bus = busMapper.toEntity(busDto);
            Bus savedBus = busRepository.save(bus);
            BusDto savedBusDto = busMapper.toDto(savedBus);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(HttpStatus.CREATED.value(), "Bus added successfully", savedBusDto));

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }


    @PutMapping("/{busId}")
    public ResponseEntity<ApiResponse<BusDto>> updateBus(@PathVariable Integer busId,
                                                         @Valid @RequestBody BusDto busDto) {
        Optional<Bus> existingBusOpt = busRepository.findById(busId);

        if (existingBusOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(404, "Bus not found"));
        }

        Bus updatedBus = busMapper.toEntity(busDto);
        updatedBus.setId(busId);

        Bus savedBus = busRepository.save(updatedBus);

        return ResponseEntity.ok(ApiResponse.success(busMapper.toDto(savedBus)));
    }

}
