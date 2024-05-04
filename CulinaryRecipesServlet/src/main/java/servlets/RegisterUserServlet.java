package servlets;

import repositories.UserRepository;
import dto.user.CreateUserDto;
import entities.User;
import exceptions.EmailAlreadyTakenException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mappers.user.CreateUserMapper;
import mappers.user.UserMapper;
import services.UserService;
import utils.JspHelper;

import java.io.IOException;

import static utils.UrlPathHelper.REGISTER;
import static utils.UrlPathHelper.LOGIN;

@WebServlet(REGISTER)
public class RegisterUserServlet extends HttpServlet {
    private static final UserService userService = new UserService(
            new UserRepository(),
            new UserMapper(),
            new CreateUserMapper()
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
            User user = userService.create(
                    new CreateUserDto(
                    firstName,
                    lastName,
                    password,
                    email)
            );
            resp.sendRedirect(req.getContextPath() + LOGIN);
        } catch (EmailAlreadyTakenException e) {
            String message = e.getMessage();
            req.setAttribute("message", message);
            req.getRequestDispatcher(JspHelper.get("register")).forward(req, resp);
        }
    }
}