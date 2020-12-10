package com.easy.restful.easyapi.config.web;

import cn.hutool.core.util.StrUtil;
import com.easy.restful.common.core.common.status.ResultCode;
import com.easy.restful.common.core.exception.EasyException;
import com.easy.restful.common.core.util.Response;
import com.easy.restful.sys.model.SysException;
import com.easy.restful.sys.model.SysUser;
import com.easy.restful.sys.service.SysExceptionService;
import com.easy.restful.util.ShiroUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 通用异常处理(应用级异常)
 *
 * @author tengchong
 * @date 2018/10/22
 */
@RestControllerAdvice
public class ExceptionControllerAdvice {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysExceptionService sysExceptionService;

    /**
     * 拦截业务异常
     */
    @ExceptionHandler(EasyException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response easyException(HttpServletRequest request, EasyException e) {
        logger.debug("业务异常", e);
        return Response.failError(e.getCode(), e.getMessage());
    }

    /**
     * 拦截未经认证异常（未登录）
     */
    @ExceptionHandler(UnauthenticatedException.class)
    public Response unauthenticatedException(UnauthenticatedException e) {
        return Response.failError(ResultCode.UNAUTHORIZED.getCode(), "你尚未登录，请登录后重试");
    }

    /**
     * 权限异常（已登录无权限）
     */
    @ExceptionHandler({UnauthorizedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Response unauthorizedException(HttpServletRequest request, UnauthorizedException e) {
        // 你无权限访问此资源
        return Response.failError(ResultCode.FORBIDDEN.getCode(), "无权限访问[" + request.getMethod() + "]" + request.getRequestURI());
    }

    /**
     * 登录异常
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response authenticationException(HttpServletRequest request, AuthenticationException e) {
        if (e.getCause() != null && e.getCause() instanceof EasyException) {
            logger.error("登录异常:" + e.getCause().getMessage());
            EasyException easyException = (EasyException) e.getCause();
            return Response.failError(easyException.getCode(), easyException.getMessage());
        } else {
            logger.error("登录异常:" + e.getMessage());
            return Response.failError(e.getMessage());
        }
    }

    /**
     * 未知异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object handleException(HttpServletRequest request, RuntimeException e) {
        logger.debug("未知异常", e);
        // 将异常记录到表中
        saveLog(HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI(), e);
        return Response.failError(e.getMessage());
    }

    /**
     * 保存异常信息
     *
     * @param code 错误代码
     * @param uri  请求地址
     * @param e    异常信息
     */
    private void saveLog(int code, String uri, RuntimeException e) {
        SysException sysException = new SysException();
        sysException.setCode(code);
        sysException.setMessage(e.getMessage());
        sysException.setUrl(uri);
        sysException.setTriggerTime(new Date());
        sysException.setType(e.getClass().getName());
        sysException.setTrace(StrUtil.join("\n\t", e.getStackTrace()));
        SysUser currentUser = ShiroUtil.getCurrentUser();
        if (currentUser != null) {
            sysException.setUserId(currentUser.getId());
        }
        sysExceptionService.saveData(sysException);
    }
}
