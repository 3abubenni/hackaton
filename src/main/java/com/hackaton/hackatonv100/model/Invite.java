package com.hackaton.hackatonv100.model;

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

    public enum StateInvite {
        CREATED(0),
        ACCEPTED(1),
        CANCELED(2);
        public final int code;
        StateInvite(int code) {
            this.code = code;
        }
    }

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

    public void setState(StateInvite state) {
        this.state = state.code;
    }
}
