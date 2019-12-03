package com.omi.sakura.common.validation;

import com.omi.sakura.common.utils.DateUtils;
import com.omi.sakura.common.utils.StatusEnum;
import com.omi.sakura.common.utils.TypeDeviceEnum;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.StringUtils;

import javax.swing.text.DateFormatter;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.Date;

public class ReturnTimeEqualsThanHandTimeValidator implements ConstraintValidator<FieldMatchReturnDateEqualsThanHandTime, Object> {
    private String firstFieldName;
    private String secondFieldName;
    private String threeFieldName;
    private String fourFieldName;
    private String message;

    @Override
    public void initialize(FieldMatchReturnDateEqualsThanHandTime constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
        threeFieldName = constraintAnnotation.three();
        fourFieldName = constraintAnnotation.four();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean valid = true;
        try {
            final String firstObj = BeanUtils.getProperty(value, firstFieldName);
            final String secondObj = BeanUtils.getProperty(value, secondFieldName);
            final String threeObj = BeanUtils.getProperty(value, threeFieldName);
            final String fourObj = BeanUtils.getProperty(value, fourFieldName);
            if (!StringUtils.isEmpty(firstObj)
                    && !StringUtils.isEmpty(secondObj)
                    && !StringUtils.isEmpty(threeObj)
                    && !StringUtils.isEmpty(fourObj)
                    && firstObj.equals(String.valueOf(StatusEnum.DANG_SU_DUNG.getCode()))
                    && secondObj.equals(String.valueOf(TypeDeviceEnum.TEST.getCode()))) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date1 = sdf.parse(threeObj);
                Date date2 = sdf.parse(fourObj);
                valid = date1.before(date2)||threeObj.equals(fourObj);
            }
        } catch (Exception ignore) {
            //ignore
        }
        if (!valid) {
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(fourFieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return valid;
    }
}
