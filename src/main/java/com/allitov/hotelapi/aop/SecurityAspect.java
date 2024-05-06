package com.allitov.hotelapi.aop;

import com.allitov.hotelapi.exception.ExceptionMessage;
import com.allitov.hotelapi.exception.IllegalDataAccessException;
import com.allitov.hotelapi.security.UserDetailsImpl;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * Aspect for different security purposes.
 * @author allitov
 */
@Aspect
@Component
public class SecurityAspect {

    @Before(value = "execution(* com.allitov.hotelapi.web.controller.UserController.*ById(..)) " +
            "&& args(userId, userDetails, ..)", argNames = "userId,userDetails")
    public void userControllerByIdMethodsAdvice(Integer userId, UserDetailsImpl userDetails) {
        if (isDataAccessIllegal(userDetails, userId)) {
            throw new IllegalDataAccessException(
                    MessageFormat.format(
                            ExceptionMessage.USER_DATA_ILLEGAL_ACCESS, userDetails.getUser().getId(), userId)
            );
        }
    }

    private boolean isDataAccessIllegal(UserDetailsImpl userDetails, Integer requestedId) {
        return !userDetails.getUser().getId().equals(requestedId);
    }
}
