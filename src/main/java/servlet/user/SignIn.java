package servlet.user;

import model.User;
import servlet.IUserService;
import servlet.impl.UserServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

import static config.Config.WEB_URL_BEGIN;

/**
 * @author 连仕杰
 */
@WebServlet(urlPatterns = "/SignIn")
public class SignIn extends HttpServlet {


    static final String URL_INDEX = WEB_URL_BEGIN + "index.html";
    static final String URL_MAIN = WEB_URL_BEGIN + "up.html";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("Username");
        System.out.println(name);
        if ("".equals(name)) {
            response.sendRedirect(URL_INDEX + "?error=UsernameNull");
        } else {
            String password = request.getParameter("Password");
            IUserService userService = new UserServiceImpl();
            User user = null;
            try {
                user = userService.login(name, password);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (user != null) {
                request.getSession().setAttribute("user", user);
                response.sendRedirect(URL_MAIN);
            } else {
                response.sendRedirect(URL_INDEX + "?error=passwdError");

            }
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }
}
