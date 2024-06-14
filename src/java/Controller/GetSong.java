package Controller;

import Model.Song;
import Utils.Utils;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;

public class GetSong extends HttpServlet {

    Utils lib = new Utils();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = getServletContext().getRealPath("\\") + "\\songs";
        String songTitle = request.getParameter("selectSong");
        HttpSession session = request.getSession(false);
        
//        
//        if (songTitle == null || session.isNew()) {
//            session.setAttribute("songs", r);
//            System.out.println("Setting songs for sessions");
//        }
//        if (songTitle != null) {
//            try {
//                // Handle metadata shit
//                Song song = lib.readSongMetadata(desiredAudio, false, "");
//                request.setAttribute("song", song);
//            } catch (Exception e) {
//                request.setAttribute("error", e.getMessage());
//                request.getRequestDispatcher("error.jsp").forward(request, response);
//            }
//        }

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
