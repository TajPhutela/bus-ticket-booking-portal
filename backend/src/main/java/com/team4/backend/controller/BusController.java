package com.team4.backend.controller;

import com.team4.backend.dto.BusDto;
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

    @GetMapping()
    public ResponseEntity<List<BusDto>> getAllBusesDetails(){
        List<Bus> buses = busRepository.findAll();
        List<BusDto> busDtos =  buses.stream()
                .map(BusMapper::toDTO)
                .toList();
        return ResponseEntity.ok(busDtos);
    }

    @GetMapping("/{busId}")
    public ResponseEntity<BusDto> getBusById(@PathVariable int busId){
        Optional<Bus> result = busRepository.findById(busId);
        if(result.isPresent()){
            BusDto busDto = BusMapper.toDTO(result.get());
            return ResponseEntity.ok(busDto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
