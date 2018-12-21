package com.ua.sutty.struts.action;

import com.opensymphony.xwork2.ActionSupport;
import com.ua.sutty.struts.domain.User;
import com.ua.sutty.struts.service.HibernateUserService;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Namespace(value = "/")
@Action(value = "/login", results = {
        @Result(name = "input", location = "/WEB-INF/jsp/login.jsp"),
        @Result(name = "success", type = "redirect", location = "/home")
})
public class LoginAction extends ActionSupport implements SessionAware {
    private HttpServletRequest request = ServletActionContext.getRequest();
    private SessionMap<String, Object> sessionMap;

    private User user;

    @Override
    public String execute() throws Exception {
        if (request.getMethod().equals("GET")) {
            return INPUT;
        }
        if (request.getMethod().equals("POST")) {
            HibernateUserService hibernateUserService = new HibernateUserService();
            User userInBase = hibernateUserService.findByLogin(user.getLogin());
            if (userInBase != null && user.getPassword().equals(userInBase.getPassword())) {
                request.getSession().setAttribute("loggedInUser", userInBase);
                return SUCCESS;
            } else {
                addActionError("Login or password incorrect");
            }
        }
        return INPUT;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SessionMap<String, Object> getSessionMap() {
        return sessionMap;
    }

    public void setSessionMap(SessionMap<String, Object> sessionMap) {
        this.sessionMap = sessionMap;
    }

    @Override
    public void setSession(Map<String, Object> map) {
        sessionMap = (SessionMap<String, Object>) map;
        sessionMap.put("login", "true");
    }

}
