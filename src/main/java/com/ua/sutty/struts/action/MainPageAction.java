package com.ua.sutty.struts.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

@Namespace("/")
@Action(value = "")
@Result(location = "/WEB-INF/jsp/main.jsp")
public class MainPageAction extends ActionSupport {
}
