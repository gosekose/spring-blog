package blog.exception.exception.advice;

import blog.exception.exception.advice.ErrorDto;
import blog.exception.exception.exception.BindingInvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class ExceptionControllerAdvice {

    /**
     * 바인딩 에러
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResponseEntity<ErrorDto> bindingInvalidHandler(BindingInvalidException e) {
        return new ResponseEntity<>(new ErrorDto(e.getErrorCode(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
