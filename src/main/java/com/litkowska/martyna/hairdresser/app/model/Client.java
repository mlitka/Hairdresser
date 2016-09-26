package com.litkowska.martyna.hairdresser.app.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by Martyna on 21.09.2016.
 */
@Entity
public class Client extends Person{
    @OneToMany
    private List<Visit> visits;
}
