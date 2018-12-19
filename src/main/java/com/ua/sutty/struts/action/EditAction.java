package com.ua.sutty.struts.action;

import com.opensymphony.xwork2.ActionSupport;
import com.ua.sutty.struts.domain.User;
import com.ua.sutty.struts.form.UserForm;
import com.ua.sutty.struts.service.HibernateUserService;
import lombok.Getter;
import lombok.Setter;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import javax.servlet.http.HttpServletRequest;

@Namespace(value = "/")
@Action(value = "/edit", results = {
        @Result(name = "input", location = "/WEB-INF/jsp/edit.jsp"),
        @Result(name = "success", type = "redirect", location = "/home"),
        @Result(name = "accessDenied", location = "/WEB-INF/jsp/403.jsp"),
        @Result(name = "error", type = "redirect", location = "/login")
})
public class EditAction extends ActionSupport {
    private HttpServletRequest request = ServletActionContext.getRequest();
    //    private User user;
    @Getter
    @Setter
    private UserForm userForm;
    @Getter
    @Setter
    private User user;


    @Override
    public String execute() throws Exception {
        User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
        HibernateUserService hibernateUserService = new HibernateUserService();
        if (loggedInUser == null) {
            return ERROR;
        }
        if (loggedInUser.getRole().getName().equals("ADMIN")) {
            String userId = request.getParameter("userId");
            if (request.getMethod().equals("GET")) {


                user = hibernateUserService.findById(Long.valueOf(userId));

                return INPUT;
            }
            if (request.getMethod().equals("POST")) {
                System.out.println("UserForm" + userForm);
                User toUser = userForm.toUser();
                toUser.setId(Long.valueOf(userId));
                hibernateUserService.update(toUser);
                return SUCCESS;
            }
            return ERROR;
        } else {
            return "accessDenied";
        }
    }

}
