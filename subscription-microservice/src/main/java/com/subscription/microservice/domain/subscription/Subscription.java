package com.subscription.microservice.domain.subscription;


import com.subscription.microservice.dtos.SubscriptionDTO;
import jakarta.persistence.*;
import lombok.*;
import java.util.Objects;


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
    private Boolean formReceived = Boolean.FALSE;

    @Column(length = 10)
    private Boolean formResponseApproved = Boolean.FALSE;

    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status = SubscriptionStatus.IN_PROGRESS;

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

    public Subscription(SubscriptionDTO data){
        this.userId = data.userId();
        this.actionId = data.actionId();
    }
}


