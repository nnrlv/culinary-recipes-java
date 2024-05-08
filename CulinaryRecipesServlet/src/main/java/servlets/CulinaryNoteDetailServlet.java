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

import static utils.UrlPathHelper.*;

@WebServlet(CULINARY_NOTE_DETAIL)
public class CulinaryNoteDetailServlet extends HttpServlet {
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
        Long id = Long.parseLong(req.getParameter("idCulinaryNote"));
        CulinaryNoteDto culinaryNote = culinaryNoteService.getById(id);
        if (culinaryNote == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Culinary note not found");
            return;
        }
        culinaryNote.setIngredientsInCulinaryNote(ingredientInCulinaryNoteService
                    .getAllByCulinaryNoteId(culinaryNote.getIdCulinaryNote()));

        req.setAttribute("culinaryNote", culinaryNote);
        req.getRequestDispatcher(JspHelper.get("culinaryNoteDetail")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");

        switch (action) {
            case "viewAll":
                resp.sendRedirect(req.getContextPath() + CULINARY_NOTES);
                break;
            case "delete":
                Long id = Long.parseLong(req.getParameter("idCulinaryNote"));
                culinaryNoteService.delete(id);
                break;
            case "update":
                resp.sendRedirect(req.getContextPath() + CULINARY_NOTE_UPDATE);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                break;
        }
        resp.sendRedirect(req.getContextPath() + CULINARY_NOTE_DETAIL);
    }
}
