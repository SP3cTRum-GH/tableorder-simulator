package com.tableorder.tb.controller;

import com.tableorder.tb.DTO.CartItemRequestDTO;
import com.tableorder.tb.DTO.CartResponseDTO;
import com.tableorder.tb.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<CartResponseDTO> addItemToCart(@RequestBody CartItemRequestDTO dto) {
        return ResponseEntity.ok(cartService.addItemToCart(dto));
    }

    @GetMapping("/{tableNo}")
    public ResponseEntity<CartResponseDTO> getCartByTableNo(@PathVariable Long tableNo) {
        return ResponseEntity.ok(cartService.getCartByTableNo(tableNo));
    }

    @DeleteMapping("/remove/{orderNo}")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable Long orderNo) {
        cartService.removeItemFromCart(orderNo);
        return ResponseEntity.noContent().build();
    }
}
