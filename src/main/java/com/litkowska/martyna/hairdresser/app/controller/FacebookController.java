package com.litkowska.martyna.hairdresser.app.controller;

/**
 * Created by Martyna on 12.11.2016.
 */

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FacebookController {

    private static String APP_ID = "221519038253457";
    private static String APP_SECRET = "4881c9c219e7ae1c8565219093991948";
    private FacebookConnectionFactory connectionFactory;
    private OAuth2Operations oauthOperations;


    @RequestMapping(value = "/facebook")
    @CrossOrigin("*")
    public String helloFacebook(){
        System.out.println("in hello facebook");
        connectionFactory = new FacebookConnectionFactory(APP_ID,APP_SECRET);
        oauthOperations = connectionFactory.getOAuthOperations();
        OAuth2Parameters params = new OAuth2Parameters();

        params.setRedirectUri("http://localhost/logged");
//        params.setScope("scope");

        String authorizeUrl = oauthOperations.buildAuthorizeUrl(params);
        authorizeUrl+="&display=popup";
        System.out.println(authorizeUrl);
        return "redirect:"+authorizeUrl;
    }

    @RequestMapping(value = "/logged")
    @CrossOrigin("*")
    public String loggedFacebook(final @RequestParam("code") String code){
        AccessGrant accessGrant = oauthOperations.exchangeForAccess(code,"http://localhost/logged",null);

        Connection<Facebook> connection = connectionFactory.createConnection(accessGrant);

        ConnectionKey connectionKey = connection.getKey();

        Facebook facebook=connection!=null?connection.getApi():new FacebookTemplate(code);

        String [] fields = { "id", "email",  "first_name", "last_name" };
        System.out.println("User key: "+connectionKey.getProviderUserId());
        User userProfile = facebook.fetchObject(connectionKey.getProviderUserId(), User.class, fields);
        System.out.println("\n\nLOGGGEDD!!");
        System.out.println(userProfile);
        System.out.println("\n\nfb get user profile!!");
        System.out.println(userProfile.getFirstName());

        return "redirect:/";
    }
}
