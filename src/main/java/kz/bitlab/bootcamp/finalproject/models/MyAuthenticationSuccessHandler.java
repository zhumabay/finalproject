package kz.bitlab.bootcamp.finalproject.models;

import kz.bitlab.bootcamp.finalproject.repositories.UserRepository;
import kz.bitlab.bootcamp.finalproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component("myAuthenticationSuccessHandler")
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private ActiveUserStore activeUserStore;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException {
        User user = userRepository.findByEmail(authentication.getName());
        HttpSession session = request.getSession(false);
        if (session != null) {
            LoggedUser loggedUser = new LoggedUser(authentication.getName(), activeUserStore);
            session.setAttribute("user", loggedUser);
            user.setOnline(true);
            userRepository.save(user);
        }
        response.sendRedirect("/usersprofile/"+user.getId());
    }
}