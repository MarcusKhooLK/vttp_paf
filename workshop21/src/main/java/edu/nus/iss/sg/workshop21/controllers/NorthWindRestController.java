package edu.nus.iss.sg.workshop21.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.nus.iss.sg.workshop21.services.NorthWindService;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

@RestController
@RequestMapping(path="/api")
public class NorthWindRestController {
    
    @Autowired
    private NorthWindService northWindSvc;
    
    @GetMapping(path="/customers", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllCustomers(@RequestParam(defaultValue = "0") String offset, 
        @RequestParam(defaultValue = "5") String limit) {

            JsonArray array = northWindSvc.getAllCustomers(limit, offset);

            if(array.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok().body(array.toString());
    }

    @GetMapping(path="/customer/{customerId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCustomerById(@PathVariable String customerId) {
        Optional<JsonObject> customerOpt = northWindSvc.getCustomerDetailsById(customerId);

        if(customerOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        JsonObject customer = customerOpt.get();

        return ResponseEntity.ok().body(customer.toString());
    }

    @GetMapping(path="/customer/{customerId}/orders", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getOrdersByCustomerId(@PathVariable String customerId) {

        final JsonArray result = northWindSvc.getOrdersByCustomerId(customerId);
        return ResponseEntity.ok().body(result.toString());
        
    }
}
