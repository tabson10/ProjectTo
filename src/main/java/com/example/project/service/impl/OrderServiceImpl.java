package com.example.project.service.impl;

import com.example.project.domain.*;
import com.example.project.repository.OrderRepository;
import com.example.project.service.CartItemService;
import com.example.project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartItemService cartItemService;

    @Override
    public synchronized Order createOrder(ShoppingCart shoppingCart, ShippingAddress shippingAddress, Billing billing, Payment payment, String shippingMethod, User user) {
        Order order = new Order();
        order.setBilling(billing);
        order.setOrderStatus("przyjęto");
        order.setPayment(payment);
        order.setShippingAddress(shippingAddress);
        order.setShippingMethod(shippingMethod);

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        for(CartItem cartItem : cartItemList) {
            Batch batch = cartItem.getBatch();
            cartItem.setOrder(order);
            batch.setQuantity(batch.getQuantity() - cartItem.getQty());
        }

        order.setCartItemList(cartItemList);
        order.setOrderDate(Calendar.getInstance().getTime());
        order.setOrderTotal(shoppingCart.getGrandTotal());
        shippingAddress.setOrder(order);
        billing.setOrder(order);
        payment.setOrder(order);
        order.setUser(user);
        order = orderRepository.save(order);

        return order;
    }

    @Override
    public Optional<Order> findOne(int id) {
        return orderRepository.findById(id);
    }
}
