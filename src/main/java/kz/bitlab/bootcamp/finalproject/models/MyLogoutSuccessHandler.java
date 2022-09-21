package kz.bitlab.bootcamp.finalproject.models;

import kz.bitlab.bootcamp.finalproject.repositories.UserRepository;
import kz.bitlab.bootcamp.finalproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;

@Component("myLogoutSuccessHandler")
public class MyLogoutSuccessHandler implements LogoutSuccessHandler{

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        User user = userRepository.findByEmail(authentication.getName());
        HttpSession session = request.getSession();
        if (session != null){
            session.removeAttribute("user");
            user.setOnline(false);
            user.setExitTime(new Timestamp(System.currentTimeMillis()));
            userRepository.save(user);
        }
        response.sendRedirect("/signin");
    }
}