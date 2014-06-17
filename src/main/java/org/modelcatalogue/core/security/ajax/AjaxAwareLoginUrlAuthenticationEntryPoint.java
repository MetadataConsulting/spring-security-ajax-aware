package org.modelcatalogue.core.security.ajax;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxAwareLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {
    public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException authException) throws IOException, ServletException {
        String acceptHeader = request.getHeader("Accept");
        if (acceptHeader != null && acceptHeader.startsWith("application/json")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
        } else {
            super.commence(request, response, authException);
        }
    }
}