package servlets;

import dto.IngredientDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mappers.IngredientMapper;
import org.apache.log4j.Logger;
import repositories.IngredientRepository;
import services.IngredientService;
import utils.JspHelper;
import utils.UrlPathHelper;
import validators.IngredientValidator;

import java.io.IOException;

import static utils.UrlPathHelper.INGREDIENTS;

@WebServlet(UrlPathHelper.INGREDIENT_CREATE)
public class CreateIngredientServlet extends HttpServlet {
    private final IngredientService ingredientService = new IngredientService(
            new IngredientRepository(),
            new IngredientMapper()
    );

    private  final Logger logger = Logger.getLogger(HttpServlet.class);

    private final IngredientValidator validator = new IngredientValidator();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.get("createIngredient")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        try {
            IngredientDto ingredient = new IngredientDto(name);
            validator.validate(ingredient);
            ingredientService.create(ingredient);
            resp.sendRedirect(req.getContextPath() + INGREDIENTS);
        } catch (Exception e) {
            String message = e.getMessage();
            logger.error(message);
            req.setAttribute("error", message);
            doGet(req, resp);
        }
    }
}
