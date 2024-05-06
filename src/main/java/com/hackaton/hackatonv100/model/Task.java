package com.hackaton.hackatonv100.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class Task {

    public enum SolutionStatus {
        CREATED(0),
        TOOK(1),
        //Member solved task and it needs checking
        SOLVED(2),
        //Solution of task was checked and reward was given
        CHECKED(3);
        public final int num;
        SolutionStatus(int num) {
            this.num = num;
        }
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int status;
    private int money;
    private int exp;
    private Date createdAt;
    @NotNull
    private String description;
    @NotNull
    private String name;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
    private Clan clan;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member solver;

    public void setStatus(SolutionStatus status) {
        this.status = status.num;
    }

}
