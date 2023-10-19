package com.subscriptionMicroservice.domain.subscription;


import jakarta.persistence.*;
import lombok.*;
import java.util.Objects;

//TODO: Add equal and hashCode

@Entity(name="Subscription")
@Table(name="Subscription")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(SubscriptionId.class)
public class Subscription {
    @Id
    private Long userId;

    @Id
    private Long actionId;

    @Column(length = 10)
    private Boolean formReceived;

    @Column(length = 10)
    private Boolean formResponseApproved;

    @Enumerated(EnumType.STRING)
    private SubscriptionId status;

    @Override
    public int hashCode() {
        return Objects.hash(userId, actionId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Subscription that = (Subscription) obj;
        return userId.equals(that.userId) && actionId.equals(that.actionId);
    }

}


