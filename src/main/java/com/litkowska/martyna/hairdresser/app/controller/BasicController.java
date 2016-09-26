package com.litkowska.martyna.hairdresser.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Martyna on 28.08.2016.
 */

@RestController
public class BasicController {

    @RequestMapping(value = "/")
    public String getBasicPage(){
        return "Hello hairdresser!";
    }
}
