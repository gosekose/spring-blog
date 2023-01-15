package blog.exception.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue
    private Long id;
    private String email;
    private String password;

    @Builder
    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
