package app;

import entry.ExceptionEntry;
import exception.DeviceTypeException;
import exception.DuplicateDeviceException;
import exception.InitializationException;
import exception.ParentNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler({DuplicateDeviceException.class,
            DeviceTypeException.class,
            InitializationException.class,
            ParentNotFoundException.class})
    public ResponseEntity<ExceptionEntry> handleDuplicateDeviceException(RuntimeException ex) {
        ExceptionEntry errors = new ExceptionEntry();
        errors.setMessage(ex.getMessage());
        errors.setStatus(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
