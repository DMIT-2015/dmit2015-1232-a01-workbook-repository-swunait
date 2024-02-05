package dmit2015.faces;

import dmit2015.restclient.Todo;
import dmit2015.restclient.TodoAuthMpRestClient;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.json.JsonObject;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.util.Messages;
import org.primefaces.PrimeFaces;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

@Named("currentTodoCrudView")
@ViewScoped
public class TodoCrudView implements Serializable {

    @Inject
    @RestClient
    private TodoAuthMpRestClient _todoMpRestClient;

    @Getter
    private Map<String, Todo> todoMap;

    @Getter
    @Setter
    private Todo selectedTodo;

    @Getter
    @Setter
    private String selectedKey;

    @Inject
    private FirebaseLoginSession _firebaseLoginSession;

    @PostConstruct  // After @Inject is complete
    public void init() {
        String token = _firebaseLoginSession.getFirebaseUser().getIdToken();
        String userUID = _firebaseLoginSession.getFirebaseUser().getLocalId();
        try {
            todoMap = _todoMpRestClient.findAll(userUID, token);
        } catch (Exception ex) {
            Messages.addGlobalError(ex.getMessage());
        }
    }

    public void onOpenNew() {
        selectedTodo = new Todo();
    }

    public void onSave() {
        String token = _firebaseLoginSession.getFirebaseUser().getIdToken();
        String userUID = _firebaseLoginSession.getFirebaseUser().getLocalId();
        if (selectedKey == null) {
            try {
                // assign the current date time to the Todo task
                selectedTodo.setCreated(LocalDateTime.now());
                JsonObject responseBody = _todoMpRestClient.create(userUID, selectedTodo, token);
                String documentKey = responseBody.getString("name");
                Messages.addGlobalInfo("Create was successful. {0}", documentKey);
                todoMap = _todoMpRestClient.findAll(userUID, token);
            } catch (Exception e) {
                Messages.addGlobalError("Create was not successful. {0}", e.getMessage());
            }
        } else {
            try {
                _todoMpRestClient.update(userUID, selectedKey, selectedTodo, token);
                Messages.addFlashGlobalInfo("Update was successful.");
            } catch (Exception e) {
                Messages.addGlobalError("Update was not successful.");
            }
        }

        PrimeFaces.current().executeScript("PF('manageTodoDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-Todos");
    }

    public void onDelete() {
        String token = _firebaseLoginSession.getFirebaseUser().getIdToken();
        String userUID = _firebaseLoginSession.getFirebaseUser().getLocalId();
        try {
            _todoMpRestClient.delete(userUID, selectedKey, token);
            selectedTodo = null;
            Messages.addGlobalInfo("Delete was successful.");
            todoMap = _todoMpRestClient.findAll(userUID, token);
            PrimeFaces.current().ajax().update("form:messages", "form:dt-Todos");
        } catch (Exception e) {
            Messages.addGlobalError("Delete not successful.");
        }
    }

}