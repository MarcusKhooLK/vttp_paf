package edu.nus.iss.sg.workshop27.model;

import jakarta.json.JsonObject;

public class LineItem {
    
    private Integer lineItemId;
    private Integer qty;
    private Integer orderId;
    private String description;
    private Double unitPrice;

    public Integer getLineItemId() {
        return lineItemId;
    }
    public void setLineItemId(Integer lineItemId) {
        this.lineItemId = lineItemId;
    }
    public Integer getQty() {
        return qty;
    }
    public void setQty(Integer qty) {
        this.qty = qty;
    }
    public Integer getOrderId() {
        return orderId;
    }
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Double getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public static LineItem create(JsonObject obj) {
        LineItem li = new LineItem();
        li.setQty(obj.getInt("quantity"));
        li.setDescription(obj.getString("description"));
        li.setLineItemId(obj.getInt("itemId"));
        li.setUnitPrice(obj.getJsonNumber("unitPrice").doubleValue());
        return li;
    }
}
