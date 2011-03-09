package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.HomeCtrl;

import model.User;

import utilities.FacesUtil;

public class LoginRequired implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;

		String page = req.getRequestURL().toString();
		System.out.println(req.getQueryString());
		System.out.println(page);
		
	    if ((! FacesUtil.getUserConnected())
		    	&& page.contains("route/create")) {
	    	resp.sendRedirect("/Covoiturage/user/login.jsf");
	    } else {
	            chain.doFilter(request, response);
	    }
	}


	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
