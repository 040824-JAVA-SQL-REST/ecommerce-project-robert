package com.revature.ecommercep0.service;

import java.util.ArrayList;
import java.util.List;

import com.revature.ecommercep0.dao.ProductDao;
import com.revature.ecommercep0.dto.request.ProductUpdateRequest;
import com.revature.ecommercep0.model.Product;

public class ProductService {
    private ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> findAll() {
        return productDao.findAll();
    }

    public List<Product> getAllAvailableProductsCatalog() {
        List<Product> allProducts = productDao.findAll();
        List<Product> onlyAvailableProducts = new ArrayList<>();
        for (Product product : allProducts) {
            if (product.isAvailable()) {
                onlyAvailableProducts.add(product);
            }
        }
        return onlyAvailableProducts;
    }

    public Product save(Product obj) {
        return productDao.save(obj);
    }

    public Product enterNewProductIntoCatalog(String name, String description, String price, String category) {
        Product isFound = productDao.findByName(name);
        if (isFound != null) {
            if (retrieveDeletedProduct(name)) {
                isFound.setAvailable(true);
                return isFound;
            }
        }
        Product newProduct = new Product(name, description, price, category);

        if (isUniqueProductName(name)) {
            System.out.println("Product entered!");
            return productDao.save(newProduct);
        }
        return null;
    }

    public Product updateProductColumnById(String columnName, String newColumnValue, Product product) {
        return productDao.updateProductColumnById(columnName, newColumnValue, product);
    }

    public Product updateProduct(String productId, ProductUpdateRequest updateRequest) {
        Product existingProduct = productDao.findById(productId);

        if (existingProduct == null) {
            throw new RuntimeException("Product not found with ID: " + productId);
        }

        existingProduct.setName(updateRequest.getName());
        existingProduct.setDescription(updateRequest.getDescription());
        existingProduct.setPrice(updateRequest.getPrice());
        existingProduct.setCategory(updateRequest.getCategory());

        // Call the DAO method to update the product in the database
        return productDao.update(existingProduct);
    }

    public boolean retrieveDeletedProduct(String name) {
        return productDao.updateProductAvailabilityByName(name, true);
    }

    public boolean deleteProductFromCatalog(String product_id) {
        return productDao.updateProductAvailabilityById(product_id, false);
    }

    public Product findProductById(String product_id) {
        return productDao.findById(product_id);
    }

    public boolean isUniqueProductName(String name) {
        if (productDao.findByName(name) != null && !productDao.findByName(name).isAvailable()) {
            return true;
        } else if (productDao.findByName(name) != null && productDao.findByName(name).isAvailable()) {
            return false;
        }

        return false;
    }

    public double getTotalProductPrice(String product_id, String quantity) {
        return CartService.convertCostStrToInt(quantity)
                * CartService.convertCostStrToInt(findProductById(product_id).getPrice());

    }
}
