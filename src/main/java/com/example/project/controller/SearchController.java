package com.example.project.controller;

import com.example.project.domain.Batch;
import com.example.project.domain.User;
import com.example.project.service.BatchService;
import com.example.project.service.UserService;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    UserService userService;

    @Autowired
    BatchService batchService;

    @RequestMapping("/searchByCategory")
    public String searchByCategory(@RequestParam("category") int fruit, Model model, Principal principal) {
        if(principal != null) {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }

        String classActiveCategory = "active"+fruit;
        classActiveCategory = classActiveCategory.replaceAll("\\s+","");
        model.addAttribute(classActiveCategory, true);

        List<Batch> batchList = batchService.findByFruit(fruit);

        if(batchList.isEmpty()) {
            model.addAttribute("emptyList", true);
            return "browse";
        }

        model.addAttribute("batchList", batchList);
        return "browse";
    }
}
