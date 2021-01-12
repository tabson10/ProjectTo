package com.example.project.controller;

import com.example.project.domain.*;
import com.example.project.service.*;
import com.example.project.utility.MailConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Controller
public class CheckoutController {

    private ShippingAddress shippingAddress = new ShippingAddress();
    private Billing billing = new Billing();
    private Payment payment = new Payment();

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ShippingAddressService shippingAddressService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private BillingService billingService;

    @Autowired
    private UserShippingService userShippingService;

    @Autowired
    private UserPaymentService userPaymentService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MailConstructor mailConstructor;


    @RequestMapping("/checkout")
    public String checkout(@RequestParam("id") int cartId, @RequestParam(value = "missingRequiredField", required = false) boolean missingRequiredField,
    Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());

        if(cartId != user.getShoppingCart().getId()) {
            return "badRequestPage";
        }

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(user.getShoppingCart());

        if(cartItemList.size() == 0) {
            model.addAttribute("emptyCart", true);
            return "forward:/shoppingCart/cart";
        }

        for (CartItem cartItem : cartItemList) {
            if(cartItem.getBatch().getQuantity() < cartItem.getQty()) {
                model.addAttribute("notEnoughStock", true);
                return "forward:/shoppingCart/cart";
            }
        }

        List<UserShipping> userShippingList = user.getUserShippingList();
        List<UserPayment> userPaymentList = user.getUserPaymentList();

        model.addAttribute("userShippingList", userShippingList);
        model.addAttribute("userPaymentList", userPaymentList);

        if(userPaymentList.size() == 0) {
            model.addAttribute("emptyPaymentList", true);
        } else {
            model.addAttribute("emptyPaymentList", false);
        }

        if(userShippingList.size() == 0) {
            model.addAttribute("emptyShippingList", true);
        } else {
            model.addAttribute("emptyShippingList", false);
        }

        ShoppingCart shoppingCart = user.getShoppingCart();

        for(UserShipping userShipping : userShippingList) {
            if(userShipping.isDefaultAddress()) {
                shippingAddressService.setByUserShipping(userShipping, shippingAddress);
            }
        }

        for(UserPayment userPayment : userPaymentList) {
            if(userPayment.isDefaultPayment()) {
                paymentService.setByUserPayment(userPayment, payment);
                billingService.setByUserBilling(userPayment.getUserBilling(), billing);
            }
        }

        model.addAttribute("shippingAddress", shippingAddress);
        model.addAttribute("billing", billing);
        model.addAttribute("payment", payment);
        model.addAttribute("cartItemList", cartItemList);
        model.addAttribute("shoppingCart", user.getShoppingCart());

        model.addAttribute("classActiveShipping", true);

        if(missingRequiredField) {
            model.addAttribute("missingRequiredField", true);
        }

        return "checkout";
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public String checkoutPost(@ModelAttribute("shippingAddress") ShippingAddress shippingAddress,
                               @ModelAttribute("billing") Billing billing,
                               @ModelAttribute("payment") Payment payment,
                               @ModelAttribute("billingSameAsShippinh") String billingSameAsShipping,
                               @ModelAttribute("shippingMethod") String shippingMethod,
                               Principal principal, Model model) {
        ShoppingCart shoppingCart = userService.findByUsername(principal.getName()).getShoppingCart();

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
        model.addAttribute("cartItemList", cartItemList);

        if(billingSameAsShipping.equals("true")) {
            billing.setBillingAddressCity(shippingAddress.getShippingAddressCity());
            billing.setBillingAddressStreet(shippingAddress.getShippingAddressStreet());
            billing.setBillingAddressName(shippingAddress.getShippingAddressName());
            billing.setBillingAddressHouseNumber(shippingAddress.getShippingAddressHouseNumber());
            billing.setBillingAddressPostcode(shippingAddress.getShippingAddressPostcode());
            billing.setBillingAddressApartmentNumber(shippingAddress.getShippingAddressApartmentNumber());
            billing.setBillingAddressVoivodeship(shippingAddress.getShippingAddressVoivodeship());
        }

        if(shippingAddress.getShippingAddressCity().isEmpty() ||
                shippingAddress.getShippingAddressStreet().isEmpty() ||
                shippingAddress.getShippingAddressName().isEmpty() ||
                shippingAddress.getShippingAddressHouseNumber().isEmpty() ||
                shippingAddress.getShippingAddressPostcode().isEmpty() ||
                shippingAddress.getShippingAddressVoivodeship().isEmpty() ||
                payment.getCardNumber().isEmpty() ||
                payment.getCvc() == 0 ||
                billing.getBillingAddressCity().isEmpty() ||
                billing.getBillingAddressStreet().isEmpty() ||
                billing.getBillingAddressName().isEmpty() ||
                billing.getBillingAddressHouseNumber().isEmpty() ||
                billing.getBillingAddressPostcode().isEmpty() ||
                billing.getBillingAddressVoivodeship().isEmpty())  {
            return "redirect:/checkout?id="+shoppingCart.getId()+"&missingRequiredField=true";
        }
        User user = userService.findByUsername(principal.getName());

        Order order = orderService.createOrder(shoppingCart, shippingAddress, billing, payment, shippingMethod, user);

        mailSender.send(mailConstructor.constructOrderConfirmationEmail(user, order, Locale.ENGLISH));

        shoppingCartService.clearShoppingCart(shoppingCart);

        return "orderSubmittedPage";
    }

