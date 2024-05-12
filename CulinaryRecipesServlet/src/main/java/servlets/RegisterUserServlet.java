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

import org.apache.log4j.Logger;
import validators.RegisterUserValidator;

import java.io.IOException;

import static utils.UrlPathHelper.REGISTER;
import static utils.UrlPathHelper.LOGIN;

@WebServlet(REGISTER)
public class RegisterUserServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(RegisterUserServlet.class);
    private static final UserService userService = new UserService(
            new UserRepository(),
            new UserMapper()
    );

    private final RegisterUserValidator validator = new RegisterUserValidator();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IllegalArgumentException {
        req.getRequestDispatcher(JspHelper.get("register")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        try {
            UserDto user = new UserDto(UserRole.USER, firstName, lastName, password, email);
            validator.validate(user);
            userService.create(user);
            resp.sendRedirect(req.getContextPath() + LOGIN);
        } catch (EmailAlreadyTakenException | IllegalArgumentException e) {
            String message = e.getMessage();
            logger.error(message);
            req.setAttribute("error", message);
            req.getRequestDispatcher(JspHelper.get("register")).forward(req, resp);
        }
    }
}