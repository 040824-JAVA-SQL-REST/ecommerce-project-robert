package com.revature.p0.ecommerce.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.revature.ecommercep0.dao.ProductDao;
import com.revature.ecommercep0.dto.request.ProductUpdateRequest;
import com.revature.ecommercep0.model.Product;
import com.revature.ecommercep0.service.ProductService;

public class ProduceServiceTest {
    private ProductService productService;
    private ProductDao mockProductDao;

    @Before
    public void setUp() {
        mockProductDao = mock(ProductDao.class);
        productService = new ProductService(mockProductDao);
    }

    @Test
    public void getAllAvailableProductsCatalogReturnAllProductsIfAvailable() {
        List<Product> allProducts = new ArrayList<>();
        allProducts.add(new Product("Product1", "Description1", "100", "Category1"));
        allProducts.add(new Product("Product2", "Description2", "200", "Category2"));
        when(mockProductDao.findAll()).thenReturn(allProducts);

        List<Product> availableProducts = productService.getAllAvailableProductsCatalog();

        assertEquals(2, availableProducts.size());
        assertEquals("Product1", availableProducts.get(0).getName());
    }

    @Test
    public void saveProductReturnsProductIfSaved() {
        Product product = new Product("TestProduct", "TestDescription", "50", "TestCategory");
        productService.save(product);
        verify(mockProductDao).save(product);
    }

    @Test
    public void isUniqueProductNameReturnsTrueIfUnique() {
        String productName = "TestProduct";
        Product existingProduct = new Product(productName, "TestDescription", "50", "TestCategory");
        when(mockProductDao.findByName(productName)).thenReturn(existingProduct);
        boolean result = productService.isUniqueProductName(productName);
        assertFalse(result);
    }

    @Test
    public void isUniqueProductNameReturnsFalseIfWrong() {
        String productName = "TestProduct";
        Product existingProduct = new Product(productName, "TestDescription", "50", "TestCategory");
        existingProduct.setAvailable(true);
        when(mockProductDao.findByName(productName)).thenReturn(existingProduct);
        boolean result = productService.isUniqueProductName(productName);
        assertFalse(result);
    }

    @Test
    public void isUniqueProductNameReturnsFalseIfDoesntExist() {
        String productName = "NonExistingProduct";
        when(mockProductDao.findByName(productName)).thenReturn(null);
        boolean result = productService.isUniqueProductName(productName);
        assertFalse(result);
    }

    @Test
    public void findProductByIdReturnsProductIfExists() {
        String productId = "123";
        Product expectedProduct = new Product("TestProduct", "TestDescription", "50", "TestCategory");
        when(mockProductDao.findById(productId)).thenReturn(expectedProduct);
        Product actualProduct = productService.findProductById(productId);
        assertEquals(expectedProduct, actualProduct);
    }

    @Test
    public void findProductByIdReturnsNullIfNotFound() {
        String productId = "456";
        when(mockProductDao.findById(productId)).thenReturn(null);
        Product actualProduct = productService.findProductById(productId);
        assertEquals(null, actualProduct);
    }

    @Test
    public void retrieveDeletedProductReturnsTrueIfDeleted() {
        String productName = "TestProduct";
        when(mockProductDao.updateProductAvailabilityByName(productName, true)).thenReturn(true);
        boolean result = productService.retrieveDeletedProduct(productName);
        assertTrue(result);
        verify(mockProductDao).updateProductAvailabilityByName(productName, true);
    }

    @Test
    public void deleteProductFromCatalogReturnsTrueIfDeleted() {
        String productId = "123";
        when(mockProductDao.updateProductAvailabilityById(productId, false)).thenReturn(true);
        boolean result = productService.deleteProductFromCatalog(productId);
        assertTrue(result);
        verify(mockProductDao).updateProductAvailabilityById(productId, false);
    }

    @Test
    public void updateProductReturnsProductIfUpdatedSuccesfully() {
        String productId = "123";
        ProductUpdateRequest updateRequest = new ProductUpdateRequest();
        updateRequest.setName("UpdatedName");
        updateRequest.setDescription("UpdatedDescription");
        updateRequest.setPrice("UpdatedPrice");
        updateRequest.setCategory("UpdatedCategory");

        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setName("InitialName");
        existingProduct.setDescription("InitialDescription");
        existingProduct.setPrice("InitialPrice");
        existingProduct.setCategory("InitialCategory");

        when(mockProductDao.findById(productId)).thenReturn(existingProduct);
        when(mockProductDao.update(existingProduct)).thenReturn(existingProduct);

        Product updatedProduct = productService.updateProduct(productId, updateRequest);

        assertEquals(updateRequest.getName(), updatedProduct.getName());
        assertEquals(updateRequest.getDescription(), updatedProduct.getDescription());
        assertEquals(updateRequest.getPrice(), updatedProduct.getPrice());
        assertEquals(updateRequest.getCategory(), updatedProduct.getCategory());
        verify(mockProductDao).update(existingProduct);
    }

    @Test
    public void findAllReturnsAllIfNotEmpty() {
        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(new Product("Product1", "Description1", "10.99", "Category1"));
        expectedProducts.add(new Product("Product2", "Description2", "15.99", "Category2"));
        when(mockProductDao.findAll()).thenReturn(expectedProducts);

        List<Product> result = productService.findAll();

        assertEquals(expectedProducts.size(), result.size());
        assertEquals(expectedProducts, result);
    }

    @Test
    public void enterNewProductIntoCatalogReturnsProductIfSuccessful() {
        String name = "ExistingProduct";
        String description = "Test description";
        String price = "10.99";
        String category = "Test";

        when(mockProductDao.findByName(name)).thenReturn(new Product(name, description, price, category));

        Product result = productService.enterNewProductIntoCatalog(name, description, price, category);

        assertNull(result);
    }
}
