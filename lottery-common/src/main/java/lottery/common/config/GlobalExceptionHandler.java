package lottery.common.config;

import lottery.common.model.vo.BaseResultVO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BaseResultVO> handleViolationException(HttpServletRequest req, ConstraintViolationException e){
        List<String> errors = new ArrayList<>();
        e.getConstraintViolations().forEach(constraintViolation -> errors.add(constraintViolation.getMessage()));
        return ResponseEntity.status(400).body(BaseResultVO.create(0, errors.get(0)));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResultVO> handleMethodArgumentNotValid(HttpServletRequest req, MethodArgumentNotValidException e) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        return ResponseEntity.status(400).body(BaseResultVO.create(0, errors.get(0)));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<BaseResultVO> handleBindException(HttpServletRequest req, BindException e){
        System.out.println("BindException()");
        return ResponseEntity.status(400).body(BaseResultVO.create(0, "params valid fail"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResultVO> handleException(HttpServletRequest req, Exception e){
        System.out.println("handleException()");
        return ResponseEntity.status(500).body(BaseResultVO.create(0, "internal error, " + e.getMessage()));
    }

}
