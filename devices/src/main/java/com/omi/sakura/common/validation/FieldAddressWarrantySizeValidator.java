package com.omi.sakura.common.validation;

import com.omi.sakura.common.utils.StatusEnum;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldAddressWarrantySizeValidator implements ConstraintValidator<FieldAddressWarrantySize, Object> {
    private String firstFieldName;
    private String secondFieldName;
    private String message;

    @Override
    public void initialize(final FieldAddressWarrantySize constraintAnnotation) {
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
                    && (secondObj[0].length() > 30 || secondObj[0].length() < 5)
                    && firstObj[0].equals(String.valueOf(StatusEnum.BAO_HANH.getCode()))) {
                valid = false;
            }
        } catch (Exception ignore) {
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
