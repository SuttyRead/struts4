package com.ua.sutty.struts.action;

import com.opensymphony.xwork2.ActionSupport;
import com.ua.sutty.struts.domain.User;
import com.ua.sutty.struts.service.HibernateUserService;
import lombok.Getter;
import lombok.Setter;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import javax.servlet.http.HttpServletRequest;

@Namespace(value = "/")
@Action(value = "/login", results = {
        @Result(name = "input", location = "/WEB-INF/jsp/login.jsp"),
        @Result(name = "success", type = "redirect", location = "/home")
})
public class LoginAction extends ActionSupport {
    private HttpServletRequest request = ServletActionContext.getRequest();

    @Getter
    @Setter
    private User user;

    @Override
    public String execute() {
        if (request.getMethod().equals("GET")) {
            if (request.getParameter("successfullyRegistration") != null) {
                request.getSession().setAttribute("successfullyRegistration", true);
            } else {
                request.getSession().removeAttribute("successfullyRegistration");
            }
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

}
