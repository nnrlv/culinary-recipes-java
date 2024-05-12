package servlets;

import dto.CulinaryNoteDto;
import dto.IngredientDto;
import dto.IngredientInCulinaryNoteDto;
import entities.Category;
import entities.UnitOfMeasurement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mappers.CulinaryNoteMapper;
import mappers.IngredientInCulinaryNoteMapper;
import mappers.IngredientMapper;
import org.apache.log4j.Logger;
import repositories.CulinaryNoteRepository;
import repositories.IngredientInCulinaryNoteRepository;
import repositories.IngredientRepository;
import services.CulinaryNoteService;
import services.IngredientInCulinaryNoteService;
import services.IngredientService;
import utils.JspHelper;
import validators.CulinaryNoteValidator;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static utils.UrlPathHelper.CULINARY_NOTE_UPDATE;

@WebServlet(CULINARY_NOTE_UPDATE)
public class UpdateCulinaryNoteServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(RegisterUserServlet.class);
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

    private final CulinaryNoteValidator validator = new CulinaryNoteValidator();

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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Long id = Long.parseLong(req.getParameter("idCulinaryNote"));
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String instructions = req.getParameter("instructions");
        String[] selectedCategories = req.getParameterValues("categories");
        String[] selectedIngredients = req.getParameterValues("ingredients");

        try {
            CulinaryNoteDto culinaryNote = culinaryNoteService.getById(id);
            culinaryNote.setName(name);
            culinaryNote.setDescription(description);
            culinaryNote.setInstructions(instructions);

            if (selectedCategories != null) {
                List<Category> categories = Arrays.stream(selectedCategories)
                        .map(Category::valueOf)
                        .toList();
                culinaryNote.setCategories(categories);
            }

            for (IngredientInCulinaryNoteDto ingredient : ingredientInCulinaryNoteService
                    .getAllByCulinaryNoteId(culinaryNote.getIdCulinaryNote())) {
                ingredientInCulinaryNoteService
                        .delete(ingredient.getIngredient().getIdIngredient(), culinaryNote.getIdCulinaryNote());
            }

            if (selectedIngredients != null) {
                for (String selectedIngredient : selectedIngredients) {
                    double amount = Double.parseDouble(req.getParameter("amount_" + selectedIngredient));
                    UnitOfMeasurement unitOfMeasurement = UnitOfMeasurement.valueOf(req.getParameter("unit_" + selectedIngredient));
                    IngredientDto ingredient = ingredientService.getById(Long.valueOf(selectedIngredient));
                    IngredientInCulinaryNoteDto ingredientInCulinaryNoteDto =
                            new IngredientInCulinaryNoteDto(ingredient, culinaryNote, unitOfMeasurement, amount);
                    ingredientInCulinaryNoteService.create(ingredientInCulinaryNoteDto);
                }
            }
            validator.validate(culinaryNote);
            culinaryNoteService.update(culinaryNote);
            resp.sendRedirect(req.getContextPath() + "/culinaryNoteDetail?idCulinaryNote=" + culinaryNote.getIdCulinaryNote());
        } catch (Exception e) {
            String message = e.getMessage();
            logger.error(message);
            req.setAttribute("error", message);
            doGet(req, resp);
        }
    }
}

