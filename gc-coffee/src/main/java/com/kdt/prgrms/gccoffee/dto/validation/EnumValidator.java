package com.kdt.prgrms.gccoffee.dto.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.stream.Stream;

public class EnumValidator implements ConstraintValidator<ValueOfEnum, String> {

    private ValueOfEnum annotation;

    @Override
    public void initialize(ValueOfEnum constraintAnnotation) {

        annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .toList()
                .contains(value);
    }
}
