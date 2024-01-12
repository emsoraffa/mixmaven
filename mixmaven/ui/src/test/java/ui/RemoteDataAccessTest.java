package ui;

import core.Drink;
import core.Ingredient;
import core.MixMavenModel;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.google.gson.Gson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the RemoteDataAccess class.
 */
@SuppressWarnings("magicnumber")
public class RemoteDataAccessTest {

    private WireMockConfiguration config;
    private WireMockServer wireMockServer;

    private RemoteDataAccess remoteDataAccess;

    /**
     * Starts the WireMock server and sets up the RemoteDataAccess for testing.
     * @throws URISyntaxException If there is an issue with the URI syntax.
     */
    @BeforeEach
    public void startWireMockServerAndSetup() throws URISyntaxException {
        config = WireMockConfiguration.wireMockConfig().port(8000);
        wireMockServer = new WireMockServer(config.portNumber());
        wireMockServer.start();
        WireMock.configureFor("localhost", config.portNumber());
        remoteDataAccess = new RemoteDataAccess(new URI("http://localhost:" + wireMockServer.port() + "/drinks"));
    }

    /**
     * Tests the getDrinks method of RemoteDataAccess.
     */
    @Test
    public void testgetDrinks() {
        Gson gson = new Gson();
        MixMavenModel mixMavenModelToBeReturned = new MixMavenModel(
                List.of(new Drink("TestDrink", List.of(
                        new Ingredient(
                                "TestIngredient", 1.0, "cl", "alcohol")),
                        "1dcc373c-5e15-4e41-accc-00fd2cb01103")));

        stubFor(get(urlEqualTo("/drinks"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(gson.toJson(mixMavenModelToBeReturned.getDrinks()))
                )
        );
        MixMavenModel mixMavenModel = new MixMavenModel(remoteDataAccess.getDrinks());
        assertEquals(1, mixMavenModel.getDrinks().size());
        assertEquals(1,
                mixMavenModel.getDrinks()
                        .stream()
                        .mapToInt(drink -> drink.getIngredients()
                                .size())
                        .sum());
        Drink drink = mixMavenModel.getDrinks().get(0);
        assertTrue(drink.getId().equals("1dcc373c-5e15-4e41-accc-00fd2cb01103"));
        assertTrue(drink.getName().equals("TestDrink"));
        assertTrue(drink.getIngredients().get(0).getName().equals("TestIngredient"));
    }

    /**
     * Stops the WireMock server after each test.
     */
    @AfterEach
    public void stopWireMockServer() {
        wireMockServer.stop();
    }
}
