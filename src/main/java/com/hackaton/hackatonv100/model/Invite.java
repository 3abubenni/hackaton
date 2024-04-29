package com.hackaton.hackatonv100.model;

import com.hackaton.hackatonv100.model.enums.States;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Invite {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "_user")
    private User user;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Clan clan;
    private int state;

    public void setState(States state) {
        this.state = state.code;
    }
}