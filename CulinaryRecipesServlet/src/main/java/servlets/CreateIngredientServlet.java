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

@WebServlet(UrlPathHelper.INGREDIENT_CREATE)
public class CreateIngredientServlet extends HttpServlet {
    private final IngredientService ingredientService = new IngredientService(
            new IngredientRepository(),
            new IngredientMapper()
    );

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.get("createIngredient")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        IngredientDto ingredient = new IngredientDto(null, name);

        ingredientService.create(ingredient);

        resp.sendRedirect(req.getContextPath() + UrlPathHelper.INGREDIENTS);
    }
}
