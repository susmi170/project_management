package com.sirma.pms.mapper;


import com.sirma.pms.dto.ProjectDto;
import com.sirma.pms.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface to handle convert Project entity to DTO and vice versa
 */
@Mapper
public interface ProjectDtoMapper {
    ProjectDtoMapper INSTANCE = Mappers.getMapper( ProjectDtoMapper.class );

    ProjectDto projectEntityToDto(Project source);
    Project projectDtoToEntity(ProjectDto source);
}
