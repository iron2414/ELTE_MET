package config.authentication;

import app.entity.User;
import app.entity.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

@Service
public class RESTAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setHeader("Content-Type","application/json");
        User user = (User)authentication.getPrincipal();
//        user.setSessionId(RequestContextHolder.currentRequestAttributes().getSessionId());
        user.setLastLogin(new Date());
        userRepository.save(user);
        //TODO auditlog
        String data = objectMapper.writeValueAsString(user);
        response.getWriter().print("{\"success\":true,\"data\":{\"user\":"+data+"}}");
    }
}