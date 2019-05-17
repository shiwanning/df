package com.tcg.mis.common.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;

import com.tcg.mis.common.constant.UriPermission;
import com.tcg.mis.common.http.BehaviorRequestWrapper;
import com.tcg.mis.service.subsystem.OSService;

import io.swagger.models.HttpMethod;

@Component
public class InterceptorConfig implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory
			.getLogger(InterceptorConfig.class);

	private UrlPathHelper urlPathHelper = new UrlPathHelper();

	private OSService ossService;

	public InterceptorConfig(OSService ossService) {
		this.ossService = ossService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		String token = request.getHeader("Authorization");
		String passMerchant = "";
		String merchantCode = request.getParameter("merchantCode");

		if(merchantCode != null){
			passMerchant = merchantCode;
		}
		
		final String uri = request.getRequestURI();
		final String queryString = urlPathHelper.getOriginatingQueryString(request);
		String logParams = queryString;
		if("POST".equals(request.getMethod())) {
			logParams = ((BehaviorRequestWrapper) request).getBody();
		}
		
		boolean hasUrlPermission = hasUrlPermission(
				request.getRequestURI(), request.getMethod(), token, logParams, passMerchant);
		if (!hasUrlPermission) {
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter()
					.write("{\"success\":\"false\",\"errorCode\":\"403\",\"message\":\"Not permitted to access the url!\"}");
			return false;
		}

		logger.info("===========InterceptorConfig=======preHandle======= URI: "
				+ uri + " QueryURLString: 【"
				+ (queryString == null ? "" : queryString) + "】");

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse httpServletResponse, Object o,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse httpServletResponse, Object o, Exception e)
			throws Exception {

	}
	
	public boolean hasUrlPermission(String requestURI, String method, String token, String logParams,String merchantCode) {
        if(UriPermission.hasURIMapping(requestURI)) {
        	Integer menuId = UriPermission.getMenuId(requestURI, method);
            return ossService.hasPermission(logParams, token, menuId, merchantCode);
        }
        return true;
    }
}
