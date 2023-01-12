package blog.generatedvalue.repository;

import blog.generatedvalue.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("책 저장")
    public void save() throws Exception {
        //given
        Book book = new Book("JPA 원리");

        //when
        Book saveBook = bookRepository.save(book);
        em.flush();

        //then
        Assertions.assertThat(saveBook.getBookId()).isNotNull();

    }
}