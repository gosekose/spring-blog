package blog.generatedvalue.repository;

import blog.generatedvalue.domain.Music;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MusicRepositoryTest {

    @Autowired
    MusicRepository musicRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("GeneratedValue를 안쓰고 repository에 저장하기")
    public void save() throws Exception {
        //given
        Music hypeBoy = new Music("music-3001", "Hype boy");

        //when
        Music saveHypeBoy = musicRepository.save(hypeBoy);
        em.flush();

        //then
        Assertions.assertThat(saveHypeBoy.getMusicSeq()).isEqualTo("music-3001");
    }

}