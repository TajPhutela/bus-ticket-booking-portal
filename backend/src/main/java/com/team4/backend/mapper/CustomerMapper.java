package com.team4.backend.mapper;

import com.team4.backend.dto.request.CustomerRequestDto;
import com.team4.backend.entities.Customer;
import com.team4.backend.mapper.helper.CustomerMapperHelper;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = CustomerMapperHelper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CustomerMapper {

    @Mapping(source = "addressId", target = "address", qualifiedByName = "addressFromId")
    Customer toEntity(CustomerRequestDto customerRequestDto);

    @Mapping(source = "address.id", target = "addressId")
    CustomerRequestDto toDto(Customer customer);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "addressId", target = "address", qualifiedByName = "addressFromId")
    Customer partialUpdate(CustomerRequestDto customerRequestDto, @MappingTarget Customer customer);
}