package blog.generatedvalue.repository;

import blog.generatedvalue.domain.Computer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComputerRepository extends JpaRepository<Computer, String> {
}
