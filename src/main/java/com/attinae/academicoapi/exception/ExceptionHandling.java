package com.attinae.academicoapi.exception;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import javax.persistence.NoResultException;

import com.attinae.academicoapi.domain.HttpResponse;
import com.attinae.academicoapi.exception.domain.*;
import com.auth0.jwt.exceptions.TokenExpiredException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ExceptionHandling implements ErrorController {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private static final String ACCOUNT_LOCKED = "Su cuenta ha sido bloqueada. Comuníquese con la administración";
    private static final String METHOD_IS_NOT_ALLOWED = "Este método de solicitud no está permitido en este endPoint. Envíe una solicitud '%s' ";
    private static final String INTERNAL_SERVER_ERROR_MSG = "Se produjo un error al procesar la solicitud";
    private static final String INCORRECT_CREDENTIALS =   "Usuario / contraseña incorrectos. Inténtalo de nuevo";
    private static final String ACCOUNT_DISABLED = "Su cuenta ha sido inhabilitada. Si se trata de un error, comuníquese con la administración.";
    private static final String ERROR_PROCESSING_FILE = "Se produjo un error al procesar el archivo";
    private static final String NOT_ENOUGH_PERMISSION = "No tienes suficiente permiso";
    public static final String ERROR_PATH = "/error";

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<HttpResponse> accountDisabledException() {
        return createHttpResponse(BAD_REQUEST, ACCOUNT_DISABLED );

    }

     @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpResponse> badCredentialsException() {
        return createHttpResponse(BAD_REQUEST, INCORRECT_CREDENTIALS);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<HttpResponse> accessDeniedException() {
        return createHttpResponse(FORBIDDEN, NOT_ENOUGH_PERMISSION);
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<HttpResponse> lockedException() {
        return createHttpResponse(UNAUTHORIZED, ACCOUNT_LOCKED);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<HttpResponse> tokenExpiredException(TokenExpiredException exception) {
        return createHttpResponse(UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler(EmailExistException.class)
    public ResponseEntity<HttpResponse> emailExistException(EmailExistException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<HttpResponse> usernameExistException(UsernameExistsException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }


    @ExceptionHandler(PersonaExistsException.class)
    public ResponseEntity<HttpResponse> usernameExistException(PersonaExistsException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<HttpResponse> emailNotFoundException(EmailNotFoundException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<HttpResponse> userNotFoundException(UsuarioNotFoundException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }


    @ExceptionHandler(PersonaNotFoundException.class)
    public ResponseEntity<HttpResponse> userNotFoundException(PersonaNotFoundException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }



    @ExceptionHandler(ListaVaciaException.class)
    public ResponseEntity<HttpResponse> emptyListException(ListaVaciaException exception) {
        return createHttpResponse(NO_CONTENT, exception.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        HttpMethod supportedMethod = Objects.requireNonNull(exception.getSupportedHttpMethods()).iterator().next();
        return createHttpResponse(METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOWED, supportedMethod));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HttpResponse> methodArgumentNotValidException(MethodArgumentNotValidException exception) {

            var campos = new ArrayList<HttpResponse.Campo>();
            for(ObjectError error: exception.getBindingResult().getAllErrors()) {
                String nombre = ((FieldError) error).getField();
                String mensaje = error.getDefaultMessage();
                campos.add(new HttpResponse.Campo(nombre, mensaje));
            }

            var httpResponse = new HttpResponse(BAD_REQUEST.value(),BAD_REQUEST,BAD_REQUEST.getReasonPhrase(),
                    "Uno o mas campos son invalidos. Favor de realizar el procedimiento correcto e intente nuevamente"
                            ,new Date());

            httpResponse.setCampos(campos);

            return new ResponseEntity<>(httpResponse,BAD_REQUEST);

    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse> internalServerErrorException(Exception exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG);
    }




    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<HttpResponse> notEmptyResultDataException(EmptyResultDataAccessException exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<HttpResponse> notFoundException(NoResultException exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<HttpResponse> iOException(IOException exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(INTERNAL_SERVER_ERROR, ERROR_PROCESSING_FILE);
    }


    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        HttpResponse httpResponse = new HttpResponse(httpStatus.value(),httpStatus ,httpStatus.getReasonPhrase().toUpperCase()
                ,message.toUpperCase(), new Date());
        return new ResponseEntity<>(httpResponse,httpStatus );
    }


    @RequestMapping(ERROR_PATH)
    public ResponseEntity<HttpResponse> notFound404() {
        return createHttpResponse(NOT_FOUND, "No hay ruta para esta URL");
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
