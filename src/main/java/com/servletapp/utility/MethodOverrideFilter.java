package com.servletapp.utility;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class MethodOverrideFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    if (request instanceof HttpServletRequest) {
      HttpServletRequest httpServletRequest = (HttpServletRequest) request;
      String method = httpServletRequest.getParameter("_method");
      if ("DELETE".equalsIgnoreCase(method)) {
        HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(httpServletRequest) {
          @Override
          public String getMethod() {
            return "DELETE";
          }
        };
        chain.doFilter(wrapper, response);
        return;
      }
    }
    chain.doFilter(request, response);
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {}

  @Override
  public void destroy() {}
}
