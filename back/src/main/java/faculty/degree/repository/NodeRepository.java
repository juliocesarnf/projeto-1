package faculty.degree.repository;

import faculty.degree.entity.Node;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NodeRepository extends JpaRepository<Node, Long> {
    List<Node> findByDisciplineId(Long disciplineId);
}

