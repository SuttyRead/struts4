package com.ua.sutty.struts.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Namespace(value = "/")
@Action(value = "/logout")
@Results({
        @Result(name = "success", type = "redirect", location = "/login?logout"),
        @Result(name = "error", type = "redirect", location = "/login?error")
})
public class LogoutAction extends ActionSupport implements SessionAware {
    private HttpServletRequest request = ServletActionContext.getRequest();
    private SessionMap<String, Object> sessionMap;

    @Override
    public String execute() throws Exception {
        request.getSession().invalidate();
        return SUCCESS;
    }

    @Override
    public void setSession(Map<String, Object> map) {
        sessionMap = (SessionMap<String, Object>) map;
        sessionMap.put("login", "true");
    }

    public SessionMap<String, Object> getSessionMap() {
        return sessionMap;
    }

    public void setSessionMap(SessionMap<String, Object> sessionMap) {
        this.sessionMap = sessionMap;
    }

}
