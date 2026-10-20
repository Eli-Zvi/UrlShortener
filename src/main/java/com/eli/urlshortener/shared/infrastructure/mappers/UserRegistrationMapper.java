package com.eli.urlshortener.shared.infrastructure.mappers;

import com.eli.urlshortener.shared.DTOs.UserRegistrationResponseDto;
import com.eli.urlshortener.shared.models.AppUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRegistrationMapper {

    UserRegistrationResponseDto toResponseDto(AppUser user);
}
