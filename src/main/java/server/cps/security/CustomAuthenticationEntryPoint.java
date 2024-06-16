package server.cps.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import server.cps.aop.ErrorStatus;

import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorStatus exception = (ErrorStatus) request.getAttribute("exception");

        if (exception.equals(ErrorStatus.NOT_JWT_ERROR)) {
            exceptionHandler(response, ErrorStatus.NOT_JWT_ERROR);
            return;
        }

        if (exception.equals(ErrorStatus.EXP_JWT_ERROR)) {
            exceptionHandler(response, ErrorStatus.EXP_JWT_ERROR);
            return;
        }

        if (exception.equals(ErrorStatus.NOT_FOUND_JWT_USER)) {
            exceptionHandler(response, ErrorStatus.NOT_FOUND_JWT_USER);
        }
    }

    public void exceptionHandler(HttpServletResponse response, ErrorStatus error) {
        response.setStatus(403);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            String json = new ObjectMapper().writeValueAsString(error.getMessage());
            response.getWriter().write(json);
        } catch (Exception e) {
            log.info(e.toString());
        }
    }
}
