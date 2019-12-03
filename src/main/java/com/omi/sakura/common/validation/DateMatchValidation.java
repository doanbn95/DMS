package com.omi.sakura.common.validation;

import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class DateMatchValidation implements ConstraintValidator<DateMatch, Object> {
    private String firstDate;
    private String secondDate;
    private String thirdDate;
    private String forthDate;
    private String message;

    @Override
    public void initialize(final DateMatch constraintAnnotation) {
        firstDate = constraintAnnotation.date1();
        secondDate = constraintAnnotation.date2();
        thirdDate = constraintAnnotation.date3();
        forthDate = constraintAnnotation.date4();
        message = constraintAnnotation.message();
    }

    private LocalDate localDate = LocalDate.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private String currentDate = localDate.format(formatter);

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        boolean valid = true;
        boolean valid1 = true;
        boolean valid2 = true;
        boolean valid3 = true;
        boolean valid4 = true;
        try {
            final String firstObj = BeanUtils.getProperty(value, firstDate);
            final String secondObj = BeanUtils.getProperty(value, secondDate);
            final String thirdObj = BeanUtils.getProperty(value, thirdDate);
            final String fouthObj = BeanUtils.getProperty(value, forthDate);

            valid = StringUtils.isEmpty(firstObj) || firstObj.equals(secondObj);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date d1 = sdf.parse(firstDate);
            Date d2 = sdf.parse(secondDate);
            Date d3 = sdf.parse(thirdDate);
            Date d4 = sdf.parse(forthDate);
            Date dc = sdf.parse(currentDate);

            valid1 = d1.before(dc);
            valid2 = d2.after(d1);
            valid3 = d3.after(d2);
            valid4 = d4.after(d3);
            valid = valid1 && valid2 && valid3 && valid4;

        } catch (final Exception ignore) {
            // ignore
        }

        if (!valid1) {
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(firstDate)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        if (!valid2) {
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(secondDate)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        if (!valid3) {
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(thirdDate)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        if (!valid4) {
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(forthDate)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }
}
