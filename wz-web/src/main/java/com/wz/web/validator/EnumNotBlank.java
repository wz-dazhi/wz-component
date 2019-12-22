package com.wz.web.validator;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.lang.reflect.Method;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @projectName: wz-component
 * @package: com.wz.web.validator
 * @className: EnumNotBlank
 * @description:
 * @author: Zhi
 * @date: 2019-12-21 20:00
 * @version: 1.0
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = EnumNotBlank.EnumValidator.class)
public @interface EnumNotBlank {

    String message() default "{com.wz.web.validator.EnumNotBlank.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * the enum's class-type
     *
     * @return Class
     */
    Class<?> clazz();

    /**
     * the method's name ,which used to validate the enum's value
     *
     * @return method's name
     */
    String method() default "name";

    /**
     * Defines several {@link EnumNotBlank} annotations on the same element.
     *
     * @see EnumNotBlank
     */
    @Documented
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @interface List {
        EnumNotBlank[] value();
    }

    class EnumValidator implements ConstraintValidator<EnumNotBlank, Object> {
        private EnumNotBlank annotation;

        @Override
        public void initialize(EnumNotBlank constraintAnnotation) {
            this.annotation = constraintAnnotation;
        }

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext context) {
            if (value == null) {
                return false;
            }

            Object[] objects = annotation.clazz().getEnumConstants();
            try {
                Method m = annotation.clazz().getMethod(annotation.method());
                for (Object o : objects) {
                    if ("name".equals(annotation.method())) {
                        if (value.toString().equals(m.invoke(o))) {
                            return true;
                        }
                    } else {
                        if (value.equals(m.invoke(o))) {
                            return true;
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return false;
        }
    }
}
