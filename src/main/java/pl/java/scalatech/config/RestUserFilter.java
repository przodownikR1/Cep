package pl.java.scalatech.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.MDC;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class RestUserFilter implements Filter
{
    private WebApplicationContext springContext;

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest) request;        
        final String clientIpAddress = request.getRemoteAddr();
        final String ip = request.getRemoteAddr();
        try {
        if (ip != null) {
            MDC.put("ip", ip);
          } else {
            // Only null in test case:
            MDC.put("ip", "unknown");
          }
       
       //MDC.put("Id", StringUUID.getUUID());  

        }finally {
            MDC.remove("ip");
            MDC.remove("user");
        }
        
    }
    private String getAttribute(final HttpServletRequest req, final String key)
    {
      String value = req.getHeader(key);
      if (value == null) {
        value = req.getParameter(key);
      }
      return value;
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext());
        final AutowireCapableBeanFactory beanFactory = springContext.getAutowireCapableBeanFactory();
        beanFactory.autowireBean(this);
        
    }
    
}