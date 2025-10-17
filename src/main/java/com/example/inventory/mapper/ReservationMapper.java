package com.example.inventory.mapper;

import com.example.inventory.dto.request.ReservationRequestDto;
import com.example.inventory.dto.response.ReservationResponseDto;
import com.example.inventory.model.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    @Mapping(source = "itemId", target = "item.id")
    Reservation toEntity(ReservationRequestDto reservationRequestDto);

    @Mapping(source = "item.id", target = "itemId")
    @Mapping(source = "item.name", target = "itemName")
    ReservationResponseDto toDto(Reservation reservation);
}
