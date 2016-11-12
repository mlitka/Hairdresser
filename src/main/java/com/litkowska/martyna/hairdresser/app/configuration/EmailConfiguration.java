package com.litkowska.martyna.hairdresser.app.configuration;

/**
 * Created by Martyna on 03.11.2016.
 */

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "mail")
public class EmailConfiguration {
    public static class Smtp {

        private boolean auth;
        private boolean starttlsEnable;
        private int port;

        public boolean isAuth() {
            return auth;
        }

        public void setAuth(boolean auth) {
            this.auth = auth;
        }

        public boolean isStarttlsEnable() {
            return starttlsEnable;
        }

        public void setStarttlsEnable(boolean starttlsEnable) {
            this.starttlsEnable = starttlsEnable;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }


    private String transport;
    private String connect;
    private String gmailId;
    private String gmailPass;
    private Smtp smtp;

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getConnect() {
        return connect;
    }

    public void setConnect(String connect) {
        this.connect = connect;
    }

    public String getGmailID() {
        return gmailId;
    }

    public void setGmailID(String gmailId) {
        this.gmailId = gmailId;
    }

    public String getGmailPass() {
        return gmailPass;
    }

    public void setGmailPass(String gmailPass) {
        this.gmailPass = gmailPass;
    }

    public Smtp getSmtp() {
        return smtp;
    }

    public void setSmtp(Smtp smtp) {
        this.smtp = smtp;
    }
}
