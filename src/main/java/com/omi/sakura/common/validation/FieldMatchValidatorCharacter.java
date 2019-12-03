package com.omi.sakura.common.validation;

import com.omi.sakura.common.utils.StatusEnum;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldMatchValidatorCharacter implements ConstraintValidator<FeildMatchCharacterSpec, Object> {
    private String firstFieldName;
    private String secondFieldName;
    private String message;

    @Override
    public void initialize(final FeildMatchCharacterSpec constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object value, ConstraintValidatorContext context) {
        boolean valid = true;
        try {
            String[] firstObj = BeanUtils.getArrayProperty(value, firstFieldName);
            String[] secondObj = BeanUtils.getArrayProperty(value, secondFieldName);
            if (!StringUtils.isEmpty(firstObj[0])
                    && !StringUtils.isEmpty(secondObj[0])
                    && firstObj[0].equals(String.valueOf(StatusEnum.THANH_LY.getCode()))
                    && !secondObj[0].matches("^[0-9]+(.[0-9]+)*$")) {
                valid = false;
            }
        } catch (final Exception ignore) {
            //ignore
        }
        if (!valid) {
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(secondFieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return valid;
    }
}
