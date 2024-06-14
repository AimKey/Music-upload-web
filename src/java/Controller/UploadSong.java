package Controller;

import Model.Song;
import Model.SongDao;
import Utils.Utils;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;

// Multipart config is moved to the Utils
public class UploadSong extends HttpServlet {

    Utils utils = new Utils();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    // Handle downloading music from user and save informations to database
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("multipart/form-data;charset=UTF-8");
        // This path should be: (...\WebMusicPlayer\build\web)
        String path = getServletContext().getRealPath("\\");

        String fileName = request.getPart("song").getSubmittedFileName();
        String fileNameWithoutExtension = utils.getFileNameWithoutExtension(fileName);
        
        // User upload song should be like this: something.mp3
        // The final path should be like this: (...\WebMusicPlayer\build\web\songs\something\something.mp3)
        String fullPath = path + "\\songs" + "\\" + fileNameWithoutExtension;
        String songPath = fullPath + "\\" + fileName;
        String imagePath = fullPath +  "\\" + fileNameWithoutExtension + ".jpeg";
        
        System.out.println("Fullpath: " + fullPath);
        System.out.println("songPath: " + songPath);
        System.out.println("imagePath: " + imagePath);
        
        // Make a folder that will contain the song and its coresponding image
        Song s = null;
        try {
            utils.setupSongFolder(songPath, request.getParts());
            File f = new File(songPath);
            s = utils.readSongMetadata(f, true, imagePath);
            
            // Setting the song folder
            // It should be like TestingUploadFiles\songs\something
            // This is because we be able to access the audio and the img as the same time
            s.setSongFolder("songs" + "\\" + fileNameWithoutExtension + "\\" + fileNameWithoutExtension);
            
            // Make SQL call
            boolean status = new SongDao().save(s);
            System.out.println("Status code: " + status);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
        
        // Dispatch
        request.setAttribute("status", fileName + " saved at " + s != null ? s.getSongFolder() : "Empty song");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
