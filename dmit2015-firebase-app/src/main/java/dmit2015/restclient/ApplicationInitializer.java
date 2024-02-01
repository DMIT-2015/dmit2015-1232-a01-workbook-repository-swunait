package dmit2015.restclient;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import net.datafaker.Faker;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class ApplicationInitializer {
    private final Logger _logger = Logger.getLogger(ApplicationInitializer.class.getName());

    @Inject
    @RestClient
    private VideoGameMpRestClient _gameRestClient;

    public void initialize(@Observes @Initialized(ApplicationScoped.class) Object event) {
        _logger.info("Preloading data");

        try {
            // Create a new Faker instance
            var faker = new Faker();
            var videogames = _gameRestClient.findAll();
            if (videogames == null || videogames.isEmpty()) {
                // Create 50 random VideoGames
                for (int count = 1; count <= 20; count++) {
                    // Create a new VideoGame instance
                    var newGame = new VideoGame();
                    // Generate a title, genre, and platform for the newGame
                    newGame.setPlatform(faker.videoGame().platform());
                    newGame.setGenre(faker.videoGame().genre());
                    newGame.setTitle(faker.videoGame().title());
                    // Post the newGame to the REST API endpoints for Firebase Realtime DB
                    _gameRestClient.create(newGame);
                }
            } else {
                if (videogames.size() >= 50) {
                    for (var currentKey : videogames.keySet()) {
                        _gameRestClient.delete(currentKey);
                    }
                }
            }

        } catch (Exception ex) {
            _logger.log(Level.FINE, ex.getMessage(), ex);
        }

    }
}