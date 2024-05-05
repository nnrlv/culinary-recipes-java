package servlets;

import dto.UserDto;
import entities.UserRole;
import repositories.UserRepository;
import exceptions.EmailAlreadyTakenException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mappers.UserMapper;
import services.UserService;
import utils.JspHelper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static utils.UrlPathHelper.REGISTER;
import static utils.UrlPathHelper.LOGIN;

@WebServlet(REGISTER)
public class RegisterUserServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(RegisterUserServlet.class);
    private static final UserService userService = new UserService(
            new UserRepository(),
            new UserMapper()
    );
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.get("register")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        try {
            userService.create(
                    new UserDto(null, UserRole.USER, firstName, lastName, password, email)
            );
            resp.sendRedirect(req.getContextPath() + LOGIN);
        } catch (EmailAlreadyTakenException e) {
            logger.error(e.getMessage());
            String message = e.getMessage();
            req.setAttribute("message", message);
            req.getRequestDispatcher(JspHelper.get("register")).forward(req, resp);
        }
    }
}