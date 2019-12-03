package com.omi.sakura.common.validation;

import com.omi.sakura.common.utils.DateUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FieldMatchValidatorEndDate implements ConstraintValidator<FieldMatchEndDate, Object> {
    private String firstFieldName;
    private String secondFieldName;
    private String message;

    @Override
    public void initialize(final FieldMatchEndDate constraintAnnotation) {
        firstFieldName = constraintAnnotation.date1();
        secondFieldName = constraintAnnotation.date2();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object value, ConstraintValidatorContext context) {
        boolean valid = true;
        try {
            String[] firstObj = BeanUtils.getArrayProperty(value, firstFieldName);
            String[] secondObj = BeanUtils.getArrayProperty(value, secondFieldName);
            valid = StringUtils.isEmpty(firstObj[0]) || StringUtils.isEmpty(secondObj[0]);
            if(!valid) {
                Date d1 = DateUtils.convertToDate(firstObj[0]);
                Date d2 = DateUtils.convertToDate(secondObj[0]);
                valid=valid||d1.before(d2)||firstObj[0].equals(secondObj[0]);
            }


        } catch (final Exception ignore) {
            //ignore
        }
        if(!valid){
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(secondFieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }
}
