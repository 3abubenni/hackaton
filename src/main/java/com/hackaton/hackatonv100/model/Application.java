package com.hackaton.hackatonv100.model;

import com.hackaton.hackatonv100.model.enums.States;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "_user")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Clan clan;
    private int state;

    public void setState(States state) {
        this.state = state.code;
    }
}
