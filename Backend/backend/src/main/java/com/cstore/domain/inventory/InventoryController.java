package com.cstore.domain.inventory;

import com.cstore.domain.product.edit.ProductAddRequest;
import com.cstore.model.warehouse.Inventory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

@Tag(name = "Update Inventory")
@RestController
@RequestMapping(path = "/api/v1/variants")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService serv;

    @Operation(
        description = """
            Stocks a given count of a given variant to a given warehouse.
            The warehouse may or may not have that variant initially.
            Returns the updated status of the inventory.""",
        method = "Stock",
        responses = {
            @ApiResponse(
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Inventory.class)
                ),
                description = "Success",
                responseCode = "200"
            )
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        path = "/stock"
    )
    public Inventory stock(
        @RequestBody(required = true)
        Inventory newStock
    ) throws DataAccessException, SQLIntegrityConstraintViolationException {
        return serv.stock(newStock);
    }
}
