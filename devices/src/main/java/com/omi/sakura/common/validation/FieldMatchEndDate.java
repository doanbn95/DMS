package com.omi.sakura.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE,ANNOTATION_TYPE})
@Constraint(validatedBy = FieldMatchValidatorEndDate.class )
@Retention(RUNTIME)
@Documented
public @interface FieldMatchEndDate {
    String message() default "This date is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String date1();

    String date2();

    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        FieldMatchEndDate[] value();
    }
}
