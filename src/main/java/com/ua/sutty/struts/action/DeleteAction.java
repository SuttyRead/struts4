package com.ua.sutty.struts.action;

import com.opensymphony.xwork2.ActionSupport;
import com.ua.sutty.struts.domain.User;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import javax.servlet.http.HttpServletRequest;

@Namespace(value = "/")
@Action(value = "/delete", results = {
        @Result(name = "input", location = "/WEB-INF/jsp/add.jsp"),
        @Result(name = "success", type = "redirect", location = "/home"),
        @Result(name = "accessDenied", location = "/WEB-INF/jsp/403.jsp"),
        @Result(name = "error", type = "redirect", location = "/login")
})
public class DeleteAction extends ActionSupport {
    private HttpServletRequest request = ServletActionContext.getRequest();
    private User user;

    @Override
    public String execute() throws Exception {
        User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
        if (loggedInUser == null){
            return ERROR;
        }
        if (loggedInUser.getRole().getName().equals("ADMIN")) {
            if (request.getMethod().equals("GET")) {
                return INPUT;
            }
            if (request.getMethod().equals("POST")){
                System.out.println("User = " + user);
            }
            return ERROR;
        } else {
            return "accessDenied";
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
