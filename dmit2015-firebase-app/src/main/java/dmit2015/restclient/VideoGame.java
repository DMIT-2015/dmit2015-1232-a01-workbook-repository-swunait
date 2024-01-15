package dmit2015.restclient;

/**
 * This data class contains information on VideoGame data.
 *
 * @author Sam Wu
 * @version 2024.01.15
 */
public class VideoGame {
    // Define data fields for storing data
    private String genre;
    private String title;
    private String platform;
    // Create Getters/Setters to encapsulate access to data fields

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
