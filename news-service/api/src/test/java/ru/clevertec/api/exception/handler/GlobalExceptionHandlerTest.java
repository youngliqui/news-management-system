package ru.clevertec.api.exception.handler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import ru.clevertec.api.exception.response.ErrorResponse;
import ru.clevertec.api.feign_client.exception.AccessDeniedException;
import ru.clevertec.api.feign_client.exception.InvalidTokenException;
import ru.clevertec.api.feign_client.exception.NotFoundException;
import ru.clevertec.common.exception.CommentNotFoundException;
import ru.clevertec.common.exception.NewsNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {
    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    void testHandleCommentNotFoundException() {
        CommentNotFoundException exception = new CommentNotFoundException("Comment not found");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleCommentNotFoundException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Comment not found");
        assertThat(response.getBody().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getBody().getTimestamp()).isNotNull();
    }

    @Test
    void testHandleNewsNotFoundException() {
        NewsNotFoundException exception = new NewsNotFoundException("News not found");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleNewsNotFoundException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(Objects.requireNonNull(response.getBody()).getMessage()).isEqualTo("News not found");
    }

    @Test
    void testHandleNotFoundException() {
        NotFoundException exception = new NotFoundException("Resource not found");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleNotFoundException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(Objects.requireNonNull(response.getBody()).getMessage()).isEqualTo("Resource not found");
    }

    @Test
    void testHandleMissingRequestHeader() throws NoSuchMethodException {
        String headerName = "Authorization";
        String parameterType = "MissingRequestHeaderException";
        String expectedMessage = String.format("Required request header '%s' " +
                "for method parameter type %s is not present", headerName, parameterType);

        MethodParameter methodParameter = new MethodParameter(
                GlobalExceptionHandler.class.getMethod("handleMissingRequestHeader", MissingRequestHeaderException.class),
                0
        );

        MissingRequestHeaderException exception = new MissingRequestHeaderException(headerName, methodParameter);

        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleMissingRequestHeader(exception);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    void testHandleInvalidToken() {
        InvalidTokenException exception = new InvalidTokenException("Invalid token");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleInvalidToken(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(Objects.requireNonNull(response.getBody()).getMessage()).isEqualTo("Invalid token");
    }

    @Test
    void testHandleAccessDenied() {
        AccessDeniedException exception = new AccessDeniedException("Access denied");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleAccessDenied(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(Objects.requireNonNull(response.getBody()).getMessage()).isEqualTo("Access denied");
    }

    @Test
    void testHandleValidationExceptions() {
        List<ObjectError> errors = new ArrayList<>();
        errors.add(new FieldError("userAccount", "username", "Username is required"));

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getAllErrors()).thenReturn(errors);

        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        when(exception.getBindingResult()).thenReturn(bindingResult);

        ResponseEntity<Map<String, String>> responseEntity = globalExceptionHandler.handleValidationExceptions(exception);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        Map<String, String> body = responseEntity.getBody();
        assertThat(Objects.requireNonNull(body).size()).isEqualTo(1);
        assertThat(body.get("username")).isEqualTo("Username is required");
    }

    @Test
    void testHandleGenericException() {
        Exception exception = new Exception("Generic error");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGenericException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(Objects.requireNonNull(response.getBody()).getMessage()).isEqualTo("An unexpected error occurred");
    }
}