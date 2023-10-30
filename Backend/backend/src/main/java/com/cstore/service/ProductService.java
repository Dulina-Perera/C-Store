package com.cstore.service;

import com.cstore.dao.product.ProductDao;
import com.cstore.dao.varieson.VariesOnDao;
import com.cstore.dto.NewProductDto;
import com.cstore.dto.ProductDto;
import com.cstore.dto.PropertyDto;
import com.cstore.exception.NoSuchProductException;
import com.cstore.model.product.Product;
import com.cstore.model.product.VariesOn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductDao productDao;
    private final VariesOnDao variesOnDao;

    @Autowired
    public ProductService(ProductDao productDao, VariesOnDao variesOnDao) {
        this.productDao = productDao;
        this.variesOnDao = variesOnDao;
    }

    private Product getProduct(Product unknown) throws SQLException {
        Optional<Product> product = productDao.findProduct(unknown);

        return product.orElse(null);
    }

    private ProductDto convert(Product product) {
        List<VariesOn> variances = variesOnDao.findByProduct(product);
        ProductDto productDto = new ProductDto();
        List<PropertyDto> properties = new ArrayList<PropertyDto>();

        productDto.setProductId(product.getProductId());
        productDto.setProductName(product.getProductName());
        productDto.setBasePrice(product.getBasePrice());
        productDto.setBrand(product.getBrand());
        productDto.setDescription(product.getDescription());
        productDto.setMainImage(product.getMainImage());

        for (VariesOn variesOn : variances) {
            PropertyDto propertyDTO = new PropertyDto();

            propertyDTO.setPropertyId(variesOn.getProperty().getPropertyId());
            propertyDTO.setPropertyName(variesOn.getProperty().getPropertyName());
            propertyDTO.setValue(variesOn.getProperty().getValue());
            propertyDTO.setImageUrl(variesOn.getProperty().getImageUrl());
            propertyDTO.setPriceIncrement(variesOn.getProperty().getPriceIncrement());

            properties.add(propertyDTO);
        }
        productDto.setProperties(properties);

        return productDto;
    }

    public List<ProductDto> getAllProducts() throws SQLException {
        return productDao.findAll()
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());

    }

    public ProductDto getProductById(Long productId) throws SQLException {
        Optional<Product> product = productDao.findById(productId);

        if (product.isEmpty()) {
            throw new NoSuchProductException("WholeProduct with id " + productId + " not found.");
        }
        return convert(product.get());
    }

    public List<ProductDto> getProductByName(String productName) throws SQLException {
        return productDao.findByName(productName)
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    public ProductDto addNewProduct(NewProductDto newProduct) throws SQLException {
        Product product = new Product();

        product.setProductName(newProduct.getProductName());
        product.setBasePrice(newProduct.getBasePrice());
        product.setBrand(newProduct.getBrand());
        product.setDescription(newProduct.getDescription());
        product.setMainImage(newProduct.getImageUrl());

        productDao.save(product);
        product.setProductId(getProduct(product).getProductId());
        return null;
    }
}
