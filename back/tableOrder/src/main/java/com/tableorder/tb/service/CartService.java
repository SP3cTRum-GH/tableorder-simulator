package com.tableorder.tb.service;

import com.tableorder.tb.DTO.CartItemRequestDTO;
import com.tableorder.tb.DTO.CartResponseDTO;

public interface CartService {
    CartResponseDTO addItemToCart(CartItemRequestDTO dto);
    CartResponseDTO getCartByTableNo(Long tableNo);
    void removeItemFromCart(Long orderNo);
}
