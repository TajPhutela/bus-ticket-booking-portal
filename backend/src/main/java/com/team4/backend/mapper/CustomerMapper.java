package com.team4.backend.mapper;

import com.team4.backend.entities.Customer;
import com.team4.backend.dto.CustomerDto;
import com.team4.backend.mapper.helper.CustomerMapperHelper;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = CustomerMapperHelper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CustomerMapper {

//    @Mapping(source = "addressId", target = "address", qualifiedByName = "addressFromId")
    Customer toEntity(CustomerDto customerDto);

//    @Mapping(source = "address.id", target = "addressId")
    CustomerDto toDto(Customer customer);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    @Mapping(source = "addressId", target = "address", qualifiedByName = "addressFromId")
    Customer partialUpdate(CustomerDto customerDto, @MappingTarget Customer customer);
}