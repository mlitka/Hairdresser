package com.litkowska.martyna.hairdresser.app.service;

import com.litkowska.martyna.hairdresser.app.dto.VisitDTO;
import com.litkowska.martyna.hairdresser.app.model.Client;
import com.litkowska.martyna.hairdresser.app.model.User;
import com.litkowska.martyna.hairdresser.app.repository.ClientRepository;
import com.litkowska.martyna.hairdresser.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Martyna on 21.09.2016.
 */
@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private UserRepository userRepository;

    public Iterable<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client findOne(final long clientId) {
        return clientRepository.findOne(clientId);
    }

    @Transactional
    public Client saveClient(final VisitDTO visitDTO) {
        User user = userRepository.findByUsername(visitDTO.getClient().getEmail());
        Client client = clientRepository.findByUser(user);
        if (client == null) {
            client = new Client();
            client.getUser().setEmail(visitDTO.getClient().getEmail());
            client.getUser().setLastName(visitDTO.getClient().getLastName());
            client.getUser().setFirstName(visitDTO.getClient().getFirstName());
            client.getUser().setPhoneNo(visitDTO.getClient().getPhoneNo());
            if (client.getUser().checkNotNull()) {
                clientRepository.save(client);
            }
        } else {
            client.getUser().setPhoneNo(visitDTO.getClient().getPhoneNo());
            clientRepository.save(client);
        }
        return client;
    }

    public Client save(final Client client) {
        return clientRepository.save(client);
    }

    public Client findByUser(final User user){
        return clientRepository.findByUser(user);
    }

}
