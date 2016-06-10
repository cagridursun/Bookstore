package com.cagri.bookstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by cagri.dursun on 7.6.2016.
 */

@Controller
@RequestMapping(value = "/")
public class HomePageController {

    @RequestMapping(method = RequestMethod.GET)
    public String getIndexPage() {
        return "bookManagement";
    }
}
