package repository;

import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JogadorRepository extends JpaRepository<Long, Id> {
}
