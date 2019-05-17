package com.tcg.mis.common.interceptor;

import com.tcg.mis.common.log.TcgLogFactory;

import org.slf4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description: Behavior Interceptor for log. <br/>
 *
 * @author Eddie
 */
public class BehaviorLogInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOGGER = TcgLogFactory.getLogger("behaviorLog");
    private static final UrlPathHelper urlPathHelper = new UrlPathHelper();
    private static final String START_TIME = "startTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.preHandle(request, response, handler);

        request.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        final String uri = request.getRequestURI();
        final String queryString = urlPathHelper.getOriginatingQueryString(request);
        if (uri.contains("swagger")) {
            return true;
        }

        long startTime = System.currentTimeMillis();
        request.setAttribute(START_TIME, startTime);

        LOGGER.info("[BEHAVIOR] [START] URI: {}, QueryString: {}", uri, queryString);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);

        final String uri = request.getRequestURI();
        if (uri.contains("swagger")) {
            return;
        }

        Long startTime = (Long) request.getAttribute(START_TIME);
        if (startTime != null) {
            long endTime = System.currentTimeMillis();
            long elapsed = endTime - startTime;

            LOGGER.info("[BEHAVIOR] [END] URI: {}, Elapsed: {}", uri, elapsed);
        }
    }
}
