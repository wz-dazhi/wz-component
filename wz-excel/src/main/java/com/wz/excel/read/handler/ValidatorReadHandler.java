package com.wz.excel.read.handler;

import com.alibaba.excel.context.AnalysisContext;
import com.wz.excel.model.RowValidateResult;
import com.wz.excel.read.service.ValidatorReadService;
import lombok.Getter;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @projectName: wz-component
 * @package: com.wz.excel.read
 * @className: AbstractValidReadHandler
 * @description:
 * @author: zhi
 * @date: 2021/11/16
 * @version: 1.0
 */
public class ValidatorReadHandler<T> extends AbstractReadHandler<T> {
    private static final Class<?>[] EMPTY_GROUPS = new Class[0];
    private static final Validator DEFAULT_VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    private final Validator validator;
    private final Class<?>[] groups;
    private final ValidatorReadService<T> validatorReadService;

    /**
     * 行的验证结果
     */
    @Getter
    private Set<RowValidateResult> validateResults;

    public ValidatorReadHandler(ValidatorReadService<T> validatorReadService) {
        this(DEFAULT_BATCH_COUNT, validatorReadService);
    }

    public ValidatorReadHandler(int batchCount, ValidatorReadService<T> validatorReadService) {
        this(batchCount, EMPTY_GROUPS, validatorReadService);
    }

    public ValidatorReadHandler(int batchCount, Class<?>[] groups, ValidatorReadService<T> validatorReadService) {
        this(batchCount, groups, validatorReadService, DEFAULT_VALIDATOR);
    }

    public ValidatorReadHandler(int batchCount, Class<?>[] groups, ValidatorReadService<T> validatorReadService, Validator validator) {
        super(batchCount);
        this.validator = validator;
        this.groups = groups;
        this.validatorReadService = validatorReadService;
    }

    @Override
    public void parseInvoke(T t, AnalysisContext ac) {
        Set<ConstraintViolation<T>> violations = validator.validate(t, groups);
        if (!violations.isEmpty()) {
            if (validateResults == null) {
                validateResults = new LinkedHashSet<>();
            }

            List<RowValidateResult.ValidateField> fields = violations.stream()
                    .map(c -> new RowValidateResult.ValidateField(c.getPropertyPath().toString(), c.getMessage()))
                    .collect(Collectors.toList());
            RowValidateResult rowError = new RowValidateResult(ac.readSheetHolder().getSheetNo(), ac.readRowHolder().getRowIndex(), t.getClass().getSimpleName(), fields);
            validateResults.add(rowError);
        }
    }

    @Override
    public void processCachedData(List<T> cachedList) {
        if (validateResults != null && !validateResults.isEmpty()) {
            if (validatorReadService.skipInvalid()) {
                validatorReadService.process(cachedList);
            }
        } else {
            validatorReadService.process(cachedList);
        }
    }

}
