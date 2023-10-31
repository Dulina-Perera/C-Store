package com.cstore.domain.category.browse;

import com.cstore.dto.product.ProductCard;
import com.cstore.model.category.Category;
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
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/categories/browse")
@Tag(name = "Browse Categories", description = "Provides controller methods for browsing categories.")
public class CategoryBrowsingController {
    private final CategoryBrowsingService categoryBrowsingService;

    @Operation(
        method = "Get Root Categories",
        responses = {
            @ApiResponse(
                content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Category.class))
                ),
                description = "Success",
                responseCode = "200"
            )
        },
        summary = "Returns all the base categories."
    )
    @RequestMapping(method = RequestMethod.GET, path = "/root")
    public List<Category> getRootCategories(
    ) {

        return categoryBrowsingService.getRootCategories();

    }

    @Operation(
        method = "Get Child Categories",
        responses = {
            @ApiResponse(
                 content = @Content(
                      mediaType = "application/json",
                      array = @ArraySchema(schema = @Schema(implementation = Category.class))
                 ),
            description = "Success",
            responseCode = "200"
            )
        },
        summary = "Returns all sub categories, given the category identifier."
    )
    @RequestMapping(method = RequestMethod.GET, path = "/{category_id}/child")
    public List<Category> getChildCategories(
        @PathVariable(name = "category_id", required = true)
        Long categoryId
    ) {

        return categoryBrowsingService.getChildCategories(categoryId);

    }

    @Operation(
	    method = "Get Products by Category",
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
        summary = "Returns all products (with properties of non-monetary value) belonging to a category, given the category identifier."
    )
    @RequestMapping(method = RequestMethod.GET, path = "/{category_id}/products")
    public List<ProductCard> getProductsByCategory(
        @PathVariable(name = "category_id")
        Long categoryId
    ) {
        return categoryBrowsingService.getProductsByCategory(categoryId);
    }
}
