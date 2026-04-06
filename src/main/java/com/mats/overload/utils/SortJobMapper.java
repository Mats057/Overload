package com.mats.overload.utils;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.mats.overload.domain.SortJob;

@Mapper(componentModel = "spring")
public interface SortJobMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "payload", source = "payload")
    @Mapping(target = "status", constant = "PENDING")
    SortJob payloadToSortJob(String payload);
}
