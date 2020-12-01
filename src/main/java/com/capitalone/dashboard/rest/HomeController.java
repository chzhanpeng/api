package com.capitalone.dashboard.rest;

import org.ff4j.FF4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

    @Autowired
    private FF4j ff4j;

    @RequestMapping(value = "/features", method = RequestMethod.PUT)
    public ResponseEntity<String> createOrUpdateFeature(@RequestParam String featureId, @RequestParam boolean enable) {
        if(enable) {
            ff4j.enable(featureId);
        } else {
            ff4j.disable(featureId);
        }
        String result = "Set " + featureId + " to " + enable;
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @RequestMapping(value = "/features", method = RequestMethod.GET)
    public ResponseEntity<String> getFeature(@RequestParam String featureId) {
        String result = String.valueOf(ff4j.getFeature(featureId).isEnable());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }






}