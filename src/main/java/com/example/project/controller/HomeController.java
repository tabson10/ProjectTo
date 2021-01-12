package com.example.project.controller;

import com.example.project.domain.*;
import com.example.project.domain.security.PasswordResetToken;
import com.example.project.domain.security.Role;
import com.example.project.domain.security.UserRole;
import com.example.project.service.*;
import com.example.project.service.impl.UserSecurityService;
import com.example.project.utility.MailConstructor;
import com.example.project.utility.SecurityUtility;
import com.sun.xml.bind.util.AttributesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.*;

@Controller
public class HomeController {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailConstructor mailConstructor;

    @Autowired
    UserService userService;

    @Autowired
    UserSecurityService userSecurityService;

    @Autowired
    private BatchService batchService;

    @Autowired
    private UserPaymentService userPaymentService;

    @Autowired
    private UserShippingService userShippingService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private OrderService orderService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
        public String login(Model model) {
            model.addAttribute("classActiveLogin", true);
            return "myAccount";
        }

    @RequestMapping("/newUser")
    public String newUser(Locale locale, @RequestParam("token") String token, Model model) {
        PasswordResetToken passToken = userService.getPasswordResetToken(token);

        if(passToken == null) {
            String message = "Invalid Token.";
            model.addAttribute("message", message);
            return "redirect:/badRequest";
        }

        User user = passToken.getUser();
        String username = user.getUsername();

        UserDetails userDetails = userSecurityService.loadUserByUsername(username);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        model.addAttribute("user", user);

        model.addAttribute("classActiveEdit", true);
        return "myAccount";
    }

    @RequestMapping("/forgetPassword")
    public String forgetPassword(HttpServletRequest request, @ModelAttribute("email") String email, Model model) throws Exception {
        model.addAttribute("classActiveForgetPassword", true);

        User user = userService.findByEmail(email);

        if (user == null) {
            model.addAttribute("emailNotExist", true);
            return "myAccount";
        }

        String password = SecurityUtility.randomPassword();

        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
        user.setPassword(encryptedPassword);

        userService.save(user);

        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);
        String appUrl = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();

        SimpleMailMessage newEmail = mailConstructor.constructResetTokenEmailReset(appUrl, request.getLocale(), token, user, password);

        mailSender.send(newEmail);

        model.addAttribute("forgetPasswordEmailSent", true);

