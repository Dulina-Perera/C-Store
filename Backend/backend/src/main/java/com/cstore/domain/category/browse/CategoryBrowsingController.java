package com.cstore.domain.category.browse;

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
        method = "getAllBaseCategories",
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
    @RequestMapping(method = RequestMethod.GET, path = "/base")
    public List<Category> getAllRootCategories() {
        return categoryBrowsingService.getAllRootCategories();
    }

    @Operation(
        method = "getAllDirectSubCategories",
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
    @RequestMapping(method = RequestMethod.GET, path = "/{id}/sub")
    public List<Category> getAllDirectSubCategories(@PathVariable(name = "id", required = true) Long categoryId) throws SQLException {
        return categoryBrowsingService.getAllDirectSubCategories(categoryId);
    }

    @Operation(
            method = "getAllProductsBelongingToCategory",
            responses = {
                    @ApiResponse(
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ProductDto.class))
                            ),
                            description = "Success",
                            responseCode = "200"
                    )
            },
            summary = "Returns all products (with properties of non-monetary value) belonging to a category, given the category identifier."
    )
    @RequestMapping(method = RequestMethod.GET, path = "/{id}/products")
    public List<ProductDto> getAllProductsBelongingToCategory(@PathVariable(name = "id") Long categoryId) throws SQLException {
        return categoryBrowsingService.getAllProductsBelongingToCategory(categoryId);
    }
}
