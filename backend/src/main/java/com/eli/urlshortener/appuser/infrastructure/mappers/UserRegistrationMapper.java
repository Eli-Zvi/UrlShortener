package com.eli.urlshortener.appuser.infrastructure.mappers;

import com.eli.urlshortener.appuser.DTOs.UserRegistrationResponseDto;
import com.eli.urlshortener.appuser.AppUser;
import org.mapstruct.Mapper;

/**
 * Interface implemented by MapStruct dependency to map an AppUser to DTOs
 * @author Ilay Zvi
 */
@Mapper(componentModel = "spring")
public interface UserRegistrationMapper {

    UserRegistrationResponseDto toResponseDto(AppUser user);
}
