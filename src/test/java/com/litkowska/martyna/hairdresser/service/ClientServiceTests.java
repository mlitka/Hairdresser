package com.litkowska.martyna.hairdresser.service;

import com.litkowska.martyna.hairdresser.app.dto.ClientDTO;
import com.litkowska.martyna.hairdresser.app.dto.VisitDTO;
import com.litkowska.martyna.hairdresser.app.model.Client;
import com.litkowska.martyna.hairdresser.app.model.User;
import com.litkowska.martyna.hairdresser.app.repository.ClientRepository;
import com.litkowska.martyna.hairdresser.app.repository.UserRepository;
import com.litkowska.martyna.hairdresser.app.service.ClientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by Martyna on 30.11.2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
public class ClientServiceTests {

    private static String USER_EMAIL = "email";
    private static String USER_LAST_NAME = "LastName";
    private static String USER_FIRST_NAME = "FirstName";
    private static String USER_PHONE_NO = "000-000-000";

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private UserRepository userRepository;


    @InjectMocks
    private ClientService clientService;

    @Test
    public void testFindAllEmpty(){
        //given
        this.stubFindAllEmpty();
        //when
        ArrayList<Client> result = (ArrayList<Client>) clientService.findAll();
        //then
        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    public void testFindAllNull(){
        //given
        this.stubFindAllNull();
        //when
        ArrayList<Client> result = (ArrayList<Client>) clientService.findAll();
        //then
        assertThat(result).isNull();
    }

    @Test
    public void testFindAllNotEmpty(){
        //given
        this.stubFindAllNotEmpty();
        //when
        ArrayList<Client> result = (ArrayList<Client>) clientService.findAll();
        //then
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void testSaveClientNull(){
        //given
        this.stubSaveClientNull();
        //when
        Client client = clientService.save(null);
        //then
        assertThat(client).isNull();
    }

    @Test
    public void testSaveClientNotNull(){
        //given
        this.stubSaveClientNotNull();
        //when
        Client client = clientService.save(new Client());
        //then
        assertThat(client).isNotNull();
    }

    @Test
    public void testFindByUserNotNull(){
        //given
        this.stubFindByUserNotNull();
        //when
        Client client = clientService.findByUser(new User());
        //then
        assertThat(client).isNotNull();
    }

    @Test
    public void testFindByUserNull(){
        //given
        this.stubFindByUserNull();
        //when
        Client client = clientService.findByUser(new User());
        //then
        assertThat(client).isNull();
    }

    @Test
    public void testSaveClientUserNull(){
        //given
        VisitDTO visitDTO = this.stubSaveClientVisitDTO();
        this.stubSaveClientNullClient();
        //when
        Client client = clientService.saveClient(visitDTO);
        //then
        assertThat(client).isNotNull();
        assertThat(client.getUser().getEmail()).isEqualTo(USER_EMAIL);
    }

    @Test
    public void testSaveClientUserNotNull(){
        //given
        VisitDTO visitDTO = this.stubSaveClientVisitDTO();
        this.stubSaveClientNotNullClient();
        //when
        Client client = clientService.saveClient(visitDTO);
        //then
        assertThat(client).isNotNull();
        assertThat(client.getUser().getEmail()).isEqualTo(USER_EMAIL);
    }

    private void stubFindAllEmpty(){
        given(clientRepository.findAll()).willReturn(new ArrayList<Client>());
    }

    private void stubFindAllNull(){
        when(this.clientRepository.findAll()).thenReturn(null);
    }

    private void stubFindAllNotEmpty(){
        ArrayList<Client> result = new ArrayList<Client>();
        result.add(new Client());
        given(this.clientRepository.findAll()).willReturn(result);
    }

    private void stubSaveClientNotNull(){
        given(this.clientRepository.save(any(Client.class))).willReturn(new Client());
    }

    private void stubSaveClientNull(){
        given(this.clientRepository.save(any(Client.class))).willReturn(null);
    }

    private void stubFindByUserNotNull(){
        given(clientRepository.findByUser(any(User.class ))).willReturn(new Client());
    }

    private void stubFindByUserNull(){
        given(clientRepository.findByUser(any(User.class ))).willReturn(null);
    }

    private VisitDTO stubSaveClientVisitDTO(){
        VisitDTO visitDTO = new VisitDTO();
        ClientDTO client  = new ClientDTO();
        client.setEmail(USER_EMAIL);
        client.setLastName(USER_LAST_NAME);
        client.setFirstName(USER_FIRST_NAME);
        client.setPhoneNo(USER_PHONE_NO);
        visitDTO.setClient(client);
        return visitDTO;
    }

    private Client stubClient(){
        Client client = new Client();
        client.setUser(new User());
        client.getUser().setEmail(USER_EMAIL);
        return client;
    }

    private void stubSaveClientNullClient(){
        given(userRepository.findByUsername(anyString())).willReturn(null);
        given(clientRepository.findByUser(any(User.class))).willReturn(null);
        Client client = this.stubClient();
        given(clientRepository.save(any(Client.class))).willReturn(client);
    }

    private void stubSaveClientNotNullClient(){
        given(userRepository.findByUsername(anyString())).willReturn(new User());
        Client client = this.stubClient();
        given(clientRepository.findByUser(any(User.class))).willReturn(client);
        given(clientRepository.save(any(Client.class))).willReturn(client);
    }
}
