package com.hackaton.hackatonv100.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class Member {


    /*
     *
     * Just Bit Flags (google more in Internet)
     * Very useful and simple))
     */
    public enum MemberStatus {

        MEMBER(1),
        CAN_CHECK_TASK(2),
        CAN_CREATE_TASK(4),
        CAN_INVITE_MEMBER(8),
        CAN_REDACT_CLAN(16),
        CAN_DELETE_CLAN(32),
        CAN_KICK_OUT(64),
        CAN_REDACT_MEMBER(128),
        CAN_CHANGE_STATUS(256),
        CAN_MANAGE_ITEMS(512),
        MODERATOR(
                MEMBER,
                CAN_CHECK_TASK,
                CAN_INVITE_MEMBER,
                CAN_CREATE_TASK
        ),
        ADMIN(
                MODERATOR,
                CAN_REDACT_CLAN,
                CAN_DELETE_CLAN,
                CAN_KICK_OUT,
                CAN_REDACT_MEMBER,
                CAN_CHANGE_STATUS,
                CAN_MANAGE_ITEMS
        );

        public final int code;

        MemberStatus(int num) {
            this.code = num;
        }

        MemberStatus(MemberStatus... statuses) {
            int code = 0;
            for (MemberStatus status : statuses) {
                code += status.code;
            }
            this.code = code;
        }

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private User user;
    private int status;
    private int exp;
    private int money;
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Clan clan;
    @OneToMany(cascade = CascadeType.ALL)
    private List<ItemDetails> inventory;

    /*
     *
     *
     * For comfortable using there's a list of methods with checking status
     *
     */
    public void addStatus(MemberStatus... statuses) {
        for (var status : statuses) {
            addStatus(status.code);
        }
    }

    public void addStatus(MemberStatus status) {
        addStatus(status.code);
    }

    public void addStatus(int... nums) {
        for (int num : nums) {
            addStatus(num);
        }
    }

    public void addStatus(int status) {
        this.status = status | this.status;
    }

    public boolean checkStatus(int status) {
        return (status & this.status) == status;
    }

    public boolean checkStatus(MemberStatus status) {
        return checkStatus(status.code);
    }

    public void deleteStatus(int status) {
        this.status -= status | this.status;
    }

    public void deleteStatus(MemberStatus status) {
        deleteStatus(status.code);
    }

    public void deleteStatus(MemberStatus... statuses) {
        for (var status : statuses) {
            deleteStatus(status.code);
        }
    }

    public void addMoney(int money) {
        if (money < 0) {
            throw new RuntimeException("Money cannot be less 0");
        }
        this.money += money;
    }

    public void withdrawMoney(int money) {
        if (money < 0) {
            throw new RuntimeException("Money cannot be less 0");
        } else if (this.money - money < 0) {
            throw new RuntimeException("Member don't have enough money");
        }
        this.money -= money;
    }

    public ItemDetails buyItem(Item item, int count) {
        if (count <= 0) {
            throw new RuntimeException("Count mustn't be less 0");
        } else if (money - item.getCost() * count < 0) {
            throw new RuntimeException("Member don't have enough money");
        }
        money -= item.getCost() * count;
        if (inventory != null) {
            var optional = inventory.stream().filter(i -> i.getItem().equals(item)).findFirst();
            if (optional.isPresent()) {
                var itemDetail = optional.get();
                itemDetail.setAmount(itemDetail.getAmount() + count);
            } else {
                inventory.add(ItemDetails.builder().item(item).amount(count).build());
            }
        } else {
            inventory = List.of(ItemDetails.builder().item(item).amount(count).build());
        }
        return ItemDetails.builder()
                .amount(count)
                .item(item)
                .build();
    }

    public void dropItem(Item item, int count) {
        if (count <= 0) {
            throw new RuntimeException("Count mustn't be less 0");
        }

        if (inventory == null || inventory.stream().noneMatch(i -> i.getItem().equals(item) && i.getAmount() >= count)) {
            throw new RuntimeException("Member don't have enough items");
        }

        var itemDetail = inventory.stream().filter(i -> i.getItem().equals(item)).findFirst().get();
        itemDetail.setAmount(itemDetail.getAmount() - count);
        if(itemDetail.getAmount() == 0) {
            inventory.remove(itemDetail);
        }
    }

}
