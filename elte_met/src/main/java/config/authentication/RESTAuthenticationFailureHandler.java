package config.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import exception.InvalidCredentialsException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import util.Response.Response;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RESTAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        System.out.println(exception.getMessage());
        System.out.println(exception);
        System.out.println("REST FAILURE");

        ResponseEntity<Object> objectResponseEntity = Response.create(exception);
        ObjectMapper objectMapper = new ObjectMapper();
        response.setHeader("Content-Type","application/json");
        String data = objectMapper.writeValueAsString(objectResponseEntity.getBody());
        response.getWriter().print(data);
    }
}