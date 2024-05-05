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

import java.io.IOException;
import java.util.List;

import static utils.UrlPathHelper.INGREDIENTS;
import static utils.UrlPathHelper.INGREDIENT_CREATE;
import static utils.UrlPathHelper.INGREDIENT_UPDATE;

@WebServlet(INGREDIENTS)
public class IngredientsServlet extends HttpServlet {
    private final IngredientService ingredientService = new IngredientService(
            new IngredientRepository(),
            new IngredientMapper()
    );

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<IngredientDto> ingredients = ingredientService.getAll();
        req.setAttribute("ingredients", ingredients);
        req.getRequestDispatcher(JspHelper.get("ingredients")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        switch (action) {
            case "create":
                resp.sendRedirect(req.getContextPath() + INGREDIENT_CREATE);
                break;
            case "update":
                resp.sendRedirect(req.getContextPath() + INGREDIENT_UPDATE);
                break;
            case "delete":
                Long id = Long.parseLong(req.getParameter("idIngredient"));
                ingredientService.delete(id);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                return;
        }
        resp.sendRedirect(req.getContextPath() + INGREDIENTS);
    }
}
