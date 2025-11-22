package com.ufro.microservice.location_API.place.mapper;

import com.ufro.microservice.location_API.place.dto.StatDataDTO;
import com.ufro.microservice.location_API.place.model.StatData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IStatDataMapper {
    StatData toStatData(StatDataDTO statData);
    StatDataDTO toStatDataDTO(StatData statData);
}
