package com.hackaton.hackatonv100.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import jdk.jfr.Enabled;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Valid
@Enabled
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Size(min = 3, max = 40)
    private String name;
    private String description;
    private int cost;
    private String img;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    private Clan clan;


    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Item) {
            return ((Item) obj).id.equals(id);
        } else {
            return false;
        }
    }
}
