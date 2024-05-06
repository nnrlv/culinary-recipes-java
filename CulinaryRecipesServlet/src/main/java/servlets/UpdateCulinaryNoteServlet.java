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
import utils.UrlPathHelper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static utils.UrlPathHelper.*;

@WebServlet(CULINARY_NOTE_UPDATE)
public class UpdateCulinaryNoteServlet extends HttpServlet {
    private final CulinaryNoteService culinaryNoteService = new CulinaryNoteService(
            new CulinaryNoteRepository(),
            new CulinaryNoteMapper()
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
        Long id = Long.parseLong(req.getParameter("idCulinaryNote"));
        CulinaryNoteDto culinaryNote = culinaryNoteService.getById(id);

        req.setAttribute("culinaryNote", culinaryNote);

        List<IngredientDto> allIngredients = ingredientService.getAll();
        req.setAttribute("allIngredients", allIngredients);
        List<Long> ingredientsInCulinaryNote = ingredientInCulinaryNoteService
                .getAllByCulinaryNoteId(culinaryNote.getIdCulinaryNote())
                .stream()
                .map(IngredientInCulinaryNoteDto::getIngredient)
                .map(IngredientDto::getIdIngredient)
                .toList();
        req.setAttribute("ingredientsInCulinaryNote", ingredientsInCulinaryNote);

        List<String> allCategoryNames = Category.getAllNames();
        req.setAttribute("allCategoryNames", allCategoryNames);
        List<String> categoriesOfCulinaryNote = culinaryNote.getCategories()
                .stream()
                .map(Enum::name)
                .toList();
        req.setAttribute("categoriesOfCulinaryNote", categoriesOfCulinaryNote);

        List<String> allUnitOfMeasurementNames = UnitOfMeasurement.getAllNames();
        req.setAttribute("allUnitOfMeasurementNames", allUnitOfMeasurementNames);

        req.getRequestDispatcher(JspHelper.get("updateCulinaryNote")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("idCulinaryNote"));
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String instructions = req.getParameter("instructions");
        String[] selectedCategories = req.getParameterValues("categories");

        List<Category> categories = Arrays.stream(selectedCategories)
                .map(Category::valueOf)
                .collect(Collectors.toList());

        CulinaryNoteDto culinaryNote = culinaryNoteService.getById(id);
        culinaryNote.setName(name);
        culinaryNote.setDescription(description);
        culinaryNote.setInstructions(instructions);
        culinaryNote.setCategories(categories);

        culinaryNoteService.update(culinaryNote);

        resp.sendRedirect(req.getContextPath() + UrlPathHelper.CULINARY_NOTES);
    }
}

