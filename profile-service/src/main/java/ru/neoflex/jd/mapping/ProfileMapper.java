package ru.neoflex.jd.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.neoflex.jd.dto.ProfileDtoRequest;
import ru.neoflex.jd.dto.ProfileDtoResponse;
import ru.neoflex.jd.entity.Profile;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileDtoResponse toDto(Profile account);
    @Mapping(target ="password", source = "password.password" )
    ProfileDtoRequest toDtoRequest(Profile account);
    @Mapping(target ="password.password", source = "password" )
    Profile toEntity(ProfileDtoRequest profileDtoRequest);
}
