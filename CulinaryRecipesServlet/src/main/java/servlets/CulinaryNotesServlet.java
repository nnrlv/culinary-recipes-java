package servlets;

import dto.CulinaryNoteDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mappers.CulinaryNoteMapper;
import mappers.IngredientInCulinaryNoteMapper;
import repositories.CulinaryNoteRepository;
import repositories.IngredientInCulinaryNoteRepository;
import services.CulinaryNoteService;
import services.IngredientInCulinaryNoteService;
import utils.JspHelper;

import java.io.IOException;
import java.util.List;

import static utils.UrlPathHelper.*;

@WebServlet(CULINARY_NOTES)
public class CulinaryNotesServlet extends HttpServlet {
    private final CulinaryNoteService culinaryNoteService = new CulinaryNoteService(
            new CulinaryNoteRepository(),
            new CulinaryNoteMapper(),
            new IngredientInCulinaryNoteService(
                    new IngredientInCulinaryNoteRepository(),
                    new IngredientInCulinaryNoteMapper()
            )
    );

    private final IngredientInCulinaryNoteService ingredientInCulinaryNoteService = new IngredientInCulinaryNoteService(
                    new IngredientInCulinaryNoteRepository(),
                    new IngredientInCulinaryNoteMapper()
    );

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CulinaryNoteDto> culinaryNotes = culinaryNoteService.getAll();
        for (CulinaryNoteDto culinaryNote : culinaryNotes) {
            culinaryNote.setIngredientsInCulinaryNote(ingredientInCulinaryNoteService
                    .getAllByCulinaryNoteId(culinaryNote.getIdCulinaryNote()));
        }
        req.setAttribute("culinaryNotes", culinaryNotes);
        req.getRequestDispatcher(JspHelper.get("culinaryNotes")).forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");
        switch (action) {
            case "create":
                resp.sendRedirect(req.getContextPath() + CULINARY_NOTE_CREATE);
                break;
            case "update":
                resp.sendRedirect(req.getContextPath() + CULINARY_NOTE_UPDATE);
                break;
            case "delete":
                Long id = Long.parseLong(req.getParameter("idCulinaryNote"));
                culinaryNoteService.delete(id);
                break;
            case "viewDetails":
                resp.sendRedirect(req.getContextPath() + CULINARY_NOTE_DETAIL);
                break;
            case "viewIngredients":
                resp.sendRedirect(req.getContextPath() + INGREDIENTS);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                return;
        }

        resp.sendRedirect(req.getContextPath() + CULINARY_NOTES);
    }
}
