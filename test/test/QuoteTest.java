package test;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.http.ContentType.JSON;
import static com.jayway.restassured.matcher.RestAssuredMatchers.*;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.path.json.config.JsonPathConfig;
import com.jayway.restassured.response.Response;
import facade.Facade;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.ws.rs.client.Entity.json;
import static org.hamcrest.Matchers.*;
import org.junit.After;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * @author Tobias Jacobsen
 */
public class QuoteTest {

    /*
     GET: get a quote using id
     GET: get a random quote (how is this tested?)
     POST: add a quote and test returned 
    
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        baseURI = "http://localhost:8080";
        defaultParser = Parser.JSON;
        basePath = "/RESTwithJAX_RS/api/quote";
    }
    
    @After
    public void before() {
        Facade.resetQuotes();
    }

    @Test
    public void testGet() {
        System.out.println("##testGet##");
        when().
                get("/1").
                then().
                statusCode(200).body("quote", equalTo("Friends are kisses blown to us by angels"));

        when().
                get("/3").
                then().
                statusCode(200).body("quote", equalTo("Behind every great man, is a woman rolling her eyes"));
    }
    
    @Test
    public void testPut() {
        System.out.println("##testPut##");
        String JSONRequest = "{\"quote\": \"test quote\"}";
        Response res = given().
                contentType(JSON).
                body(JSONRequest).
                with().
                authentication().basic("test", "test").
                when().
                put("/2");

        String json = res.asString();
        JsonPath jp = new JsonPath(json);
        assertEquals("test quote", jp.get("quote"));
    }

    @Test
    public void testDelete() {
        System.out.println("##testDelete##");
        given().with().authentication().basic("test", "test").
                when().
                delete("/3").
                then().
                statusCode(200).body("quote", equalTo("Behind every great man, is a woman rolling her eyes"));
    }

    @Test
    public void testGetJson() {
        System.out.println("##testGetJson##");
        Response res = get("/1");
        assertEquals(200, res.getStatusCode());
        String json = res.asString();
        JsonPath jp = new JsonPath(json);
        assertEquals("Friends are kisses blown to us by angels", jp.get("quote"));
    }

    @Test
    public void testPOST_Authenticated() {
        System.out.println("##testPost##");
        String JSONRequest = "{\"quote\": \"test quote\"}";
        Response res = given().
                contentType(JSON).
                body(JSONRequest).
                with().
                authentication().basic("test", "test").
                when().
                post("");

        String json = res.asString();
        JsonPath jp = new JsonPath(json);
        assertEquals("test quote", jp.get("quote"));
    }

    @Test
    public void testPOST_NotAuthenticated() {
        System.out.println("##testAuthenticated##");
        String JSONRequest = "{\"quote\": \"test quote\"}";
        Response res = given().
                contentType(JSON).
                body(JSONRequest).
                when().
                post("");

        assertEquals(401, res.getStatusCode());
    }
}
