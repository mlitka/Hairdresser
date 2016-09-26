package com.litkowska.martyna.hairdresser.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Martyna on 26.09.2016.
 */
@Entity
public class Person{
    @Id
    private long id;
    @Column
    private String lastName;
    @Column
    private String firstName;
    @Column
    private String email;

}
