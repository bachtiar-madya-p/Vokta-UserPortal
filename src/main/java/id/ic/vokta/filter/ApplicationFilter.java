package id.ic.vokta.filter;

import id.ic.vokta.rest.model.ServiceResponse;
import id.ic.vokta.util.helper.JWTHelper;
import id.ic.vokta.util.json.JsonHelper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*")
public class ApplicationFilter implements Filter {

    private List<String> unprotectedList;

    @Override
    public void init(FilterConfig config) throws ServletException {
        unprotectedList = new ArrayList<>();
        unprotectedList.add("/");

        //BE API
        unprotectedList.add("/rest/onboard/register-start");
        unprotectedList.add("/rest/register/validate/email");
        unprotectedList.add("/rest/register/store");
        unprotectedList.add("/rest/register/otp/resend");
        unprotectedList.add("/rest/register/verify");
        unprotectedList.add("/rest/onboard/register-status");
        unprotectedList.add("/rest/onboard/register-complete");
        unprotectedList.add("/rest/onboard/environment");

        unprotectedList.add("/rest/onboard/login-start");
        unprotectedList.add("/rest/auth");

    }

    private boolean isProtectedPath(String path) {
        boolean protectedPath = true;

        for (String pathUrl : unprotectedList) {
            if (path.equalsIgnoreCase(pathUrl)) {
                protectedPath = false;
                break;
            }
        }
        return protectedPath;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        boolean valid = true;

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String path = request.getRequestURI().substring(request.getContextPath().length());

        if (path.contains("/lib/") || path.contains("/js/") || path.contains("/css/") || path.contains("/images/")
                || path.contains(".txt") || path.contains(".json") || path.contains(".js") || path.contains("/asset/")) {
            chain.doFilter(servletRequest, servletResponse);
        } else {
            if (isProtectedPath(path)) {
                HttpSession session = request.getSession(false);
                if (session == null) {
                    valid = false;
                }
            }

            if (valid) {
                chain.doFilter(servletRequest, servletResponse);
            } else {
                // Get the Bearer token from the Authorization header
                String token = extractToken(request);

                if (token != null && isValidToken(token)) {
                    // Token is valid, proceed with the request
                    chain.doFilter(request, servletResponse);
                } else {
                    // Token is missing or invalid, return 401 Unauthorized
                    httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                    // Construct the JSON response message
                    httpResponse.setContentType("application/json"); // Set response content type
                    PrintWriter writer = httpResponse.getWriter();

                    ServiceResponse serviceResponse = new ServiceResponse();
                    serviceResponse.setStatus(401);
                    serviceResponse.setDescription("Invalid or missing token");

                    writer.print(JsonHelper.toJson(serviceResponse));
                }
            }
        }
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); // Remove "Bearer " prefix
        }
        return null;
    }

    private boolean isValidToken(String token) {
        return JWTHelper.validateJWT(token);
    }

}
