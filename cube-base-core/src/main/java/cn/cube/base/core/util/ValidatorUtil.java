package cn.cube.base.core.util;


import cn.cube.base.core.exception.BaseBusinessErrorCode;
import cn.cube.base.core.exception.BusinessException;
import com.google.common.collect.Lists;
import org.hibernate.validator.HibernateValidatorFactory;

import javax.validation.*;
import java.util.Set;

/**
 * Description:验证util
 * Author:zhanglida
 * Date:2017/7/20
 * Email:406504302@qq.com
 */
public class ValidatorUtil {

    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    public static <T> void validate(T t) {
        if (null == t) {
            throw new BusinessException(BaseBusinessErrorCode.REQUEST_PARAMS_NULL);
        }
        Validator validator = factory.unwrap(HibernateValidatorFactory.class).usingContext()
                .failFast(true)
                .getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);
        if (constraintViolations.isEmpty()) {
            return;
        }
        ConstraintViolation<T> violation = Lists.newArrayList(constraintViolations).get(0);

        String error = violation.getMessage();
        Path.Node node = violation.getPropertyPath().iterator().next();
        String field = node.getName();
        if (StringUtils.isEmpty(error)) {
            error = BaseBusinessErrorCode.REQUEST_PARAMS_INVALID.getMessage();
        } else {
            error = field + BaseBusinessErrorCode.REQUEST_PARAMS_INVALID.getMessage();
        }
        throw new BusinessException(BaseBusinessErrorCode.REQUEST_PARAMS_INVALID, error);
    }

}
