package servlets;

import dto.IngredientDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mappers.IngredientMapper;
import repositories.IngredientRepository;
import services.IngredientService;
import utils.JspHelper;
import utils.UrlPathHelper;

import java.io.IOException;

@WebServlet(UrlPathHelper.INGREDIENT_UPDATE)
public class UpdateIngredientServlet extends HttpServlet {
    private final IngredientService ingredientService = new IngredientService(
            new IngredientRepository(),
            new IngredientMapper()
    );

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("idIngredient"));
        IngredientDto ingredient = ingredientService.getById(id);
        if (ingredient == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Ingredient not found");
            return;
        }
        req.setAttribute("ingredient", ingredient);
        req.getRequestDispatcher(JspHelper.get("updateIngredient")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long id = Long.parseLong(req.getParameter("idIngredient"));
        String name = req.getParameter("name");
        IngredientDto ingredient = new IngredientDto(id, name);

        ingredientService.update(ingredient);

        resp.sendRedirect(req.getContextPath() + UrlPathHelper.INGREDIENTS);
    }
}
