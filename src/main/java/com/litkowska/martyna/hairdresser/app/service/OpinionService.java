package com.litkowska.martyna.hairdresser.app.service;

import com.litkowska.martyna.hairdresser.app.dto.OpinionDTO;
import com.litkowska.martyna.hairdresser.app.model.Client;
import com.litkowska.martyna.hairdresser.app.model.Opinion;
import com.litkowska.martyna.hairdresser.app.model.User;
import com.litkowska.martyna.hairdresser.app.repository.OpinionRepository;
import com.litkowska.martyna.hairdresser.app.repository.UserRepository;
import com.litkowska.martyna.hairdresser.app.security.models.AuthRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Created by Martyna on 28.10.2016.
 */
@Service
public class OpinionService {

    @Autowired
    private OpinionRepository opinionRepository;
    @Autowired
    private ClientService clientService;
    @Autowired
    private UserRepository userRepository;

    public Iterable<Opinion> getAllOpinions() {
        return opinionRepository.findAll();
    }

    @Transactional
    public Iterable<Opinion> getAllClientsOpinions(final long userId) {
        User user = userRepository.findOne(userId);
        System.out.println("all C Op.");
        System.out.println(user);
        if (user != null) {
            Client client = clientService.findByUser(user);
            System.out.println(client);
            if (client != null) {
                return client.getOpinions();
            }
        }
        return null;
    }

    @Transactional
    public Opinion save(final OpinionDTO opinionDTO) {
        Opinion opinion = new Opinion();
        User user = userRepository.findOne(opinionDTO.getUserId());
        Client client = clientService.findByUser(user);
        if (client == null) {

            client = new Client();
            client.setUser(user);
            client = clientService.save(client);
        }
        if (checkNotNull(opinionDTO)) {
            opinion.setClient(client);
            opinion.setRate(opinionDTO.getRate());
            opinion.setOpinionText(opinionDTO.getText());
            opinion.setDateTime(LocalDateTime.now());
            client.getOpinions().add(opinion);
            Opinion savedOpinion = opinionRepository.save(opinion);
            clientService.save(client);
            return savedOpinion;
        }
        return null;
    }

    @Transactional
    public boolean delete(final long opinionId) {
        Opinion opinion = opinionRepository.findOne(opinionId);
        if (opinion != null) {
            Client client = opinion.getClient();
            if (client != null) {
                client.getOpinions().remove(opinion);
                clientService.save(client);
                opinionRepository.delete(opinion);
                return true;
            }
        }
        return false;
    }

    /**
     * Checking not null attributes of Opinion entity (without dateTime -> it'll be set on the go)
     */
    private boolean checkNotNull(final OpinionDTO opinionDTO) {
        return opinionDTO.getUserId() > -1
                && opinionDTO.getRate() > -1
                && opinionDTO.getText() != null
                && !opinionDTO.getText().toString().isEmpty();
    }

    public boolean checkIfUserCanDeleteOpinion(final User user, final long opinionId) {
        if (user.getRole() == AuthRole.USER) {
            Client client = clientService.findOne(user.getId());
            Opinion opinion = opinionRepository.findOne(opinionId);
            if (client != null && opinion != null) {
                return opinion.getClient().getUser().getId() == client.getUser().getId();
            }
        }
        return false;
    }
}
