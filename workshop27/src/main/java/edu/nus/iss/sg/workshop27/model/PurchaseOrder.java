package edu.nus.iss.sg.workshop27.model;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class PurchaseOrder {

    private Integer orderId;
    private String email;
    private String name;
    private List<LineItem> lineItems = new ArrayList<>();

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static PurchaseOrder create(String jsonStr) throws Exception {
        JsonReader reader = Json.createReader(new StringReader(jsonStr));
        return create(reader.readObject());
    }

    public static PurchaseOrder create(JsonObject obj) {
        final PurchaseOrder po = new PurchaseOrder();

        po.setOrderId(obj.getInt("orderId"));
        po.setEmail(obj.getString("email"));
        po.setName(obj.getString("name"));

        JsonArray jArray = obj.getJsonArray("listItems");
        List<LineItem> lineItems = jArray.stream()
                .map(li -> LineItem.create((JsonObject) li))
                .toList();
        po.setLineItems(lineItems);
        
        return po;
    }
}
