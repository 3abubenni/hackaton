package com.hackaton.hackatonv100.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class Task {

    enum SolutionStatus {

        CREATED(0),
        TOOK(1),
        SOLVED(2),
        CANCELED(3);


        public final int num;
        SolutionStatus(int num) {
            this.num = num;
        }

    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated
    private SolutionStatus status;
    private int money;
    private int exp;
    private Date createdAt;
    private boolean required;
    @ManyToOne(fetch = FetchType.LAZY)
    private Clan clan;

    public void setStatus(int status) {
        switch (status) {
            case 0 -> this.status = SolutionStatus.CREATED;
            case 1 -> this.status = SolutionStatus.TOOK;
            case 2 -> this.status = SolutionStatus.SOLVED;
            case 3 -> this.status = SolutionStatus.CANCELED;
        }
    }

}
