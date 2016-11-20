package com.litkowska.martyna.hairdresser.app.service;

import com.litkowska.martyna.hairdresser.app.model.Client;
import com.litkowska.martyna.hairdresser.app.model.User;
import com.litkowska.martyna.hairdresser.app.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Martyna on 21.09.2016.
 */
@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public Iterable<Client> findAll(){
        return clientRepository.findAll();
    }

    public Client findByUser(final User user){
        return clientRepository.findByUser(user);
    }

    public Client findOne(final long clientId){
        return clientRepository.findOne(clientId);
    }

}
