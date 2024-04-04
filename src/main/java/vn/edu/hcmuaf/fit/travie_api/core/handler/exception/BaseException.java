package vn.edu.hcmuaf.fit.travie_api.core.handler.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseException extends Exception {
    public BaseException(String message) {
        super(message);
    }
}
