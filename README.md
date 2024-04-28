# project_management
This is a spring boot project which contains codebase for CRUD APIs needed for a project management system  
This is a Project for "Project Managemnet Application"  
Please find below Setup and Run process.  
Setup and run :-  
1.Please create a fork of this repo.  
2.Git clone to your local system and open in IDE/ or setup via git link directly to your IDE  
3.DB used H2 in memory with DB name:projectManagement, username : susmita , no passwrod. (http://localhost:8080/h2-console/)  
4.Run the ProjectManagementApplication ( Spring boot application main class)  
5. By default server will start at localhost 8080  
6.You can now start using the apis ( details in Swagger ) in your postman  
7.Here Project is the resouce, so all the apis are created keeping this is mind with just /project endpoit   
8.With keeping standard rset api in mind have developed the api endpoit accordingly   
7.to save/create use /project with POST Method  
8.to Update use /project with PUT method  
9.to get by ID use /project/{id} with GET method  
10.to get all use /project/all with GET method   
11.to delete use /project/{id} with DELETE method  
  
note : we shoud use the resource as it is and use Rest method to define them , should not create endpoint like createProject or updateProject .   
as POST is dedicated to create project , PUT is for update , like wise GET and DELETE  
  
swagger url:http://localhost:8080/swagger-ui/index.html#/   
has all the details for each api and response code and all the details  
  
Project table has below coloumns  
ID,NAME,DESCRIPTION,STARTDATE,ENDDATE,STATUS,CREATEDBY,UPDATEDBY  
  
ID is the primary key ,will be automatcailly genarted by sequence  
  
   
 ----Technology used:---  
 Java 17 with Spring boot  
 Lombok for data generation(constructor and getter ,setter)  
 Mapstruct:(ProjectDtoMapper.java) For converting Dto to Entity pojo and vice versa . In this project using ProjectDto for all api calls , and Project entity for Database table  
 ControllerAdvice: (PMExceptionHandlerControllerAdvice.java) -- To handle exception in a cerntarlized place and create custom exception  
					(ProjectNotFoundException,MethodArgumentNotValidException,ProjectManagemnetException)  
Open-API : for Swagger api documentation (http://localhost:8080/swagger-ui/index.html#/ )  
Database: H2 inmemory DB with SPring data JPA  
Junit for service layer method testing .(ProjectServiceTest.java)  
Integration test for API testing (ProjectControllerIntegrationTest.java)  
 Build tool :Gradle  
   
   
   
-------API details:----  

1.Create project :-  
Endpoint :http://localhost:8080/project  
Method: POST  
Sample request:-  
{  
     "name": "Project-2",  
     "description": "Test project2 ",  
     "startDate": "2024-04-27",  
     "endDate": "2024-04-27",  
     "status":"In-Progress",  
     "createdBy":"Susmita",  
     "updatedBy":"John"  
 }  
   
 Response:  
 {  
         "id": 3,  
         "name": "Project-2",  
         "description": "Test project2 ",  
         "startDate": "2024-04-27",  
         "endDate": "2024-04-27",  
         "status": "In-Progress",  
         "createdBy": "Susmita",  
         "updatedBy": "John"  
 }  
 
2.Update projcet :
  
  
Endpoint :http://localhost:8080/project  
Method: PUT  
Sample request:-  
{  
     "id":"2",  
     "name": "Project-2",  
     "description": "Test project2-updated ",  
     "startDate": "2024-04-27",  
     "endDate": "2024-04-27",  
     "status":"Started",  
     "createdBy":"Susmita",  
     "updatedBy":"Susmita"  
 }  
   
 Response:  
 {  
     "id": 2,  
     "name": "Project-2",  
     "description": "Test project2-updated ",  
     "startDate": "2024-04-27",  
     "endDate": "2024-04-27",  
     "status": "Started",  
     "createdBy": "Susmita",  
     "updatedBy": "Susmita"  
 }  
   
 3.Get Project by id :  
 Endpoint: http://localhost:8080/project/2  
 Method: GET  
 Request:http://localhost:8080/project/2  
 {  
     "id": 2,  
     "name": "Project-1",  
     "description": "Test project ",  
     "startDate": "2024-04-27",  
     "endDate": "2024-04-27",  
     "status": "In-Progress",  
     "createdBy": "Susmita",  
     "updatedBy": "John"  
 }  
   
   
 4.Get all projects:  
 Endpoint: http://localhost:8080/project/all  
 Method: GET  
 Request:http://localhost:8080/project/all  
   
 Response:  
   
 [  
       {  
           "id": 2,  
           "name": "Project-1",  
           "description": "Test project ",  
           "startDate": "2024-04-27",  
           "endDate": "2024-04-27",  
           "status": "In-Progress",  
           "createdBy": "Susmita",  
           "updatedBy": "John"  
       },  
       {  
           "id": 3,  
           "name": "Project-2",  
           "description": "Test project2 ",  
           "startDate": "2024-04-27",  
           "endDate": "2024-04-27",  
           "status": "In-Progress",  
           "createdBy": "Susmita",  
           "updatedBy": "John"  
       }  
  ]  
   
   
 5.Delete a project by id:  
 endpoint://localhost:8080/project/1  
 Method:DELETE  
 Request://localhost:8080/project/1  
 Response:  
 Project has been deleted successfully  ID :1  
  
Swagger :  
![image](https://github.com/susmi170/project_management/assets/149704969/39990b2f-b9df-47d2-8462-d575d27a6ff7)  

 
