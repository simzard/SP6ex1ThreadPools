/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entities.Group;
import java.util.concurrent.ExecutionException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import scrape.Scraper;

/**
 * REST Web Service
 *
 * @author simon
 */
@Path("group")
public class GroupResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GroupResource
     */
    public GroupResource() {
    }

    /**
     * Retrieves representation of an instance of rest.GroupResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json; charset=UTF-8")
    public String getGroups() throws InterruptedException, ExecutionException {
        //TODO return proper representation object
        JsonArray ja = new JsonArray();

        for (Group g : Scraper.beginScrape()) {
            JsonObject jo = new JsonObject();
            jo.addProperty("authors", g.getAuthors());
            jo.addProperty("class", g.getClassDescr());
            jo.addProperty("groupNo", g.getGroupNo());
            ja.add(jo);
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(ja.toString());
        String prettyJsonString = gson.toJson(je);

        return prettyJsonString;

    }

}
