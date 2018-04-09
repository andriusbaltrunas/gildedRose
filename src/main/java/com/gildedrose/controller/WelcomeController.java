package com.gildedrose.controller;

import com.gildedrose.elastic.search.service.builder.ElasticSearchBuilderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by andriusbaltrunas on 4/9/2018.
 */
@Controller
public class WelcomeController {

    private static final String WELCOME_PATH = "welcome";

    @Autowired
    private ElasticSearchBuilderService elasticSearchBuilderService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String initWelcomePage(){
        elasticSearchBuilderService.buildRestClient();
        return WELCOME_PATH;
    }

}
