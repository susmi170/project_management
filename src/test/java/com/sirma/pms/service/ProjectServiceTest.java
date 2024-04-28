package com.sirma.pms.service;

import com.sirma.pms.constants.ProjectManagementConstant;
import com.sirma.pms.dto.ProjectDto;
import com.sirma.pms.exception.ProjectManagemnetException;
import com.sirma.pms.exception.ProjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * we have loaded project id with 2 in in-mem h2 DB using data.sql part of resources/data.sql of test folder
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProjectServiceTest {
    @Autowired
    ProjectService projectService;


    /**
     * Junit
     * Get project positive scenario , passing id which is present in h2 db
     */
    @Test
    void getProjectById_positive() throws ProjectNotFoundException {
        ProjectDto projectDto = projectService.getProjectById(2);
        assertEquals(projectDto.getId(), 2);
    }

    /**
     * Junit
     * Get project positive scenario , passing id which is not present in h2 db
     */
    @Test
    void getProjectById_negative() {
        int id = 1;
        try {
            projectService.getProjectById(id);
        } catch (ProjectNotFoundException exception) {
            assertEquals(exception.getMessage(), ProjectManagementConstant.PROJECT_NOT_FOUND + "" + id);
        }
    }

    /**
     * Junit
     * Get all projects negative scenario , no project in db , throws exception
     */
    @Test
    void getAllProjects_negative() throws ProjectNotFoundException, ProjectManagemnetException {
        projectService.deleteProject(2);
        try {
            projectService.getAllProjects();
        } catch (ProjectNotFoundException exception) {
            assertEquals(exception.getMessage(), ProjectManagementConstant.PROJECTS_NOT_FOUND);
        }
        //set up for remaining test cases
        ProjectDto projectDto = createTestProjectData(2);
        projectService.saveProject(projectDto);
    }

    /**
     * Junit
     * Get all project positive scenario , two project added in db
     */
    @Test
    void getAllProjects_positive() throws ProjectNotFoundException, ProjectManagemnetException {
        ProjectDto projectDto = createTestProjectData(7);
        projectService.saveProject(projectDto);
        List<ProjectDto> projectDtos = projectService.getAllProjects();

        assertEquals(projectDtos.size(), 2);
    }


    /**
     * Junit
     * save project positive scenario ,Saving a new project in h2 db
     */
    @Test
    void saveProject_positive() throws ProjectNotFoundException, ProjectManagemnetException {
        ProjectDto projectDto = createTestProjectData(4);
       ProjectDto savedProjectDto= projectService.saveProject(projectDto);
        ProjectDto projectDto1 = projectService.getProjectById(savedProjectDto.getId());

        assertEquals(projectDto1.getName(), "test");
    }

    /**
     * Junit
     * update project positive scenario ,updating  a new project which is present in h2 db
     */
    @Test
    void updateProject_positive() throws Exception {
        ProjectDto existingProject = projectService.getProjectById(2);
        existingProject.setName("updated name");

        ProjectDto updatedProject = projectService.updateProject(existingProject);


        assertEquals(updatedProject.getName(), "updated name");
    }

    /**
     * Junit
     * update negative  scenario ,updating  a new project which is not present in h2 db
     */
    @Test
    void updateProject_projectNotFound() throws ProjectManagemnetException {

        ProjectDto existingProject = createTestProjectData(4);
        existingProject.setId(3);
        existingProject.setName("updated name");
        try {
            projectService.updateProject(existingProject);

        } catch (ProjectNotFoundException e) {
            assertEquals(e.getMessage(), ProjectManagementConstant.PROJECT_NOT_FOUND + "" + 3);

        }
    }


    /**
     * Junit
     * delete project positive scenario ,
     * Insert a new project
     * check the project get created by geProjectByid call
     * delete the project
     * now again check geProjectById
     * this will send Resource now found exception
     */
    @Test
    public void deleteProject_positive() throws ProjectNotFoundException, ProjectManagemnetException {
        int id = 5;
        ProjectDto projectDto = createTestProjectData(id);
        ProjectDto saveProjectDto = projectService.saveProject(projectDto);
        id = saveProjectDto.getId();
        ProjectDto exisProjectDto = projectService.getProjectById(id);

        assertEquals(exisProjectDto.getId(), id);
        String deleteResponse = projectService.deleteProject(id);
        assertEquals(deleteResponse, ProjectManagementConstant.PROJECT_DELETED_SUCCESS + " ID :" + id);
        try {

            projectService.getProjectById(id);
        } catch (ProjectNotFoundException exception) {
            assertEquals(exception.getMessage(), ProjectManagementConstant.PROJECT_NOT_FOUND + "" + id);
        }
    }

    /**
     * Junit
     * Project delete negative scenario, If project not found which we are trying to delete it will throw exception
     */
    @Test
    void deleteProject_negative() {
        int id = 8;
        try {
            projectService.deleteProject(id);
        } catch (ProjectNotFoundException exception) {
            assertEquals(exception.getMessage(), ProjectManagementConstant.PROJECT_NOT_FOUND + "" + id);
        }
    }

    /**
     * Method to create test data for ProjectDto
     *
     * @param id
     * @return
     */
    ProjectDto createTestProjectData(int id) {
        Date date = new Date(System.currentTimeMillis());
        ProjectDto projectDto = new ProjectDto("test", "test desc", date, date,"In-Progress","Susmita","John");
        return projectDto;
    }
}
