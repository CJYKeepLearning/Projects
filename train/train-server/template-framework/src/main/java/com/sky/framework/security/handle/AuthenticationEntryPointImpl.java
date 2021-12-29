package com.sky.framework.security.handle;

import com.sky.common.core.result.CommonError;
import com.sky.common.core.result.Result;
import com.sky.common.core.result.error.AuthError;
import com.sky.common.util.ServletUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * 认证失败处理类 返回未授权
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        ServletUtil.responseResult(response, Result.getResult(AuthError.LOGIN_STATUS_IS_INVALID));
    }
}
