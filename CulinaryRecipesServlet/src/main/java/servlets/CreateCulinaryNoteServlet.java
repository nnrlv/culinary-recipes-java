package servlets;

import dto.CulinaryNoteDto;
import dto.IngredientDto;
import dto.IngredientInCulinaryNoteDto;
import dto.UserDto;
import entities.Category;
import entities.UnitOfMeasurement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mappers.CulinaryNoteMapper;
import mappers.IngredientInCulinaryNoteMapper;
import mappers.IngredientMapper;
import repositories.CulinaryNoteRepository;
import repositories.IngredientInCulinaryNoteRepository;
import repositories.IngredientRepository;
import services.CulinaryNoteService;
import services.IngredientInCulinaryNoteService;
import services.IngredientService;
import utils.JspHelper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static utils.UrlPathHelper.CULINARY_NOTE_CREATE;

@WebServlet(CULINARY_NOTE_CREATE)
public class CreateCulinaryNoteServlet extends HttpServlet {
    private final CulinaryNoteService culinaryNoteService = new CulinaryNoteService(
            new CulinaryNoteRepository(),
            new CulinaryNoteMapper(),
            new IngredientInCulinaryNoteService(
                    new IngredientInCulinaryNoteRepository(),
                    new IngredientInCulinaryNoteMapper()
            )
    );

    private final IngredientService ingredientService = new IngredientService(
            new IngredientRepository(),
            new IngredientMapper()
    );

    private final IngredientInCulinaryNoteService ingredientInCulinaryNoteService = new IngredientInCulinaryNoteService(
            new IngredientInCulinaryNoteRepository(),
            new IngredientInCulinaryNoteMapper()
    );

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<IngredientDto> ingredients = ingredientService.getAll();
        req.setAttribute("ingredients", ingredients);
        List<String> allCategoryNames = Category.getAllNames();
        req.setAttribute("allCategoryNames", allCategoryNames);
        List<String> allUnitOfMeasurementNames = UnitOfMeasurement.getAllNames();
        req.setAttribute("allUnitOfMeasurementNames", allUnitOfMeasurementNames);
        req.getRequestDispatcher(JspHelper.get("createCulinaryNote")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String instructions = req.getParameter("instructions");
        String[] selectedCategories = req.getParameterValues("categories");
        String[] selectedIngredients = req.getParameterValues("ingredients");

        HttpSession session = req.getSession();
        UserDto user = (UserDto) session.getAttribute("user");

        List<Category> categories = Arrays.stream(selectedCategories)
                .map(Category::valueOf)
                .collect(Collectors.toList());

        CulinaryNoteDto culinaryNote = new CulinaryNoteDto();
        culinaryNote.setName(name);
        culinaryNote.setDescription(description);
        culinaryNote.setInstructions(instructions);
        culinaryNote.setUser(user);
        culinaryNote.setCategories(categories);

        CulinaryNoteDto created = culinaryNoteService.create(culinaryNote);

        for (String selectedIngredient : selectedIngredients) {
            double amount = Double.parseDouble(req.getParameter("amount_" + selectedIngredient));
            UnitOfMeasurement unitOfMeasurement = UnitOfMeasurement.valueOf(req.getParameter("unit_" + selectedIngredient));
            IngredientDto ingredient = ingredientService.getById(Long.valueOf(selectedIngredient));
            IngredientInCulinaryNoteDto ingredientInCulinaryNoteDto =
                    new IngredientInCulinaryNoteDto(ingredient, created, unitOfMeasurement, amount);
            ingredientInCulinaryNoteService.create(ingredientInCulinaryNoteDto);
        }

        resp.sendRedirect(req.getContextPath() + "/culinaryNoteDetail?idCulinaryNote=" + created.getIdCulinaryNote());
    }
}

