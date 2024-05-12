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

import static repositories.IngredientRepository.CREATE_INGREDIENT;
import static repositories.IngredientRepository.UPDATE_INGREDIENT;
import static utils.UrlPathHelper.CULINARY_NOTES;
import static utils.UrlPathHelper.INGREDIENT_UPDATE;

@WebServlet(UrlPathHelper.INGREDIENT_UPDATE)
public class UpdateIngredientServlet extends HttpServlet {
    private final IngredientService ingredientService = new IngredientService(
            new IngredientRepository(),
            new IngredientMapper()
    );

    private final IngredientValidator validator = new IngredientValidator();

    private  final Logger logger = Logger.getLogger(HttpServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("idIngredient"));
        IngredientDto ingredient = ingredientService.getById(id);
        if (ingredient == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Ingredient not found");
            return;
        }
        String error = req.getParameter("error");
        req.setAttribute("ingredient", ingredient);
        req.setAttribute("error", error);
        req.getRequestDispatcher(JspHelper.get("updateIngredient")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("idIngredient"));
        String name = req.getParameter("name");
        IngredientDto ingredient = new IngredientDto(id, name);
        try {
            validator.validate(ingredient);
            ingredientService.update(ingredient);
            resp.sendRedirect(req.getContextPath() + UrlPathHelper.INGREDIENTS);
        } catch (Exception e) {
            String message = e.getMessage();
            logger.error(message);
            req.setAttribute("error", message);
            req.setAttribute("ingredient", ingredient);
            req.getRequestDispatcher(JspHelper.get("updateIngredient")).forward(req, resp);
        }
    }
}
