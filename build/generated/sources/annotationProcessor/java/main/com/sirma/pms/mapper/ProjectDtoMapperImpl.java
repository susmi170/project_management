package com.sirma.pms.mapper;

import com.sirma.pms.dto.ProjectDto;
import com.sirma.pms.entity.Project;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-28T16:32:12+0530",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.7.jar, environment: Java 17.0.9 (Azul Systems, Inc.)"
)
public class ProjectDtoMapperImpl implements ProjectDtoMapper {

    @Override
    public ProjectDto projectEntityToDto(Project source) {
        if ( source == null ) {
            return null;
        }

        ProjectDto projectDto = new ProjectDto();

        projectDto.setId( source.getId() );
        projectDto.setName( source.getName() );
        projectDto.setDescription( source.getDescription() );
        projectDto.setStartDate( source.getStartDate() );
        projectDto.setEndDate( source.getEndDate() );
        projectDto.setStatus( source.getStatus() );
        projectDto.setCreatedBy( source.getCreatedBy() );
        projectDto.setUpdatedBy( source.getUpdatedBy() );

        return projectDto;
    }

    @Override
    public Project projectDtoToEntity(ProjectDto source) {
        if ( source == null ) {
            return null;
        }

        Project project = new Project();

        project.setId( source.getId() );
        project.setName( source.getName() );
        project.setDescription( source.getDescription() );
        project.setStartDate( source.getStartDate() );
        project.setEndDate( source.getEndDate() );
        project.setStatus( source.getStatus() );
        project.setCreatedBy( source.getCreatedBy() );
        project.setUpdatedBy( source.getUpdatedBy() );

        return project;
    }
}
