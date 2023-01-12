package blog.generatedvalue.repository;

import blog.generatedvalue.domain.Computer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ComputerRepositoryTest {

    @Autowired
    ComputerRepository computerRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("isNew를 오버라이딩하여 GeneratedValue 안쓰고 저장하기")
    public void save() throws Exception {
        //given
        Computer linux = new Computer("computer-3001", "linux용");

        //when
        Computer saveComputer = computerRepository.save(linux);
        em.flush();

        //then
        assertThat(saveComputer.getId()).isEqualTo("computer-3001");
    }

}