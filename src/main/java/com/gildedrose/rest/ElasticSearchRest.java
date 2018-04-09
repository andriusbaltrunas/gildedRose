package com.gildedrose.rest;

import com.gildedrose.elastic.search.service.ElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by andriusbaltrunas on 4/9/2018.
 */
@RestController(value = "/elasticSearch")
public class ElasticSearchRest {

    @Autowired
    private ElasticSearchService elasticSearchService;
}
