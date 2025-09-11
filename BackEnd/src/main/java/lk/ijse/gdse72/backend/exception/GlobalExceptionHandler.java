package lk.ijse.gdse72.backend.exception;

import io.jsonwebtoken.ExpiredJwtException;
import lk.ijse.gdse72.backend.dto.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.management.relation.RelationNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public APIResponse handleUserNameNotFoundException(UsernameNotFoundException ex){
        return new APIResponse(
                404,
                "User not Found",
                ex.getMessage()
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public APIResponse handleBadCredentialException(BadCredentialsException ex){
        return new APIResponse(
                401,
                "Unauthorized",
                "Invalid username or password"
        );
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public APIResponse handleExpiredJwtException(ExpiredJwtException ex){
        return new APIResponse(
                401,
                "Unauthorized",
                "Expired JWT Token"
        );
    }

    @ExceptionHandler(RelationNotFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public APIResponse handleRelationNotFoundException(RelationNotFoundException ex){
        return new APIResponse(
                401,
                "Resourse Not Found",
                ex.getMessage()
        );
    }

//    @ExceptionHandler(RuntimeException.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public APIResponse handleRuntimeException(RuntimeException ex){
//        return new APIResponse(
//                500,
//                "Internal Server Error",
//                ex.getMessage()
//        );
//    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT) // 409
    public APIResponse handleUserAlreadyExists(UserAlreadyExistsException ex) {
        return new APIResponse(
                409,
                "Conflict",
                ex.getMessage()
        );
    }

}