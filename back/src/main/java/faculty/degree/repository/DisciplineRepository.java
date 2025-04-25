package faculty.degree.repository;

import faculty.degree.entity.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplineRepository extends JpaRepository<Discipline, Long> {

}
