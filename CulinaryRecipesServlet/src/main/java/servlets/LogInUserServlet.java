package servlets;

import repositories.UserRepository;
import dto.user.UserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mappers.user.CreateUserMapper;
import mappers.user.UserMapper;
import services.UserService;
import utils.JspHelper;
import utils.PasswordHasher;

import java.io.IOException;
import java.util.Objects;

import static utils.UrlPathHelper.CULINARY_NOTES;
import static utils.UrlPathHelper.LOGIN;

@WebServlet(LOGIN)
public class LogInUserServlet extends HttpServlet {
    private final UserService userService = new UserService(new UserRepository(), new UserMapper(), new CreateUserMapper());

    private final PasswordHasher passwordHasher = new PasswordHasher();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!Objects.isNull(req.getSession().getAttribute("user"))) {
            resp.sendRedirect(req.getContextPath());
            return;
        }
        req.getRequestDispatcher(JspHelper.get("login")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        try {
            UserDto user = userService.getByEmail(email);
            if (passwordHasher.checkPassword(password, user.getPassword())) {
                req.getSession().setAttribute("user", user);
                resp.sendRedirect(req.getContextPath() + CULINARY_NOTES);
            }
            else
            {
                throw new ServletException("Wrong email or password.");
            }
        } catch (Exception e) {
            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher(JspHelper.get("login")).forward(req, resp);
        }
    }
}
