package com.example.project.service.impl;

import com.example.project.domain.*;
import com.example.project.repository.BatchToCardItemRepository;
import com.example.project.repository.CartItemRepository;
import com.example.project.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private BatchToCardItemRepository batchToCartItemRepository;

    @Override
    public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart) {
        return cartItemRepository.findByShoppingCart(shoppingCart);
    }

    @Override
    public CartItem updateCartItem(CartItem cartItem) {
        BigDecimal bigDecimal = new BigDecimal(cartItem.getBatch().getPrice()).multiply(new BigDecimal(cartItem.getQty()));

        bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        cartItem.setSubtotal(bigDecimal);

        cartItemRepository.save(cartItem);

        return cartItem;
    }

    @Override
    public CartItem addBatchToCartItem(Batch batch, User user, int qty) {
        List<CartItem> cartItemList = findByShoppingCart(user.getShoppingCart());

        for(CartItem cartItem : cartItemList) {
            if(batch.getBatchId() == cartItem.getBatch().getBatchId()) {
                cartItem.setQty(cartItem.getQty()+qty);
                cartItem.setSubtotal(new BigDecimal(batch.getPrice()).multiply(new BigDecimal(qty)));
                cartItemRepository.save(cartItem);
                return cartItem;
            }
        }

        CartItem cartItem = new CartItem();
        cartItem.setShoppingCart(user.getShoppingCart());
        cartItem.setBatch(batch);
        cartItem.setQty(qty);
        cartItem.setSubtotal(new BigDecimal(batch.getPrice()).multiply(new BigDecimal(qty)));
        cartItem = cartItemRepository.save(cartItem);

        BatchToCartItem batchToCartItem = new BatchToCartItem();
        batchToCartItem.setBatch(batch);
        batchToCartItem.setCartItem(cartItem);
        batchToCartItemRepository.save(batchToCartItem);

        return cartItem;
    }

    @Override
    public Optional<CartItem> findById(int id) {
        return cartItemRepository.findById(id);
    }


    @Override
    public void removeCartItem(CartItem cartItem) {
        //batchToCartItemRepository.deleteByCartItem(cartItem);
        cartItemRepository.delete(cartItem);
    }

    @Override
    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @Override
    public List<CartItem> findByOrder(Order order) {
        return cartItemRepository.findByOrder(order);
    }
}
