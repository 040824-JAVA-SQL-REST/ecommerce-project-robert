package com.revature.ecommercep0.dto.response;

public class ProductCatalogResponse {
    private String orderId;
    private String name;
    private String description;
    private String price;
    private String category;
    
    public ProductCatalogResponse() {
    }
    public ProductCatalogResponse(String orderId, String name, String description, String price, String category) {
        this.orderId = orderId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
    
}
