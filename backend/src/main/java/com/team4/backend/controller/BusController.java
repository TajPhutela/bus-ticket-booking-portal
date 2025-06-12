package com.team4.backend.controller;

import com.team4.backend.dto.response.BusTypeCountDto;
import com.team4.backend.dto.request.BusRequestDto;
import com.team4.backend.dto.response.ApiResponse;
import com.team4.backend.dto.response.BusResponseDto;
import com.team4.backend.dto.response.PagedResponse;
import com.team4.backend.entities.Bus;
import com.team4.backend.mapper.BusMapper;
import com.team4.backend.repository.BusRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/buses")
public class BusController {

    private final BusRepository busRepository;
    private final BusMapper busMapper;

    public BusController(BusRepository busRepository, BusMapper busMapper) {
        this.busRepository = busRepository;
        this.busMapper = busMapper;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<PagedResponse<BusResponseDto>>> getAllBusesDetails(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Bus> busPage = busRepository.findAll(pageable);

        List<BusResponseDto> busResponseDtos = busPage.getContent()
                .stream()
                .map(busMapper::toResponseDto)
                .toList();

        PagedResponse<BusResponseDto> pagedResponse = new PagedResponse<>(
                busResponseDtos,
                busPage.getNumber(),
                busPage.getSize(),
                busPage.getTotalElements(),
                busPage.getTotalPages(),
                busPage.isLast()
        );

        return ResponseEntity.ok(ApiResponse.success(pagedResponse));
    }


    @GetMapping("/id/{busId}")
    public ResponseEntity<ApiResponse<BusResponseDto>> getBusById(@PathVariable int busId) {
        Optional<Bus> result = busRepository.findById(busId);
        if (result.isPresent()) {
            BusResponseDto dto = busMapper.toResponseDto(result.get());
            return ResponseEntity.ok(ApiResponse.success(dto));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "No bus with id " + busId));
    }

    @GetMapping("/capacity/{capacity}")
    public ResponseEntity<ApiResponse<List<BusResponseDto>>> getBusByCapacity(@PathVariable int capacity) {
        List<Bus> buses = busRepository.findByCapacity(capacity);
        List<BusResponseDto> dtos = buses.stream().map(busMapper::toResponseDto).toList();
        if (dtos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "No buses with capacity " + capacity));
        }
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<ApiResponse<List<BusResponseDto>>> getBusByType(@PathVariable String type) {
        List<Bus> buses = busRepository.findByTypeIgnoreCase(type);
        List<BusResponseDto> dtos = buses.stream().map(busMapper::toResponseDto).toList();
        if (dtos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "No buses with type " + type));
        }
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @GetMapping("/registration-number/{registrationNumber}")
    public ResponseEntity<ApiResponse<BusResponseDto>> getBusByRegistrationNumber(@PathVariable String registrationNumber) {
        Optional<Bus> bus = busRepository.findByRegistrationNumber(registrationNumber);
        if (bus.isPresent()) {
            BusResponseDto dto = busMapper.toResponseDto(bus.get());
            return ResponseEntity.ok(ApiResponse.success(dto));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Bus with registration number " + registrationNumber + " not found"));
    }

    @GetMapping("/office-id/{officeId}")
    public ResponseEntity<ApiResponse<List<BusResponseDto>>> getBusByOfficeId(@PathVariable int officeId) {
        List<Bus> buses = busRepository.findByOfficeId(officeId);
        List<BusResponseDto> dtos = buses.stream().map(busMapper::toResponseDto).toList();
        if (dtos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "No buses with office id " + officeId));
        }
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<BusResponseDto>> addBus(@RequestBody @Valid BusRequestDto busRequestDto) {
        try {
            if (busRequestDto.id() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "Bus id cannot be null"));
            }

            if (busRepository.existsById(busRequestDto.id())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "Bus with id " + busRequestDto.id() + " already exists"));
            }

            Bus saved = busRepository.save(busMapper.toEntity(busRequestDto));
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(HttpStatus.CREATED.value(), "Bus added successfully", busMapper.toResponseDto(saved)));

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @PutMapping("/{busId}")
    public ResponseEntity<ApiResponse<BusResponseDto>> updateBus(
            @PathVariable Integer busId,
            @Valid @RequestBody BusRequestDto busRequestDto) {

        if (busRepository.findById(busId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Bus not found"));
        }

        Bus updatedBus = busMapper.toEntity(busRequestDto);
        updatedBus.setId(busId);
        Bus saved = busRepository.save(updatedBus);

        return ResponseEntity.ok(ApiResponse.success(busMapper.toResponseDto(saved)));
    }

    /*
    @GetMapping("/type-counts_1")
    public ResponseEntity<List<BusTypeCountDto>> getBusTypeCountss() {
        // Step 1: Get counts per type
        List<BusTypeCountDto> groupedCounts = busRepository.getBusCountsGroupedByType();

        return new ResponseEntity<>(groupedCounts, HttpStatus.OK);
    }*/
    @GetMapping("/type-counts")
    public ResponseEntity<ApiResponse<List<BusTypeCountDto>>> getBusTypeCounts() {
        // get counts per type
        List<BusTypeCountDto> groupedCounts = busRepository.getBusCountsGroupedByType();

        if (groupedCounts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "No bus types found"));
        }

        //for each type, fetch the list of buses and map to BusRequestDto
        for (BusTypeCountDto dto : groupedCounts) {
            List<BusRequestDto> busList = busRepository.findByType(dto.getType()).stream()
                    .map(
                            bus -> new BusRequestDto(
                                    bus.getId(),
                                    bus.getRegistrationNumber(),
                                    bus.getCapacity(),
                                    bus.getType(),
                                    bus.getOffice().getId()
                            )
                    ).toList();

            dto.setResult(busList);
        }

        return ResponseEntity.ok(ApiResponse.success(groupedCounts));
    }
}