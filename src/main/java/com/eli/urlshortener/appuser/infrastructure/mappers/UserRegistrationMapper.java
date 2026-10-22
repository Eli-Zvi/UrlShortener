package com.eli.urlshortener.appuser.infrastructure.mappers;

import com.eli.urlshortener.appuser.DTOs.UserRegistrationResponseDto;
import com.eli.urlshortener.appuser.AppUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRegistrationMapper {

    UserRegistrationResponseDto toResponseDto(AppUser user);
}
