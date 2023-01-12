package blog.generatedvalue.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;

@Entity
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Computer extends BaseTimeEntity implements Persistable<String> {

    @Id
    private String computerSeq;

    private String computerName;

    @Override
    public String getId() {
        return computerSeq;
    }

    @Override
    public boolean isNew() {
        return super.getCreatedDate() == null;
    }
}
