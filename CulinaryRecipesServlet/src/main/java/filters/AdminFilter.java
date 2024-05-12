package filters;

import dto.UserDto;
import entities.UserRole;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Objects;

@WebFilter(urlPatterns = {"/ingredients", "/createIngredient", "/updateIngredient"})
public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Object user = ((HttpServletRequest) servletRequest).getSession().getAttribute("user");
        if (Objects.isNull(user)) {
            servletRequest.setAttribute("error", "Login or register to get access to this page");
            servletRequest.getRequestDispatcher("/login").forward(servletRequest, servletResponse);
        } else {
            if (((UserDto) user).getRole() != UserRole.ADMIN) {
                servletRequest.setAttribute("error", "You need to have admin rights to get access to this page");
                servletRequest.getRequestDispatcher("/culinaryNotes").forward(servletRequest, servletResponse);
            }
            else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }
}
