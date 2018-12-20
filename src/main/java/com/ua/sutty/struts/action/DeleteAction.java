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
@Action(value = "/delete", results = {
        @Result(name = "success", type = "redirect", location = "/home"),
        @Result(name = "accessDenied", location = "/WEB-INF/jsp/403.jsp"),
        @Result(name = "error", type = "redirect", location = "/home")
})
@Getter
@Setter
public class DeleteAction extends ActionSupport {
    private HttpServletRequest request = ServletActionContext.getRequest();
    private User user;

    @Override
    public String execute() throws Exception {
        HibernateUserService hibernateUserService = new HibernateUserService();
        User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
        if (loggedInUser == null) {
            addActionError("Must be sign in");
            return ERROR;
        }
        if (loggedInUser.getRole().getName().equals("ADMIN")) {
            String userId = request.getParameter("userIdForDelete");
            if (userId == null) {
                return ERROR;
            }
            User byId = hibernateUserService.findById(Long.valueOf(userId));
            if (byId != null) {
                hibernateUserService.remove(byId);
                return SUCCESS;
            }

            return ERROR;
        } else {
            return "accessDenied";
        }
    }

}
