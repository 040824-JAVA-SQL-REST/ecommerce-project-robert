package com.revature.ecommercep0.service;

import java.util.ArrayList;
import java.util.List;

import com.revature.ecommercep0.dao.ProductDao;
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
        Product newProduct = new Product(name, description, price, category);
        if (productDao.findByName(name) == null) {
            System.out.println("Product entered!");
            return productDao.save(newProduct);
        }
        return null;
    }
    public Product updateProductColumnByName(String column, String newColumnValue, String name) {
        //TODO: make conditiosn to check with column
        if (column.equals("category")) {
            return productDao.updateProductCategoryByName(newColumnValue, name);
        }
        else if (column.equals("price")) {
            return productDao.updateProductPriceByName(newColumnValue, name);
        }
        return productDao.updateProductColumnByName(column, newColumnValue, name);
    }
    public Product updateProductDescriptionByName(String name, String newDescription) {
        return productDao.updateProductDescriptionByName(name, newDescription);
    }

    public void updateProductNameByName(String name, String newProductName) {
       productDao.updateProductNameByName(name, newProductName);   
    }
    public Product deleteProductFromCatalog(Product obj) {
        obj.setAvailable(false);
        return productDao.save(obj);  
    }
    public void retrieveDeletedProduct(String name ) {
        productDao.updateProductAvailabilityByName(name, true);
    } 
    public void deleteProductFromCatalog(String name) {
        productDao.updateProductAvailabilityByName(name, false);
    }
    public Product findProductById(String product_id) {
        return productDao.findById(product_id);
    }
    public double getTotalProductPrice(String product_id, String quantity) {
        return CartService.convertCostStrToInt(quantity) * CartService.convertCostStrToInt(findProductById(product_id).getPrice());

   }
}
