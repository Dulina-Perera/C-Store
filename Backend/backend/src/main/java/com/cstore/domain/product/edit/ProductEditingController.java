package com.cstore.domain.product.edit;

import com.cstore.model.product.Property;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Tag(name = "Edit Products")
@RestController
@RequestMapping(path = "/api/v1/products/edit")
@RequiredArgsConstructor
public class ProductEditingController {
    private final ProductEditingService productEditingService;

    @Operation(
        description = """
            Should be invoked as the first step of adding a new product.
            Just creates a bare product with no properties & a default variant.
            Returns the product identifier of the new product.""",
        method = "Add bare Product",
        responses = {
            @ApiResponse(
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Long.class)
                ),
                description = "Success",
                responseCode = "200"
            )
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        path = "/add/1"
    )
    public Long addBareProduct(
        @RequestBody(required = true)
        BareProduct toAdd
    ) throws DataAccessException {
        return productEditingService.addBareProduct(toAdd);
    }

    @Operation(
        description = """
            Should be invoked as the second step of adding a new product.
            Defines the categories the newly added product belong to.""",
        method = "Define Categories",
        responses = {
            @ApiResponse(
                description = "Success",
                responseCode = "200"
            )
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        path = "/add/2/{product_id}"
    )
    public void defineCategories(
        @PathVariable(
            name = "product_id",
            required = true
        ) Long productId,
        @RequestBody(required = true)
        List<Long> categoryIds
    ) throws DataAccessException, SQLIntegrityConstraintViolationException {
        productEditingService.defineCategories(productId, categoryIds);
    }


    @Operation(
        description = """
            Should be invoked as the third step of adding a new product.
            Populates the product with properties by just adding the properties to the 'property' table.
            Returns the property identifiers along with property names & values as a map.""",
        method = "Populate with Properties",
        responses = {
            @ApiResponse(
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PropertyMap.class)
                ),
                description = "Success",
                responseCode = "200"
            )
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        path = "add/3"
    )
    public PropertyMap populateWithProperties(
        @RequestBody(required = true)
        List<Property> properties
    ) {
        return productEditingService.populateWithProperties(properties);
    }

    @Operation(
        description = """
            Should be invoked as the fourth step of adding a new product.
            Defines the variants of a product.
            Adds the variants to the 'variant' table.
            Adds relevant product-property-variant mappings to the 'varies_on' table.""",
        method = "Define Variants",
        responses = {
            @ApiResponse(
                content = @Content(
                    mediaType = "application/json"
                ),
                description = "Success",
                responseCode = "200"
            )
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        path = "add/4/{product_id}"
    )
    public void defineVariants(
        @PathVariable(
            name = "product_id",
            required = true
        ) Long productId,
        @RequestBody(required = true)
        List<Variant_> variantproperties
    ) {
        productEditingService.addVariants(productId, variantproperties);
    }
}
