package vn.edu.hcmuaf.fit.travie_api.core.infrastructure.annotation;

import jakarta.validation.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;

import java.lang.annotation.*;

@ConstraintComposition(CompositionType.OR)
@Email
@Pattern(regexp = "^(\\+84|0)\\d{9,10}$")
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@ReportAsSingleViolation
@Documented
public @interface EmailOrPhone {
    String message() default "Email hoặc số điện thoại không hợp lệ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
