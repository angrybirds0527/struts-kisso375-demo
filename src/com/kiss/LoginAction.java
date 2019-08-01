package com.kiss;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.CookiesAware;

import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.security.token.SSOToken;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport  implements CookiesAware{
	private static final long serialVersionUID = 1L;

    @Override
    public String execute() throws Exception {
        System.out.println("xxAction");
        
        return SUCCESS;
    }
   
    public String login() {
    	 HttpServletRequest request = ServletActionContext.getRequest();
    	
    	SSOToken ssoToken = SSOHelper.getSSOToken(request);
    	
    	String xx = "";
    	if (null != ssoToken) {
    		xx =  "登录信息 ip=" + ssoToken.getIp() + "， id=" + ssoToken.getId() + "， issuer=" + ssoToken.getIssuer();
		}
    	System.out.println("ssoToken:"+xx);
    	
        return SUCCESS;
    }
    
    
    public String logout() {
    	 HttpServletRequest request = ServletActionContext.getRequest();
    	 HttpServletResponse response = ServletActionContext.getResponse();
    	 SSOHelper.clearLogin(request, response);
         return SUCCESS;
    }

	@Override
	public void setCookiesMap(Map<String, String> arg0) {
		// TODO Auto-generated method stub
		
	}

}
