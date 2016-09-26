package com.litkowska.martyna.hairdresser.app.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Martyna on 21.09.2016.
 */
@Entity
public class Hairdresser {
    @Id
    private long id;
    @Column
    private HairdresserLevel hairdresserLevel;
    @OneToMany
    private List<HairdresserTag> hairdresserTagList;
}