    @RequestMapping("setShippingAddress")
    public String setShippingAddress(@RequestParam("userShippingId") int userShippingId, Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        Optional<UserShipping> userShipping = userShippingService.findById(userShippingId);
        
        if(userShipping.get().getUser().getId() != user.getId()) {
            return "badRequestPage";
        } else {
            shippingAddressService.setByUserShipping(userShipping.get(), shippingAddress);

            List<CartItem> cartItemList = cartItemService.findByShoppingCart(user.getShoppingCart());

            model.addAttribute("shippingAddress", shippingAddress);
            model.addAttribute("billing", billing);
            model.addAttribute("payment", payment);
            model.addAttribute("cartItemList", cartItemList);
            model.addAttribute("shoppingCart", user.getShoppingCart());

            List<UserShipping> userShippingList = user.getUserShippingList();
            List<UserPayment> userPaymentList = user.getUserPaymentList();

            model.addAttribute("userShippingList", userShippingList);
            model.addAttribute("userPaymentList", userPaymentList);

            model.addAttribute("shippingAddress", shippingAddress);
            model.addAttribute("classActiveShipping", true);

            if(userPaymentList.size() == 0) {
                model.addAttribute("emptyPaymentList", true);
            } else {
                model.addAttribute("emptyPaymentList", false);
            }

            model.addAttribute("emptyShippingList", false);

            return "checkout";
        }
    }

    @RequestMapping("/setPaymentMethod")
    public String setPaymentMethod(@RequestParam("userPaymentId") int userPaymentId, Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        Optional<UserPayment> userPayment = userPaymentService.findById(userPaymentId);
        UserBilling userBilling = userPayment.get().getUserBilling();

        if(userPayment.get().getUser().getId() != user.getId()) {
            return "badRequestPage";
        } else {
            paymentService.setByUserPayment(userPayment.get(), payment);

            List<CartItem> cartItemList = cartItemService.findByShoppingCart(user.getShoppingCart());

            billingService.setByUserBilling(userBilling, billing);

            model.addAttribute("shippingAddress", shippingAddress);
            model.addAttribute("billing", billing);
            model.addAttribute("payment", payment);
            model.addAttribute("cartItemList", cartItemList);
            model.addAttribute("shoppingCart", user.getShoppingCart());

            List<UserShipping> userShippingList = user.getUserShippingList();
            List<UserPayment> userPaymentList = user.getUserPaymentList();

            model.addAttribute("userShippingList", userShippingList);
            model.addAttribute("userPaymentList", userPaymentList);

            model.addAttribute("shippingAddress", shippingAddress);
            model.addAttribute("classActivePayment", true);

            if(userShippingList.size() == 0) {
                model.addAttribute("emptyShippingList", true);
            } else {
                model.addAttribute("emptyShippingList", false);
            }

            model.addAttribute("emptyPaymentList", false);


            return "checkout";
        }
    }

}
