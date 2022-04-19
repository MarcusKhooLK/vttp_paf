package edu.nus.iss.sg.workshop21.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import edu.nus.iss.sg.workshop21.repository.NorthWindRepo;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

@Service
public class NorthWindService {
    
    @Autowired
    private NorthWindRepo repo;

    public JsonArray getAllCustomers(String limit, String offset) {
        Integer iLimit = Integer.parseInt(limit);
        Integer iOffset = Integer.parseInt(offset);
        
        final SqlRowSet result = repo.getAllCustomers(iLimit, iOffset);

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        while(result.next()) {
            JsonObject obj = Json.createObjectBuilder()
                            .add("id", result.getInt("id"))
                            .add("lastName", result.getString("last_name"))
                            .add("firstName", result.getString("first_name"))
                            .build();
            arrayBuilder.add(obj);
        }

        return arrayBuilder.build();
    }

    public Optional<JsonObject> getCustomerDetailsById(String id) {
        Integer iId = Integer.parseInt(id);
        final SqlRowSet result = repo.getCustomerById(iId);

        if(!result.next())
            return Optional.empty();
        
        JsonObject obj = Json.createObjectBuilder()
                            .add("id", iId)
                            .add("lastName", result.getString("last_name"))
                            .add("firstName", result.getString("first_name"))
                            .build();
        
        return Optional.of(obj);
    }

    public JsonArray getOrdersByCustomerId(String id) {
        Integer iId = Integer.parseInt(id);
        final SqlRowSet result = repo.getOrdersByCustomerId(iId);

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        while(result.next()) {
            JsonObject obj = Json.createObjectBuilder()
                .add("id", result.getInt("id"))
                .add("shipName", result.getString("ship_name"))
                .add("shipAddress", result.getString("ship_address"))
                .build();
            arrayBuilder.add(obj);
        }

        return arrayBuilder.build();
    }
}
