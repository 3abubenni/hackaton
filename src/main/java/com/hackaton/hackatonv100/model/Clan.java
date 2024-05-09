package com.hackaton.hackatonv100.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.Map;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = @Index(columnList = "name"))
public class Clan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String name;
    private String description;
    @OneToMany(cascade = CascadeType.ALL)
    private List<ItemDetails> shop;

    public ItemDetails getItemDetailsFromShop(Item item) {
        return shop.stream()
                .filter(itemDetails -> itemDetails.getItem().equals(item))
                .findFirst()
                .get();
    }

    public ItemDetails sellItem(Item item, int amount) {
        var details = getItemDetailsFromShop(item);
        if(details.getAmount() < amount) {
            throw new RuntimeException("Trying to buy items more than clan contains: " +
                    "clan item amount: " + details.getAmount() + " " +
                    "trying to buy: " + amount);
        }

        details.setAmount(details.getAmount() - amount);
        return details;
    }

}
