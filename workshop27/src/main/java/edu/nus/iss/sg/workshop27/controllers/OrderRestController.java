package edu.nus.iss.sg.workshop27.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.nus.iss.sg.workshop27.exception.OrderTooLargeException;
import edu.nus.iss.sg.workshop27.model.PurchaseOrder;
import edu.nus.iss.sg.workshop27.service.OrderService;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
@RequestMapping(path="/api/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderRestController {
    
    @Autowired
    private OrderService poSvc;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postOrder(@RequestBody String jsonString) {
        PurchaseOrder po = null;
        JsonObject resp;

        try {
            po = PurchaseOrder.create(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
            resp = Json.createObjectBuilder().add("error", e.getMessage()).build();
            return ResponseEntity.badRequest().body(resp.toString());
        }

        Integer orderId;

        try {
            orderId = poSvc.createPurchaseOrder(po);
            resp = Json.createObjectBuilder().add("orderId", orderId).build();
        } catch (OrderTooLargeException e) {
            e.printStackTrace();
            resp = Json.createObjectBuilder().add("error", e.getMessage()).build();
            return ResponseEntity.badRequest().body(resp.toString());
        }

        return ResponseEntity.ok().body(resp.toString());
    }
    
}
