package com.sirma.pms.exceptionhandling;

import com.sirma.pms.exception.ExceptionResponse;
import com.sirma.pms.exception.ProjectManagemnetException;
import com.sirma.pms.exception.ProjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * This is Controller Advice class which is responsible to handle all the exception for this project in a centralized place
 */
@ControllerAdvice
public class PMExceptionHandlerControllerAdvice {

    /**
     * This method will handle the ProjectNotFoundException/status 404 ,
     * whenever this exception has been thrown by any controller method it will come here
     * to combine the exception and return a meaning full error message to the caller along with the calling method name
     *{
     *     "errorMessage": "Project not found",
     *     "requestedURI": "/project/2"
     * }
     * @param projectNotFoundException
     * @param httpServletRequest
     *
     * @return response body (ExceptionResponse )
     */

    @ExceptionHandler(ProjectNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody ExceptionResponse handleProjectNotFound(final ProjectNotFoundException projectNotFoundException,
                                                                  final HttpServletRequest httpServletRequest) {

        ExceptionResponse error = new ExceptionResponse();
        error.setErrorMessage(projectNotFoundException.getMessage());
        error.callerURL(httpServletRequest.getRequestURI());

        return error;
    }

    /**
     * This will handle the input dto field validation error usecases and combine those in a single map and return the same
     *{
     *     "errorMessage": "{name=Name is mandatory, description=Description is mandatory}",
     *     "requestedURI": "/project"
     * }
     * @param methodArgumentNotValidException
     * @param httpServletRequest
     * @return response body (ExceptionResponse )
     */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ExceptionResponse handleValidationExceptions1(final MethodArgumentNotValidException methodArgumentNotValidException,
                                                                  final HttpServletRequest httpServletRequest) {
        Map<String, String> errors = new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ExceptionResponse error = new ExceptionResponse();
        error.setErrorMessage(errors.toString());
        error.callerURL(httpServletRequest.getRequestURI());

        return error;
    }

    /**
     * This method will handle  any Intern exception and /status 500 ,
     * and will throw projectManagemnetException
     * @param projectManagemnetException
     * @param httpServletRequest
     *
     * @return response body (ExceptionResponse )
     */

    @ExceptionHandler(ProjectManagemnetException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ExceptionResponse handleInternalServerFound(final ProjectManagemnetException projectManagemnetException,
                                                                 final HttpServletRequest httpServletRequest) {

        ExceptionResponse error = new ExceptionResponse();
        error.setErrorMessage(projectManagemnetException.getMessage());
        error.callerURL(httpServletRequest.getRequestURI());

        return error;
    }

}
