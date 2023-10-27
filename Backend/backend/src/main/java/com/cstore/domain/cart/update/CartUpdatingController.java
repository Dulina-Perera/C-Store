package com.cstore.domain.cart.update;

import com.cstore.dto.VariantProperiesDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/carts/{user_id}")
@Tag(name = "Update Cart")
public class CartUpdatingController {

    private final CartUpdatingService cartUpdatingService;

    @Autowired
    public CartUpdatingController(CartUpdatingService cartUpdatingService) {
        this.cartUpdatingService = cartUpdatingService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "")
    public List<CartItemResponse> getItems(
        @PathVariable(name = "user_id", required = true)
        Long userId
    ) {
        return cartUpdatingService.getItems(userId);
    }

    @Operation(
        description = """
            Given a product, a set of properties & a count, checks whether the set of properties correspond to a unique variant of the product.
                If so, checks if there are enough such variants available.
                    If so, adds the variant to the user's cart & returns the variant identifier.
                    If not, throws a 'SparseStocksException' with the maximum allowable count.
                Otherwise, throws a 'NoSuchVariantException'.""",
        method = "addVariant",
        responses = {
            @ApiResponse(
                content = @Content(),
                description = "Success",
                responseCode = "200"
            )
        },
        summary = "Adds a variant to the cart."
    )
    @RequestMapping(method = RequestMethod.POST, path = "/add")
    public Long addVariant(
        @PathVariable(name = "user_id", required = true)
        Long userId,
        @RequestBody(required = true)
        VariantProperiesDto properties
    ) {

        return cartUpdatingService.addVariant(userId, properties);

    }

    @Operation(
        description = """
            Given a variant, checks whether it is present in the user's cart.
                If so, removes the variant from the user's cart.
                Otherwise, throws a 'NoSuchVariantException'.""",
        method = "removeVariant",
        responses = {
            @ApiResponse(
                content = @Content(),
                description = "Success",
                responseCode = "200"
            )
        },
        summary = "Removes a variant from the cart."
    )
    @RequestMapping(method = RequestMethod.PUT, path = "/update")
    public CartItemDto updateVariant(
        @PathVariable(name = "user_id", required = true)
        Long userId,
        @RequestBody(required = true)
        CartItemUpdateRequest request
    ) {
        return cartUpdatingService.updateVariant(userId, request);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/refresh")
    public List<CartItemDto> refresh(
        @PathVariable(name = "user_id", required = true) Long userId,
        @RequestBody(required = true) List<CartItemDto> cartItems
    ) {

        return cartUpdatingService.refresh(userId, cartItems);

    }

}
