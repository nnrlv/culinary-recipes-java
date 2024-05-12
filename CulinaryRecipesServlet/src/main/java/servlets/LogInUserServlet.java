package servlets;

import repositories.UserRepository;
import dto.UserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mappers.UserMapper;
import services.UserService;
import utils.JspHelper;
import utils.PasswordHasher;

import java.io.IOException;
import java.util.Objects;

import org.apache.log4j.Logger;
import validators.LoginUserValidator;

import static utils.UrlPathHelper.CULINARY_NOTES;
import static utils.UrlPathHelper.LOGIN;


@WebServlet(LOGIN)
public class LogInUserServlet extends HttpServlet {
    private  final Logger logger = Logger.getLogger(HttpServlet.class);
    private final UserService userService = new UserService(new UserRepository(), new UserMapper());

    private final PasswordHasher passwordHasher = new PasswordHasher();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!Objects.isNull(req.getSession().getAttribute("user"))) {
            resp.sendRedirect(req.getContextPath() + CULINARY_NOTES);
            return;
        }
        req.getRequestDispatcher(JspHelper.get("login")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IllegalArgumentException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        try {
            LoginUserValidator validator = new LoginUserValidator();
            validator.validate(email, password);
            UserDto user = userService.getByEmail(email);
            if (passwordHasher.checkPassword(password, user.getPassword())) {
                req.getSession().setAttribute("user", user);
                resp.sendRedirect(req.getContextPath() + CULINARY_NOTES);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher(JspHelper.get("login")).forward(req, resp);
        }
    }
}
