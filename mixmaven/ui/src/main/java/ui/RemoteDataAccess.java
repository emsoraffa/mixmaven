package ui;

import core.Drink;
import core.MixMavenModel;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * A class that provides remote data access to a MixMaven application through a RESTful API.
 */
public class RemoteDataAccess implements DataAccess {
    private final URI endpointBaseUri;
    private static final String APPLICATION_JSON = "application/json";
    private static final String ACCEPT_HEADER = "Accept";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private MixMavenModel mixMavenModel;
    private Gson gson;

    /**
     * Constructs a RemoteDataAccess object with the specified endpoint base URI.
     * @param endpointBaseUri The base URI of the remote MixMaven API.
     */
    public RemoteDataAccess(URI endpointBaseUri) {
        this.endpointBaseUri = endpointBaseUri;
        this.gson = new Gson();
    }

    /**
     * Retrieves the MixMavenModel from the remote API.
     * @return The MixMavenModel retrieved from the remote API, or null if an error occurs.
     */
    public final MixMavenModel getModel() {
        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri)
        .header(ACCEPT_HEADER, APPLICATION_JSON)
        .GET()
        .build();
        try {
            final HttpResponse<String> response = HttpClient
            .newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString());
            this.mixMavenModel = new MixMavenModel(fromJson(response.body()));
        } catch (ConnectException e) {
            return null;
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
        return mixMavenModel;
    }

    /**
     * @return A list of the drinks in the model.
     */
    public List<Drink> getDrinks() {
        return getModel().getDrinks();
    }

    /**
     * Sends a DELETE request to the remote API to delete a drink with the specified ID.
     * @param id The ID of the drink to be deleted.
     */
    public final void deleteDrink(String id) {
        HttpRequest request = HttpRequest.newBuilder(drinkUri(id))
          .header(ACCEPT_HEADER, APPLICATION_JSON)
          .DELETE()
          .build();
        try {
            HttpClient
            .newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sends a POST request to the remote API to add a new drink.
     * @param drink The Drink object to be added to the remote API.
     */
    public final void addDrink(Drink drink) {
        String json = toJson(drink);
        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri)
          .header(ACCEPT_HEADER, APPLICATION_JSON)
          .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
          .POST(BodyPublishers.ofString(json))
          .build();

        try {
            HttpClient
            .newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sends a PUT request to the remote API to edit an existing drink with the specified ID.
     * @param id The ID of the drink to be edited.
     * @param newDrink The updated Drink object to replace the old drink.
     */
    public final void editDrink(String id, Drink newDrink) {
        String json = toJson(newDrink);
        HttpRequest request = HttpRequest.newBuilder(drinkUri(id))
          .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
          .PUT(BodyPublishers.ofString(json))
          .build();

        try {
            HttpClient
            .newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets the file path for data storage.
     * @param filename The file path (not used in this implementation).
     */
    public void setFilePath(String filename) {
        HttpRequest request = HttpRequest.newBuilder(setFileUri())
          .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
          .POST(BodyPublishers.ofString(filename))
          .build();

        try {
            HttpClient
            .newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private String uriParam(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }

    private List<Drink> fromJson(String responsebody) {
        Type listType = new TypeToken<List<Drink>>() { }.getType();
        return gson.fromJson(responsebody, listType);
    }

    private String toJson(Drink drink) {
        return gson.toJson(drink);
    }

    private URI drinkUri(String id) {
        return endpointBaseUri.resolve(uriParam(id));
      }

    private URI setFileUri() {
        return endpointBaseUri.resolve("setfile");
    }
}
