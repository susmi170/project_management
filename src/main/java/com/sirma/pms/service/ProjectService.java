package com.sirma.pms.service;

import com.sirma.pms.constants.ProjectManagementConstant;
import com.sirma.pms.dao.ProjectDAO;
import com.sirma.pms.dto.ProjectDto;
import com.sirma.pms.entity.Project;
import com.sirma.pms.exception.ProjectManagemnetException;
import com.sirma.pms.exception.ProjectNotFoundException;
import com.sirma.pms.mapper.ProjectDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProjectService {
    @Autowired
    ProjectDAO projectDAO;
    ProjectDtoMapper PROJECT_MAPPER = ProjectDtoMapper.INSTANCE;

    /**
     * This method is dedicated to get a project by a id from DB
     * If project not found will throw ProjectNotFoundException
     *
     * @param id
     * @return
     * @throws ProjectNotFoundException
     */
    public ProjectDto getProjectById(int id) throws ProjectNotFoundException {
        Optional<Project> projectOptional = projectDAO.findById(id);
        Project project = projectOptional.orElseThrow(() -> new ProjectNotFoundException(ProjectManagementConstant.PROJECT_NOT_FOUND + "" + id));
        return ProjectDtoMapper.INSTANCE.projectEntityToDto(project);


    }

    /**
     * This method used to get all the projects available in DB
     * If project not found will throw ProjectNotFoundException
     *
     * @return
     * @throws ProjectNotFoundException
     */
    public List<ProjectDto> getAllProjects() throws ProjectNotFoundException {
        Iterable<Project> projects = projectDAO.findAll();
        List<ProjectDto> projectDtoList = new ArrayList<>();
        for (Project project : projects) {
            ProjectDto projectDto = PROJECT_MAPPER.projectEntityToDto(project);
            projectDtoList.add(projectDto);
        }
        if (projectDtoList.size() == 0)
            throw new ProjectNotFoundException(ProjectManagementConstant.PROJECTS_NOT_FOUND);
        return projectDtoList;
    }

    /**
     * This used to save a project in DB with given project details
     *
     * @param projectDto
     * @return
     */
    public ProjectDto saveProject(ProjectDto projectDto) throws ProjectManagemnetException {
        try {
            Project projectEntity = ProjectDtoMapper.INSTANCE.projectDtoToEntity(projectDto);
            Project project = projectDAO.save(projectEntity);
            return ProjectDtoMapper.INSTANCE.projectEntityToDto(project);
        } catch (Exception exception) {
            throw new ProjectManagemnetException(" Project Management exception");
        }
    }

    /**
     * This method is dedicated to update a project by the project details passed to the method.
     * In case the project not found in DB it will throw ProjectNotFoundException
     *
     * @param projectDto
     * @return
     * @throws ProjectNotFoundException
     */
    public ProjectDto updateProject(ProjectDto projectDto) throws ProjectNotFoundException, ProjectManagemnetException {
        getProjectById(projectDto.getId());
        return saveProject(projectDto);

    }

    /**
     * This method is to delete a project by id ,
     * In case project not found in DB it throw exception .
     * if project available or not then we can call get project first and then if found proceed further.
     * If not found then throw exception
     *
     * @param id
     * @return
     */
    public String deleteProject(int id) throws ProjectNotFoundException {
        getProjectById(id);
        projectDAO.deleteById(id);
        return ProjectManagementConstant.PROJECT_DELETED_SUCCESS + " ID :" + id;

    }
}
