package com.cstore.domain.product.select;

import com.cstore.dto.SelectedProduct;
import com.cstore.exception.NoSuchProductException;
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

@RestController
@RequestMapping(path = "/api/v1/products/select")
@Tag(name = "Select Product", description = "Provides controller methods for selecting products.")
@RequiredArgsConstructor
public class ProductSelectionController {
    private final ProductSelectionService productSelectionService;

    @Operation(
        description = """
            Should be invoked when a product is selected.
            Returns all the necessary details of the product including its properties, categories it belongs to & stock count.""",
        method = "getProductById",
        responses = {
            @ApiResponse(
                content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = SelectedProduct.class))
                ),
                description = "Success",
                responseCode = "200"
            )
        }
    )
    @RequestMapping(method = RequestMethod.GET, path = "/{product_id}")
    public ResponseEntity<SelectedProduct> getProductById(
        @PathVariable(
            name = "product_id",
            required = true
        )
        Long productId
    ) {
        try {
            return new ResponseEntity<>(
                productSelectionService.getProductById(productId),
                HttpStatus.OK
            );
        } catch (DataAccessException dae) {
            return new ResponseEntity<>(
                null,
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        } catch (NoSuchProductException nspe) {
            return new ResponseEntity<>(
                null,
                HttpStatus.BAD_REQUEST
            );
        }
    }
}
