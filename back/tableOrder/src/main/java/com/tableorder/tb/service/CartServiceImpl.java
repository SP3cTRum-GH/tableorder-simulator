package com.tableorder.tb.service;

import com.tableorder.tb.DTO.*;
import com.tableorder.tb.entity.*;
import com.tableorder.tb.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.Collections; // Import Collections
import java.util.ArrayList; // Import ArrayList

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final TableRepository tableRepository;
    private final MenuRepository menuRepository;
    private final OptionRepository optionRepository;
    private final OrderRepository orderRepository;
    private final OrderOptionRepository orderOptionRepository;

    @Override
    @Transactional
    public CartResponseDTO addItemToCart(CartItemRequestDTO dto) {
        System.out.println("Received CartItemRequestDTO: " + dto.toString()); // Debug log

        TableEntity table = tableRepository.findById(dto.getTableNo())
                .orElseThrow(() -> new NoSuchElementException("Table not found"));

        Cart cart = cartRepository.findByTable(table)
                .orElseGet(() -> cartRepository.save(Cart.builder().table(table).build()));

        Menu menu = menuRepository.findById(dto.getMenuNo())
                .orElseThrow(() -> new NoSuchElementException("Menu not found"));

        Order order = Order.builder()
                .cart(cart)
                .menu(menu)
                .quantity(dto.getQuantity())
                .build();
        Order savedOrder = orderRepository.save(order);

        if (dto.getSelectedOptionNos() != null) {
            for (Long optionNo : dto.getSelectedOptionNos()) {
                Option option = optionRepository.findById(optionNo)
                        .orElseThrow(() -> new NoSuchElementException("Option not found"));
                OrderOption orderOption = OrderOption.builder()
                        .order(savedOrder)
                        .option(option)
                        .optionPrice(option.getPriceAdd()) // Store price at time of order
                        .build();
                orderOptionRepository.save(orderOption);
            }
        }
        return toCartResponseDTO(cart);
    }

    @Override
    public CartResponseDTO getCartByTableNo(Long tableNo) {
        TableEntity table = tableRepository.findById(tableNo)
                .orElseThrow(() -> new NoSuchElementException("Table not found"));
        
        // If cart is not found, return an empty CartResponseDTO instead of throwing an exception
        return cartRepository.findByTable(table)
                .map(this::toCartResponseDTO)
                .orElseGet(() -> CartResponseDTO.builder()
                        .cartNo(null) // No cart yet
                        .tableNo(tableNo)
                        .orders(Collections.emptyList())
                        .build());
    }

    @Override
    @Transactional
    public void removeItemFromCart(Long orderNo) {
        Order order = orderRepository.findById(orderNo)
                .orElseThrow(() -> new NoSuchElementException("Order not found"));
        orderRepository.delete(order);
    }

    @Transactional 
    public CartResponseDTO toCartResponseDTO(Cart cart) { 
        List<OrderResponseDTO> orderDTOs = (cart.getOrders() != null ? cart.getOrders() : new ArrayList<Order>()).stream()
                .map((Order order) -> toOrderResponseDTO(order)) 
                .collect(Collectors.toList());

        return CartResponseDTO.builder()
                .cartNo(cart.getCartNo())
                .tableNo(cart.getTable().getTableNo())
                .orders(orderDTOs)
                .build();
    }

    private OrderResponseDTO toOrderResponseDTO(Order order) {
        List<OrderOptionResponseDTO> orderOptionDTOs = order.getOrderOptions().stream()
                .map((OrderOption orderOption) -> toOrderOptionResponseDTO(orderOption)) 
                .collect(Collectors.toList());

        return OrderResponseDTO.builder()
                .orderNo(order.getOrderNo())
                .menuNo(order.getMenu().getMenuNo())
                .menuName(order.getMenu().getMenuName())
                .menuPrice(order.getMenu().getMenuPrice())
                .quantity(order.getQuantity())
                .orderOptions(orderOptionDTOs)
                .build();
    }

    private OrderOptionResponseDTO toOrderOptionResponseDTO(OrderOption orderOption) {
        return OrderOptionResponseDTO.builder()
                .orderOptionNo(orderOption.getOrderOptionNo())
                .optionNo(orderOption.getOption().getOptionNo())
                .optionValue(orderOption.getOption().getValue())
                .optionPrice(orderOption.getOptionPrice())
                .build();
    }
}
