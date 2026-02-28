package com.itheima.backend.aop;

import com.itheima.backend.annotation.AuthCheck;
import com.itheima.backend.exception.BusinessException;
import com.itheima.backend.exception.ErrorCode;
import com.itheima.backend.model.entity.User;
import com.itheima.backend.model.enums.UserRoleEnum;
import com.itheima.backend.service.UserService;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class AuthInterceptor {
    @Resource
    private UserService userService;

    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        String mustRole = authCheck.mustRole();
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        //获取当前用户
        User loginUser = userService.getLoginUser(request);
        UserRoleEnum mustUserRoleEnum = UserRoleEnum.getEnumByValue(mustRole);
        //如果不需要权限放行
        if (mustUserRoleEnum == null) {
            return joinPoint.proceed();
        }
        //必须有权限才会通过
        UserRoleEnum loginUserRoleEnum = UserRoleEnum.getEnumByValue(loginUser.getUserRole());
        if (loginUserRoleEnum == null ) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无权限");
    }
        //要求必须有管理员权限,但用户没有管理员权限，拒绝访问
        if (loginUserRoleEnum.ADMIN.equals(mustUserRoleEnum)&& !loginUserRoleEnum.ADMIN.equals(loginUserRoleEnum)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无管理员权限");
        }
        return  joinPoint.proceed();

}
}