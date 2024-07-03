package Controller;

import Model.Song;
import Model.SongDao;
import Utils.Utils;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;

public class GetSong extends HttpServlet {

    Utils lib = new Utils();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = getServletContext().getRealPath("\\") + "\\songs";
        String songTitle = request.getParameter("selectSong");
        HttpSession session = request.getSession(false);
        ArrayList<Song> ss = new SongDao().getAll();
        // Forward
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
