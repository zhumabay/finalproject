package kz.bitlab.bootcamp.finalproject.mapper;

import kz.bitlab.bootcamp.finalproject.dto.RoleDto;
import kz.bitlab.bootcamp.finalproject.models.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDto toDto(Role role);
    Role toEntity(RoleDto roleDto);

    List<RoleDto> toDtoList(List<Role> roles);
    List<Role> toEntityList(List<RoleDto> roleDtoList);
}
