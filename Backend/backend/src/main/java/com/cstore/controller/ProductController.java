package com.cstore.controller;

import com.cstore.dto.NewProductDto;
import com.cstore.dto.ProductDto;
import com.cstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping(path = "api/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "")
    public List<ProductDto> getAllProducts() throws SQLException {
        return productService.getAllProducts();
    }
}