        return "myAccount";
    }

    @RequestMapping(value = "/newUser", method = RequestMethod.POST)
    public String newUserPost(HttpServletRequest request, @ModelAttribute("email") String userEmail, @ModelAttribute("username") String username, @ModelAttribute("newPassword") String newPassword, @ModelAttribute("firstName") String firstName, @ModelAttribute("lastName") String lastName, Model model)
    throws Exception{
        model.addAttribute("classActiveNewAccount", true);
        model.addAttribute("email", userEmail);
        model.addAttribute("username", username);
        model.addAttribute("password", newPassword);
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);

        if (userService.findByUsername(username) != null) {
            model.addAttribute("usernameExists", true);

            return "myAccount";
        }

        if (userService.findByEmail(userEmail) != null) {
            model.addAttribute("emailExists", true);

            return "myAccount";
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(userEmail);
        user.setPassword(newPassword);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        String encryptedPassword = SecurityUtility.passwordEncoder().encode(newPassword);
        user.setPassword(encryptedPassword);

        Role role = new Role();
        role.setRoleId(1);
        role.setName("ROLE_USER");
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(user, role));
        userService.createUser(user, userRoles);

        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);
        String appUrl = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();

        SimpleMailMessage email = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user);

        mailSender.send(email);

        model.addAttribute("emailSent", true);

        return "myAccount";
    }

    @RequestMapping("/myProfile")
    public String myProfile(Model model, Principal principal){

        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("userPaymentList", user.getUserPaymentList());
        model.addAttribute("userShippingList", user.getUserShippingList());
        model.addAttribute("orderList", user.getOrderList());

        UserShipping userShipping = new UserShipping();
        model.addAttribute("userShipping", userShipping);

        model.addAttribute("listOfCreditCards", true);
        model.addAttribute("listOfShippingAdresses", true);

        model.addAttribute("classActiveEdit", true);

        return "myProfile";
    }

    @RequestMapping("/listOfShippingAddresses")
    public String listOfShippingAddresses(Model model, Principal principal, HttpServletRequest request) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("userPaymentList", user.getUserPaymentList());
        model.addAttribute("userShippingList", user.getUserShippingList());
        model.addAttribute("orderList", user.getOrderList());

        model.addAttribute("listOfCreditCards", true);
        model.addAttribute("classActiveShipping", true);
        model.addAttribute("listOfShippingAddresses", true);

        return "myProfile";
    }

    @RequestMapping("/addNewShippingAddress")
    public String addNewShippingAddress(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);

        model.addAttribute("addNewShippingAddress", true);
        model.addAttribute("classActiveShipping", true);

        UserShipping userShipping = new UserShipping();

        model.addAttribute("userShipping", userShipping);

        model.addAttribute("userPaymentList", user.getUserPaymentList());
        model.addAttribute("userShippingList", user.getUserShippingList());
        model.addAttribute("orderList", user.getOrderList());

        return "myProfile";
    }

    @RequestMapping(value = "/addNewShippingAddress", method = RequestMethod.POST)
    public String addNewShippingAddressPost(@ModelAttribute("userShipping") UserShipping userShipping, Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        userService.updateUserShipping(userShipping, user);

        model.addAttribute("user", user);
        model.addAttribute("userPaymentList", user.getUserPaymentList());
        model.addAttribute("userShippingList", user.getUserShippingList());
        model.addAttribute("orderList", user.getOrderList());
        model.addAttribute("listOfCreditCards", true);
        model.addAttribute("classActiveShipping", true);
        model.addAttribute("listOfShippingAddresses", true);

        return "myProfile";
    }

    @RequestMapping("/updateShippingAddress")
    public String updateShippingAddress(@ModelAttribute("id") int shippingAddressId, Principal principal, Model model){
        User user = userService.findByUsername(principal.getName());
        Optional<UserShipping> userShipping = userShippingService.findById(shippingAddressId);

        if(user.getId() != userShipping.get().getUser().getId()) {
            return "badRequestPage";
        } else {
            model.addAttribute("user", user);
            model.addAttribute("userShipping", userShipping.get());

            model.addAttribute("addNewShippingAddress", true);
            model.addAttribute("classActiveShipping", true);
            model.addAttribute("listOfCreditCards", true);

            model.addAttribute("userPaymentList", user.getUserPaymentList());
            model.addAttribute("userShippingList", user.getUserShippingList());
            model.addAttribute("orderList", user.getOrderList());

            return "myProfile";
        }
    }

    @RequestMapping("/removeShippingAddress")
    public String removeShippingAddress(@ModelAttribute("id") int shippingAddressId, Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        Optional<UserShipping> userShipping = userShippingService.findById(shippingAddressId);

        if(user.getId() != userShipping.get().getUser().getId()) {
            return "badRequestPage";
        } else {
            model.addAttribute("user", user);
            userShippingService.removeById(userShipping.get());

            model.addAttribute("listOfCreditCards", true);
            model.addAttribute("classActiveShipping", true);
            model.addAttribute("listOfShippingAddresses", true);

            model.addAttribute("userPaymentList", user.getUserPaymentList());
            model.addAttribute("userShippingList", user.getUserShippingList());
            model.addAttribute("orderList", user.getOrderList());

            return "myProfile";
        }
    }

    @RequestMapping(value = "/setDefaultShippingAddress", method = RequestMethod.POST)
    public String setDefaultShippingAddress(@ModelAttribute("defaultShippingAddressId") int defaultShippingAddressId, Principal principal, Model model) {

        User user = userService.findByUsername(principal.getName());
        userService.setUserDefaultShippingAddress(defaultShippingAddressId, user);

        model.addAttribute("user", user);

        model.addAttribute("listOfCreditCards", true);
        model.addAttribute("classActiveShipping", true);
        model.addAttribute("listOfShippingAddresses", true);

        model.addAttribute("userPaymentList", user.getUserPaymentList());
        model.addAttribute("userShippingList", user.getUserShippingList());
        model.addAttribute("orderList", user.getOrderList());

        return "myProfile";
    }

    @RequestMapping("/listOfCreditCards")
    public String listOfCreditCards(Model model, Principal principal, HttpServletRequest request) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("userPaymentList", user.getUserPaymentList());
        model.addAttribute("userShippingList", user.getUserShippingList());
        model.addAttribute("orderList", user.getOrderList());

        model.addAttribute("listOfCreditCards", true);
        model.addAttribute("classActiveBilling", true);
        model.addAttribute("listOfShippingAddresses", true);

        return "myProfile";
    }

    @RequestMapping("/addNewCreditCard")
    public String addNewCreditCard(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);

        model.addAttribute("addNewCreditCard", true);
        model.addAttribute("classActiveBilling", true);
        model.addAttribute("listOfShippingAddresses", true);

        UserBilling userBilling = new UserBilling();
        UserPayment userPayment = new UserPayment();

        model.addAttribute("userBilling", userBilling);
        model.addAttribute("userPayment", userPayment);

        model.addAttribute("userPaymentList", user.getUserPaymentList());
        model.addAttribute("userShippingList", user.getUserShippingList());
        model.addAttribute("orderList", user.getOrderList());

        return "myProfile";
    }

    @RequestMapping(value = "/addNewCreditCard", method = RequestMethod.POST)
    public String addNewCreditCardPost(@ModelAttribute("userPayment") UserPayment userPayment, @ModelAttribute("userBilling") UserBilling userBilling
    , Principal principal, Model model){
        User user = userService.findByUsername(principal.getName());
        userService.updateUserBilling(userBilling, userPayment, user);

        model.addAttribute("user", user);
        model.addAttribute("userPaymentList", user.getUserPaymentList());
        model.addAttribute("userShippingList", user.getUserShippingList());
        model.addAttribute("listOfCreditCards", true);
        model.addAttribute("classActiveBilling", true);
        model.addAttribute("listOfShippingAddresses", true);
        model.addAttribute("orderList", user.getOrderList());

        return "myProfile";
    }

    @RequestMapping("/updateCreditCard")
    public String updateCreditCard(@ModelAttribute("id") int creditCardId, Principal principal, Model model){
        User user = userService.findByUsername(principal.getName());
        Optional<UserPayment> userPayment = userPaymentService.findById(creditCardId);

        if(user.getId() != userPayment.get().getUser().getId()) {
            return "badRequestPage";
        } else {
            model.addAttribute("user", user);
            UserBilling userBilling = userPayment.get().getUserBilling();
            model.addAttribute("userPayment", userPayment.get());
            model.addAttribute("userBilling", userBilling);

            model.addAttribute("addNewCreditCard", true);
            model.addAttribute("classActiveBilling", true);
            model.addAttribute("listOfShippingAddresses", true);
            model.addAttribute("orderList", user.getOrderList());

            model.addAttribute("userPaymentList", user.getUserPaymentList());
            model.addAttribute("userShippingList", user.getUserShippingList());

            return "myProfile";
        }
    }

    @RequestMapping("/removeCreditCard")
    public String removeCreditCard(@ModelAttribute("id") int creditCardId, Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        Optional<UserPayment> userPayment = userPaymentService.findById(creditCardId);

        if(user.getId() != userPayment.get().getUser().getId()) {
            return "badRequestPage";
        } else {
            model.addAttribute("user", user);
            userPaymentService.removeById(userPayment.get());

            model.addAttribute("listOfCreditCards", true);
            model.addAttribute("classActiveBilling", true);
            model.addAttribute("listOfShippingAddresses", true);

            model.addAttribute("userPaymentList", user.getUserPaymentList());
            model.addAttribute("userShippingList", user.getUserShippingList());
            model.addAttribute("orderList", user.getOrderList());

            return "myProfile";
        }
    }

    @RequestMapping(value = "/setDefaultPayment", method = RequestMethod.POST)
    public String setDefaultPayment(@ModelAttribute("defaultUserPaymentId") int defaultPaymentId, Principal principal, Model model) {

        User user = userService.findByUsername(principal.getName());
        userService.setUserDefaultPayment(defaultPaymentId, user);

        model.addAttribute("user", user);

        model.addAttribute("listOfCreditCards", true);
        model.addAttribute("classActiveBilling", true);
        model.addAttribute("listOfShippingAddresses", true);

        model.addAttribute("userPaymentList", user.getUserPaymentList());
        model.addAttribute("userShippingList", user.getUserShippingList());
        model.addAttribute("orderList", user.getOrderList());

        return "myProfile";
    }



    @RequestMapping("/browse")
    public String browse(Model model, Principal principal){
        if(principal != null) {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }

        List<Batch> batchList = batchService.findAll();
        model.addAttribute("batchList", batchList);
        List<Integer> qtyList = Arrays.asList(1,5,10,20,30,40,50,60,70,80,90,100);


        model.addAttribute("qtyList", qtyList);
        model.addAttribute("qty", 1);
        return "browse";
    }

    @RequestMapping("fruitDetail/{id}")
    public String fruitDetail(@PathVariable("id") int batchId, Model model, Principal principal) {
        if(principal != null) {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }

        Optional<Batch> batch = batchService.findById(batchId);

        model.addAttribute("batch", batch.get());

        List<Integer> qtyList = Arrays.asList(1,5,10,20,30,40,50,60,70,80,90,100);


        model.addAttribute("qtyList", qtyList);
        model.addAttribute("qty", 1);

        return "fruitDetail";
    }

    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public String updateUserInfo(@ModelAttribute("user") User user,@ModelAttribute("newPassword") String newPassword, Model model) throws Exception {
        Optional<User> currentUser = userService.findById(user.getId());

        if(currentUser == null) {
            throw new Exception("User not found");
        }

        if(userService.findByEmail(user.getEmail()) != null) {
            if(userService.findByEmail(user.getEmail()).getId() != currentUser.get().getId()) {
                model.addAttribute("emailExists", true);
                return "myProfile";
            }
        }

        if(userService.findByUsername(user.getUsername()) != null) {
            if(userService.findByUsername(user.getUsername()).getId() != currentUser.get().getId()) {
                model.addAttribute("usernameExists", true);
                return "myProfile";
            }
        }
        
        if(newPassword != null && !newPassword.isEmpty() && !newPassword.equals("")) {
            BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
            String dbPassword = currentUser.get().getPassword();
            if(passwordEncoder.matches(user.getPassword(), dbPassword)) {
                currentUser.get().setPassword(passwordEncoder.encode(newPassword));
            } else {
                model.addAttribute("incorrectPassword", true);
                return "myProfile";
            }
        }

        currentUser.get().setFirstName(user.getFirstName());
        currentUser.get().setLastName(user.getLastName());
        currentUser.get().setUsername(user.getUsername());
        currentUser.get().setEmail(user.getEmail());

        userService.save(currentUser.get());

        model.addAttribute("updateSuccess", true);
        model.addAttribute("user", currentUser.get());
        model.addAttribute("classActiveEdit", true);

        UserDetails userDetails = userSecurityService.loadUserByUsername(currentUser.get().getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "myProfile";
    }

    @RequestMapping("/orderDetail")
    public String orderDetail(@RequestParam("id") int orderId, Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        Optional<Order> order = orderService.findOne(orderId);

        if(order.get().getUser().getId() != user.getId()) {
            return "badRequestPage";
        } else {
            List<CartItem> cartItemList = cartItemService.findByOrder(order.get());
            model.addAttribute("cartItemList", cartItemList);
            model.addAttribute("user", user);
            model.addAttribute("order", order.get());

            model.addAttribute("userPaymentList", user.getUserPaymentList());
            model.addAttribute("userShippingList", user.getUserShippingList());
            model.addAttribute("orderList", user.getOrderList());

            UserShipping userShipping = new UserShipping();

            model.addAttribute("userShipping", userShipping);

            model.addAttribute("listOfShippingAddresses", true);
            model.addAttribute("classActiveOrders", true);
            model.addAttribute("listOfCreditCards", true);
            model.addAttribute("displayOrderDetail", true);


            return "myProfile";
        }

    }
}
