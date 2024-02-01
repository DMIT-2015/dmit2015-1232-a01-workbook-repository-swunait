package dmit2015.restclient;

import jakarta.validation.constraints.NotBlank;

/**
 * This data class contains information on VideoGame data.
 *
 * @author Sam Wu
 * @version 2024.01.15
 */
public class VideoGame {
    // Define data fields for storing data
    @NotBlank(message = "Genre cannot be blank.")
    private String genre;

    @NotBlank(message = "Title cannot be blank.")
    private String title;

    @NotBlank(message = "Platform cannot be blank.")
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
