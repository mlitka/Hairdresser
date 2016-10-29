package com.litkowska.martyna.hairdresser.app.controller;

import com.litkowska.martyna.hairdresser.app.model.Client;
import com.litkowska.martyna.hairdresser.app.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Martyna on 21.09.2016.
 */
@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    @CrossOrigin("*")
    public ResponseEntity<?> getAllClients(){
        List<Client> clients = (List<Client>) clientService.findAll();
        return new ResponseEntity<List<Client>>(clients, HttpStatus.OK);
    }
}
