package ru.neoflex.jd.mapping;

import org.mapstruct.Mapper;
import ru.neoflex.jd.dto.ProfileDto;
import ru.neoflex.jd.entity.Profile;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    ProfileDto toDto(Profile account);

    Profile toEntity(ProfileDto profileDto);
}
