package com.easy.restful.config.shiro;

import cn.hutool.core.util.StrUtil;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * web 会话管理
 *
 * @author tengchong
 * @date 2018/9/13
 */
public class SessionManager extends DefaultWebSessionManager {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 定义的请求头中使用的标记key，用来传递 token
     */
    private static final String AUTH_TOKEN = "Access-Token";

    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    /**
     * 获取sessionId，原本是根据sessionKey来获取一个sessionId
     * 重写的部分多了一个把获取到的token设置到request的部分。这是因为app调用登陆接口的时候sh，是没有token的，登陆成功后，产生了token,我们把它放到request中，返回结
     * 果给客户端的时候，把它从request中取出来，并且传递给客户端，客户端每次带着这个token过来，就相当于是浏览器的cookie的作用，也就能维护会话了
     * @param request
     * @param response
     * @return
     */
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        //获取请求头中的 AUTH_TOKEN 的值，如果请求头中有 AUTH_TOKEN 则其值为sessionId。shiro就是通过sessionId 来控制的
        String sessionId = WebUtils.toHttp(request).getHeader(AUTH_TOKEN);
        if (StrUtil.isEmpty(sessionId)){
            // 如果没有携带token，认为没有登录
            return null;
        } else {
            //请求头中如果有 authToken, 则其值为sessionId
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            //sessionId
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sessionId);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return sessionId;
        }
    }

    @Override
    protected Session retrieveSession(SessionKey sessionKey) {
        // 获取sessionId
        Serializable sessionId = getSessionId(sessionKey);

        ServletRequest request = null;

        // 获取 ServletRequest
        if (sessionKey instanceof WebSessionKey) {
            request = ((WebSessionKey) sessionKey).getServletRequest();
        }
        // 性能优化每次从redis读取 Session 都放到 ServletRequest,处理单次请求读取多次问题
        // 如果 ServletRequest 中有session信息直接从 ServletRequest 中获取
        if (request != null && sessionId != null) {
            Object sessionObj = request.getAttribute(sessionId.toString());
            if (sessionObj != null) {
                logger.debug("从 ServletRequest 获取到 Session:{}", sessionId);
                return (Session) sessionObj;
            }
        }
        // 如果 ServletRequest 中没有session信息 从 redis 中读取
        Session session;
        try{
            session = super.retrieveSession(sessionKey);
        }catch (UnknownSessionException e) {
            // 获取不到SESSION不抛出异常
            return null;
        }
        if (request != null && sessionId != null) {
            logger.debug("存储 Session 到 ServletRequest:{}", sessionId);
            request.setAttribute(sessionId.toString(), session);
        }
        return session;
    }

    @Override
    public Date getStartTimestamp(SessionKey key) {
        try {
            return super.getStartTimestamp(key);
        } catch (InvalidSessionException e) {
            // 获取不到SESSION不抛出异常
            return null;
        }
    }

    @Override
    public Date getLastAccessTime(SessionKey key) {
        try {
            return super.getLastAccessTime(key);
        } catch (InvalidSessionException e) {
            // 获取不到SESSION不抛出异常
            return null;
        }
    }

    @Override
    public long getTimeout(SessionKey key) {
        try {
            return super.getTimeout(key);
        } catch (InvalidSessionException e) {
            // 获取不到SESSION不抛出异常
            return 0;
        }
    }

    @Override
    public void setTimeout(SessionKey key, long maxIdleTimeInMillis) {
        try {
            super.setTimeout(key, maxIdleTimeInMillis);
        } catch (InvalidSessionException e) {
            // 获取不到SESSION不抛出异常
        }
    }

    @Override
    public void touch(SessionKey key) {
        try {
            super.touch(key);
        } catch (InvalidSessionException e) {
            // 获取不到SESSION不抛出异常
        }
    }

    @Override
    public String getHost(SessionKey key) {
        try {
            return super.getHost(key);
        } catch (InvalidSessionException e) {
            // 获取不到SESSION不抛出异常
            return null;
        }
    }

    @Override
    public Collection<Object> getAttributeKeys(SessionKey key) {
        try {
            return super.getAttributeKeys(key);
        } catch (InvalidSessionException e) {
            // 获取不到SESSION不抛出异常
            return null;
        }
    }

    @Override
    public Object getAttribute(SessionKey sessionKey, Object attributeKey) {
        try {
            return super.getAttribute(sessionKey, attributeKey);
        } catch (InvalidSessionException e) {
            // 获取不到SESSION不抛出异常
            return null;
        }
    }

    @Override
    public void setAttribute(SessionKey sessionKey, Object attributeKey, Object value) {
        try {
            super.setAttribute(sessionKey, attributeKey, value);
        } catch (InvalidSessionException e) {
            // 获取不到SESSION不抛出异常
        }
    }

    @Override
    public Object removeAttribute(SessionKey sessionKey, Object attributeKey) {
        try {
            return super.removeAttribute(sessionKey, attributeKey);
        } catch (InvalidSessionException e) {
            // 获取不到SESSION不抛出异常
            return null;
        }
    }

    @Override
    public void stop(SessionKey key) {
        try {
            super.stop(key);
        } catch (InvalidSessionException e) {
            // 获取不到SESSION不抛出异常
        }
    }

    @Override
    public void checkValid(SessionKey key) {
        try {
            super.checkValid(key);
        } catch (InvalidSessionException e) {
            // 获取不到SESSION不抛出异常
        }
    }

    @Override
    protected Session doCreateSession(SessionContext context) {
        try {
            return super.doCreateSession(context);
        } catch (IllegalStateException e) {
            return null;
        }
    }

    @Override
    protected Session newSessionInstance(SessionContext context) {
        Session session = super.newSessionInstance(context);
        session.setTimeout(getGlobalSessionTimeout());
        return session;
    }

    @Override
    public Session start(SessionContext context) {
        try {
            return super.start(context);
        } catch (NullPointerException e) {
            SimpleSession session = new SimpleSession();
            session.setId(0);
            return session;
        }
    }
}
