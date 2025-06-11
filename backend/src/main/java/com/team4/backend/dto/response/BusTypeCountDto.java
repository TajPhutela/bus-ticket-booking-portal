package com.team4.backend.dto.response;

import com.team4.backend.dto.request.BusRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusTypeCountDto {
    private String type;
    private Long count;
    private List<BusRequestDto> result;
}
