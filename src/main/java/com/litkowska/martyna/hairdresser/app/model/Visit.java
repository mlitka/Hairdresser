package com.litkowska.martyna.hairdresser.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created by Martyna on 21.09.2016.
 */
@Entity
public class Visit {
    @Id
    private long id;
    @Column
    private Hairdresser hairdresser;
    @Column
    private Client client;
    @Column
    private Timestamp timestamp;

}
