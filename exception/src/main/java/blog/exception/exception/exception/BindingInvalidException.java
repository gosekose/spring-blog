package blog.exception.exception.exception;

import blog.exception.exception.type.ExceptionCode;
import blog.exception.exception.type.ExceptionMessage;

public class BindingInvalidException extends CommonException {
    public BindingInvalidException() {
        super(ExceptionCode.BAD_REQUEST, ExceptionMessage.BINDING_INVALID);
    }
}