package com.cstore.domain.product.browse;

import com.cstore.dto.product.ProductCard;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/products/browse")
@RequiredArgsConstructor
@Tag(name = "Browse Products")
public class ProductBrowsingController {
    private final ProductBrowsingService productBrowsingService;

    // TODO: Implement pagination., Enhance documentation.
    @Operation(
        method = "Get Products",
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
    public ResponseEntity<List<ProductCard>> getProducts(
    ) {
        try {
            return new ResponseEntity<>(
                productBrowsingService.getProducts(),
                HttpStatus.OK
            );
        } catch (DataAccessException dae) {
            return new ResponseEntity<>(
                null,
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    // TODO: Implement pagination., Enhance documentation.
    @Operation(
            method = "Get Products by Name",
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
    @RequestMapping(method = RequestMethod.GET, path = "/{query}")
    public ResponseEntity<List<ProductCard>> getProductsByName(
        @PathVariable(
            name = "query",
            required = true
        )
        String productName
    ) {
        try {
            return new ResponseEntity<>(
                productBrowsingService.getProductsByName(productName),
                HttpStatus.OK
            );
        } catch (DataAccessException dae) {
            return new ResponseEntity<>(
                null,
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

}
