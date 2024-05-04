package servlets;

import dto.culinarynote.CulinaryNoteDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mappers.culinarynote.CreateCulinaryNoteMapper;
import mappers.culinarynote.CulinaryNoteMapper;
import mappers.culinarynote.UpdateCulinaryNoteMapper;
import repositories.CulinaryNoteRepository;
import services.CulinaryNoteService;
import utils.JspHelper;

import java.io.IOException;
import java.util.List;

import static utils.UrlPathHelper.CULINARY_NOTES;
import static utils.UrlPathHelper.CULINARY_NOTE_CREATE;
import static utils.UrlPathHelper.CULINARY_NOTE_UPDATE;

@WebServlet(CULINARY_NOTES)
public class CulinaryNotesServlet extends HttpServlet {
    private final CulinaryNoteService culinaryNoteService = new CulinaryNoteService(
            new CulinaryNoteRepository(),
            new CreateCulinaryNoteMapper(),
            new CulinaryNoteMapper(),
            new UpdateCulinaryNoteMapper()
    );

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CulinaryNoteDto> culinaryNotes = culinaryNoteService.getAll();
        req.setAttribute("culinaryNotes", culinaryNotes);
        req.getRequestDispatcher(JspHelper.get("culinaryNotes")).forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        switch (action) {
            case "create":
                resp.sendRedirect(req.getContextPath() + CULINARY_NOTE_CREATE);
                break;
            case "update":
                resp.sendRedirect(req.getContextPath() + CULINARY_NOTE_UPDATE);
                break;
            case "delete":
                Long id = Long.parseLong(req.getParameter("idUser"));
                culinaryNoteService.delete(id);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                return;
        }

        resp.sendRedirect(req.getContextPath() + CULINARY_NOTES);
    }
}
