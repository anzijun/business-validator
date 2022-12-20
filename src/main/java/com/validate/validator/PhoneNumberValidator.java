package com.validate.validator;

import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;

/**
 * @author anzj
 * @date 2022/12/20 14:43
 */
public class PhoneNumberValidator extends ValidatorHandler<String> implements Validator<String>{
    @Override
    public boolean validate(ValidatorContext context, String s) {
        return super.validate(context, s);
    }
}
