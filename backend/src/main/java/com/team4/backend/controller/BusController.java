package com.team4.backend.controller;

import com.team4.backend.dto.BusDto;
import com.team4.backend.dto.response.ApiResponse;
import com.team4.backend.entities.Bus;
import com.team4.backend.mapper.BusMapper;
import com.team4.backend.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
