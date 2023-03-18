package com.example.txservice.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Persistable;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
@Entity
@Table(indexes = {
        @Index(name = "member_email_index", columnList = "email"),
        @Index(name = "member_user_id_index", columnList = "userId")
})
public class Member extends BaseEntity implements Persistable {

    @Id
    @Column(name = "member_id")
    private String id;

    private String userId;

    private String password;
    private String email;


    @Builder
    public Member(String userId, String password, String email) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.password = password;
        this.email = email;
    }


    /**
     * Returns if the {@code Persistable} is new or was persisted already.
     *
     * @return if {@literal true} the object is new.
     */
    @Override
    public boolean isNew() {
        return super.getCreatedAt() == null;
    }
}
