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
import java.util.List;
import java.util.Map;

@Namespace("/")
@Action(value = "/home", results = {
        @Result(name = "admin", location = "/WEB-INF/jsp/admin-home.jsp"),
        @Result(name = "user", location = "/WEB-INF/jsp/user-home.jsp"),
        @Result(name = "error", type = "redirect", location = "/login")
})
public class HomeAction extends ActionSupport implements SessionAware {
    private HttpServletRequest request = ServletActionContext.getRequest();
    private User user;
    private SessionMap<String, Object> sessionMap;

    @Override
    public String execute() throws Exception {
        if (sessionMap.get("loggedInUser") != null) {
            User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
            if (loggedInUser.getRole().getName().equals("ADMIN")) {
                HibernateUserService hibernateUserService = new HibernateUserService();
                request.getSession().setAttribute("users", hibernateUserService.findAll());
                return "admin";
            }
            if (loggedInUser.getRole().getName().equals("USER")) {
                return "user";
            } else {
                return ERROR;
            }
        } else {
            return ERROR;
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void setSession(Map<String, Object> map) {
        sessionMap = (SessionMap<String, Object>) map;
    }

}
