package Model;

// @author phamm
import java.util.Optional;
import java.sql.*;
import java.util.ArrayList;

public class SongDao implements Dao<Song> {

    DatabaseInformation db = new DatabaseInformation("KUUL", "TestingUploadFiles");

    @Override
    public Optional<Song> get(long id) {
        Song s = null;
        try (Connection con = db.getConnection()) {
            ResultSet rs;
            try (PreparedStatement stmt = con.prepareStatement(
                    "SELECT * "
                    + "FROM Song s "
                    + "WHERE s.id = ? ")) {
                stmt.setLong(1, id);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    s = new Song(rs.getInt("id"), rs.getString("title"), rs.getString("artists"),
                            rs.getString("album"), rs.getInt("duration"),
                            rs.getString("songFolder"));
                }
            }
            rs.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return Optional.ofNullable(s);
    }

    @Override
    public ArrayList<Song> getAll() {
        ArrayList<Song> songs = new ArrayList<>();
        try (Connection con = db.getConnection()) {
            ResultSet rs;
            try (Statement stmt = con.createStatement()) {
                rs = stmt.executeQuery("SELECT * FROM Song ");
                while (rs.next()) {
                    Song s = new Song(rs.getInt("id"),rs.getString("title"), rs.getString("artists"),
                            rs.getString("album"), rs.getInt("duration"),
                            rs.getString("songFolder"));
                    songs.add(s);
                }
            }
            rs.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return songs;
    }

    @Override
    public boolean save(Song t) {
        boolean result = false;
        try (Connection con = db.getConnection()) {
            ResultSet rs;
            try (PreparedStatement stmt = con.prepareStatement(
                    "INSERT INTO Song (title, artists, album, duration, songFolder) "
                    + "OUTPUT inserted.title "
                    + "VALUES (?,?,?,?,?) ")) {
                stmt.setString(1, t.getTitle());
                stmt.setString(2, t.getArtists());
                stmt.setString(3, t.getAlbum());
                stmt.setInt(4, t.getDuration());
                stmt.setString(5, t.getSongFolder());
                rs = stmt.executeQuery();
                while (rs.next()) {
                    result = rs.getRow() >= 0;
                }
            }
            rs.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return result;
    }

    /**
     * Please follow this order for params: id,title,artists,album,songFolder
     * IMPORTANT ALL FIELDS MUST BE FILLED, EMPTY ONE WILL BE THIS ""
     *
     * @param t
     * @param params
     * @return
     */
    @Override
    public boolean update(Song t, String[] params) {
        boolean result = false;
        try (Connection con = db.getConnection(); PreparedStatement stmt = con.prepareStatement(
                "UPDATE Song "
                + "SET s.title = ?, s.artists = ?, s.album = ?, s.songFolder = ? "
                + "FROM Song  s"
                + "WHERE t.id = ? ")) {
            stmt.setString(1, params[1]);
            stmt.setString(2, params[2]);
            stmt.setString(3, params[3]);
            stmt.setString(4, params[4]);
            stmt.setString(5, params[0]); // Id is at the start
            result = stmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    @Override
    public boolean delete(Song t) {
        boolean result = false;
        try (Connection con = db.getConnection()) {
            ResultSet rs;
            try (PreparedStatement stmt = con.prepareStatement(
                    "DELETE FROM Song "
                    + "OUTPUT deleted.id "
                    + "WHERE id = ? "
            )) {
                stmt.setInt(1, t.getId());
                rs = stmt.executeQuery();
                while (rs.next()) {
                    result = rs.getRow() >= 0; // If > 0 mean there is an id
                }
            }
            rs.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

}
