package com.cstore.domain.product.browse;

import com.cstore.dto.product.ProductCard;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/products/browse")
@Tag(name = "Browse Products")

@RequiredArgsConstructor
public class ProductBrowsingController {
    private final ProductBrowsingService productService;

    @Operation(
        method = "getAllProducts",
        responses = {
           @ApiResponse(
               content = @Content(
                   mediaType = "application/json",
                   array = @ArraySchema(schema = @Schema(implementation = ProductCard.class))
               ),
               description = "Success",
               responseCode = "200"
           )
        },
        summary = "Returns all products (with properties of non-monetary value)."
    )
    @RequestMapping(method = RequestMethod.GET, path = "")
    public List<ProductCard> getAllProducts() throws SQLException {
        return productService.getAllProducts();
    }

    @Operation(
            method = "getProductsByName",
            responses = {
                    @ApiResponse(
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ProductCard.class))
                            ),
                            description = "Success",
                            responseCode = "200"
                    )
            },
            summary = "Returns all products (with properties of non-monetary value) matching a given name."
    )
    @RequestMapping(method = RequestMethod.GET, path = "/{product_name}")
    public List<ProductCard> getProductByName(@PathVariable(name = "product_name", required = true) String productName) throws SQLException {
        return productService.getProductByName(productName);
    }
}
