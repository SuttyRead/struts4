package com.ua.sutty.struts.action;

import com.opensymphony.xwork2.ActionSupport;
import com.ua.sutty.struts.domain.User;
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
public class LogoutAction extends ActionSupport {
    private HttpServletRequest request = ServletActionContext.getRequest();

    @Override
    public String execute() {
        User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return ERROR;
        }
        request.getSession().invalidate();
        return SUCCESS;
    }


}
