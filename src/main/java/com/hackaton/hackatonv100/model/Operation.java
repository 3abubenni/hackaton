package com.hackaton.hackatonv100.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Operation {

    public enum OperationType {

        TASK_REWARD(1),
        TRANSFER_FROM(2),
        TRANSFER_TO(3),
        BUYING_ITEM(4);

        public final int code;

        OperationType(int code) {
            this.code = code;
        }

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int money;
    private Date date;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;
    private int type;

    public void setType(OperationType type) {
        this.type = type.code;
    }

}
