package Utils;

// @author phamm
import Model.Song;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.KeyNotFoundException;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.images.Artwork;

public class Utils {

    public void setupSongFolder(String fullPath, Collection<Part> parts) throws IOException {

        // Extract the directory path from the full path
        File directory = new File(fullPath).getParentFile();

        // Ensure the directory exists
        if (!directory.exists()) {
            // Create the directory and any necessary but nonexistent parent directories
            Files.createDirectories(Paths.get(directory.getAbsolutePath()));
        }

        // Write each part to the full path
        for (Part part : parts) {
            part.write(fullPath);
        }
    }

    public Song readSongMetadata(File f, boolean getImage, String destPath) throws Exception {
        try {
            // Read metadata from the song file using JAudioTagger library
            AudioFile audioFile = AudioFileIO.read(f);
            Tag tag = audioFile.getTag();

            // Extract metadata fields
            String title = tag.getFirst(FieldKey.TITLE);
            String artist = tag.getFirst(FieldKey.ARTIST);
            String album = tag.getFirst(FieldKey.ALBUM);
            int duration = audioFile.getAudioHeader().getTrackLength();

            // Extract cover image by using binaries wow
            byte[] coverImageData;
            Artwork imageData = tag.getFirstArtwork();
            coverImageData = imageData.getBinaryData();

            // IMPORTANT: The path should also contain the name of the file.
            // EX: path/to/file/img.png
            if (getImage && coverImageData != null) {
                try (FileOutputStream fos = new FileOutputStream(destPath)) {
                    fos.write(coverImageData);
                }
            } else {
                destPath = "";
            }

            // Create and return Song object
            return new Song(title, artist, album, duration, destPath);
        } catch (IOException | CannotReadException | InvalidAudioFrameException | ReadOnlyFileException | KeyNotFoundException | TagException e) {
            throw new Exception(e.getMessage());
        }

    }

    public String getFileNameWithoutExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return fileName;
        }
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(0, dotIndex);
        }
        return fileName; // In case there is no dot in the filename
    }

}
