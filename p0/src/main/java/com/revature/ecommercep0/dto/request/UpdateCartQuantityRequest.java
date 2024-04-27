package com.revature.ecommercep0.dto.request;

public class UpdateCartQuantityRequest {
    private String productId;
    private String quantity;

    public UpdateCartQuantityRequest() {
    }

    public UpdateCartQuantityRequest(String productId, String quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
