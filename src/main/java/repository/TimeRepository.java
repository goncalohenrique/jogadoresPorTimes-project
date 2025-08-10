package repository;

import jakarta.persistence.Id;
import model.Times;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeRepository extends JpaRepository<Times, Id> {
}
