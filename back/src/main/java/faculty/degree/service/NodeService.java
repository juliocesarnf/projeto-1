package faculty.degree.service;

import faculty.degree.entity.Node;
import faculty.degree.entity.NodeTreeDTO;
import faculty.degree.repository.NodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NodeService {

    @Autowired
    private NodeRepository nodeRepository;

    public List<NodeTreeDTO> getTreeByDisciplineId(Long disciplineId) {
        // Busca todos os nós da disciplina de uma vez só (melhor para performance)
        List<Node> allNodes = nodeRepository.findByDisciplineId(disciplineId);

        // Mapeia os nós por ID
        Map<Long, List<Node>> childrenMap = allNodes.stream()
                .filter(n -> n.getParent() != null)
                .collect(Collectors.groupingBy(n -> n.getParent().getId()));

        // Encontra os nós de topo (sem pai)
        List<Node> rootNodes = allNodes.stream()
                .filter(n -> n.getParent() == null)
                .collect(Collectors.toList());

        // Monta a árvore
        return rootNodes.stream()
                .map(root -> buildTree(root, childrenMap))
                .collect(Collectors.toList());
    }

    private NodeTreeDTO buildTree(Node node, Map<Long, List<Node>> childrenMap) {
        NodeTreeDTO dto = new NodeTreeDTO(node.getId(), node.getName(), node.getType().toString());

        List<Node> children = childrenMap.getOrDefault(node.getId(), new ArrayList<>());
        for (Node child : children) {
            dto.getChildren().add(buildTree(child, childrenMap));
        }

        return dto;
    }
}

