package Model;

// @author phamm
import java.io.Serializable;

public class Song implements Serializable {

    private int id;
    private String title, artists, album;
    private int duration;
    private String songFolder;

    public Song() {
    }

    public Song(int id, String title, String artists, String album, int duration, String songFolder) {
        this.id = id;
        this.title = title;
        this.artists = artists;
        this.album = album;
        this.duration = duration;
        this.songFolder = songFolder;
    }

    public Song(String title, String artists, String album, int duration, String coverImgPath) {
        this.title = title;
        this.artists = artists;
        this.album = album;
        this.duration = duration;
        this.songFolder = coverImgPath;
    }

    @Override
    public String toString() {
        return "Song{" + "title=" + title + ", artists=" + artists + ", album=" + album + ", duration=" + duration + ", coverImg=" + songFolder + '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtists() {
        return artists;
    }

    public void setArtists(String artists) {
        this.artists = artists;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getSongFolder() {
        // Replace backward slashes with forward slashes
        String replacedSlashes = songFolder.replaceAll("\\\\", "/");
//
//        // Replace spaces with %20
//        String replacedSpaces = replacedSlashes.replaceAll(" ", "%20");
        return replacedSlashes;
    }
    


    public void setSongFolder(String songFolder) {
        this.songFolder = songFolder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
