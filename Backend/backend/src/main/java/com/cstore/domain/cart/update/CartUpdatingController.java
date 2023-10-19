package com.cstore.domain.cart.update;

import com.cstore.dto.CartItem_;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/cart/{user_id}")
@Tag(name = "Update Cart")
public class CartUpdatingController {

    private final CartUpdatingService cartUpdatingService;

    @Autowired
    public CartUpdatingController(CartUpdatingService cartUpdatingService) {
        this.cartUpdatingService = cartUpdatingService;
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
    @RequestMapping(method = RequestMethod.GET, path = "/add")
    public Long addVariant(@PathVariable(name = "user_id", required = true) Long userId,
                           @RequestBody(required = true) CartItem_ cartItem_) {
        return cartUpdatingService.addVariant(userId, cartItem_);
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
    @RequestMapping(method = RequestMethod.DELETE, path = "/remove/{variant_id}")
    public void removeVariant(@PathVariable(name = "user_id", required = true) Long userId,
                              @PathVariable(name = "variant_id", required = true) Long variantId) {
        cartUpdatingService.removeVariant(userId, variantId);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/refresh")
    public List<CartRefreshment> refreshCart(
        @PathVariable(name = "user_id", required = true) Long userId,
        @RequestBody(required = true) List<CartRefreshment> cartItems
    ) {
        return cartUpdatingService.refreshCart(userId, cartItems);
    }

}
