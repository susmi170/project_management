package com.sirma.pms.integrationtest.controller;


import com.sirma.pms.ProjectManagementApplication;
import com.sirma.pms.dto.ProjectDto;
import org.json.JSONException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ProjectManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class ProjectControllerIntegrationTest {
    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    /**
     * Get project by Id api test
     * @throws JSONException
     */

    @Test
    @Order(1)
    public void testGetProjectById() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/project/2"), HttpMethod.GET, entity, String.class);
        String expected = "{\"id\":2,\"name\":\"p1\",\"description\":\"p desc\",\"startDate\":\"2024-04-28\",\"endDate\":\"2024-04-28\"}";
        System.out.println("Get Project by id response body ::" + response.getBody());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    /**
     * Get all projects api test
     * @throws JSONException
     */
    @Test
    @Order(2)
    public void testGetAllProjects() throws JSONException {
        ProjectDto projectDto = createProjectData(5);
        Date date = new Date(System.currentTimeMillis());
        HttpEntity<ProjectDto> postEntity = new HttpEntity<>(projectDto, headers);

        restTemplate.exchange(
                createURLWithPort("/project"), HttpMethod.POST, postEntity, String.class);

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/project/all"), HttpMethod.GET, entity, String.class);
        String expected = "[{\"id\":1,\"name\":\"test\",\"description\":\"test desc\",\"startDate\":\"2024-04-28\",\"endDate\":\"2024-04-28\"},{\"id\":2,\"name\":\"p1\",\"description\":\"p desc\",\"startDate\":\"2024-04-28\",\"endDate\":\"2024-04-28\"}]";
        System.out.println("Get Project by id response body ::" + response.getBody());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    /**
     * Create project api test
     * @throws JSONException
     */
    @Test
    @Order(3)
    public void testCreateProject() throws JSONException {
        HttpEntity<String> deleteEntity = new HttpEntity<String>(null, headers);

        restTemplate.exchange(
                createURLWithPort("/project/" + 2), HttpMethod.DELETE, deleteEntity, String.class);
        ProjectDto projectDto = createProjectData(1);
        projectDto.setDescription("First description");
        Date date = new Date(System.currentTimeMillis());
        HttpEntity<ProjectDto> entity = new HttpEntity<>(projectDto, headers);

        ResponseEntity<ProjectDto> response = restTemplate.exchange(
                createURLWithPort("/project"), HttpMethod.POST, entity, ProjectDto.class);
        ProjectDto createdProjectDto = response.getBody();

        System.out.println("create response body ::" + response.getBody());
        assertEquals(createdProjectDto.getDescription(), "First description");
    }

    /**
     * Update project api test
     * @throws JSONException
     */
    @Test
    @Order(4)
    public void testUpdateProject() throws JSONException {
        ProjectDto projectDto = createProjectData(3);
       // projectDto.setId(3);
        Date date = new Date(System.currentTimeMillis());
        HttpEntity<ProjectDto> entity = new HttpEntity<>(projectDto, headers);
        //create with di 3
        ResponseEntity<ProjectDto> response = restTemplate.exchange(
                createURLWithPort("/project"), HttpMethod.POST, entity, ProjectDto.class);
        ProjectDto responseProjectDto = response.getBody();
        projectDto.setId(responseProjectDto.getId());
        //change the description
        projectDto.setDescription("updated");
        //update call
        ResponseEntity<ProjectDto> updatedResponse = restTemplate.exchange(
                createURLWithPort("/project"), HttpMethod.PUT, entity, ProjectDto.class);
        ProjectDto updatedProjectDto = updatedResponse.getBody();
        System.out.println("Updated response body ::" + updatedResponse.getBody());
        assertEquals(updatedProjectDto.getDescription(),"updated");
    }

    /**
     * Delete project api test
     * @throws JSONException
     */
    @Test
    @Order(5)
    public void testDeleteProjectById() throws JSONException {
        int id = 4;
        //create project with id 4
        ProjectDto projectDto = createProjectData(id);
        Date date = new Date(System.currentTimeMillis());
        HttpEntity<ProjectDto> entity = new HttpEntity<>(projectDto, headers);

        restTemplate.exchange(
                createURLWithPort("/project"), HttpMethod.POST, entity, String.class);

        //get before delete with id 4
        String beforeDeleteResponse = getProjectById(String.valueOf(id));
        String expected = "{\"id\":" + id + ",\"name\":\"test\",\"description\":\"test desc\",\"startDate\":" + date.toString() + ",\"endDate\":" + date.toString() + "}";
        System.out.println("response body ::" + beforeDeleteResponse);
        JSONAssert.assertEquals(expected, beforeDeleteResponse, false);
        //delete call
        HttpEntity<String> getEntity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> deleteResponse = restTemplate.exchange(
                createURLWithPort("/project/" + id), HttpMethod.DELETE, getEntity, String.class);
        System.out.println("respnse body ::" + deleteResponse.getBody());
        //get after delete with id 4

        String afterDeleteResponse = getProjectById(String.valueOf(id));
        System.out.println("afterDeleteResponse:::" + afterDeleteResponse);
        String afterDeleteResponseExpected = "{\"errorMessage\":\"Project not found with given Id:" + id + "\",\"requestedURI\":\"/project/" + id + "\"}";
        JSONAssert.assertEquals(afterDeleteResponseExpected, afterDeleteResponse, false);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    ProjectDto createProjectData(int id) {
        Date date = new Date(System.currentTimeMillis());
        ProjectDto projectDto = new ProjectDto("test", "test desc", date, date,"In-Progress","Susmita","John");
        return projectDto;
    }

    String getProjectById(String id) {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        //before delete get response
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/project/" + id), HttpMethod.GET, entity, String.class);

        return response.getBody();
    }
}
