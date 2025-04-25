package faculty.degree.controller;

import faculty.degree.entity.NodeTreeDTO;
import faculty.degree.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tree")
public class NodeTreeController {

    @Autowired
    private NodeService nodeService;

    @GetMapping("/discipline/{disciplineId}")
    public List<NodeTreeDTO> getTree(@PathVariable Long disciplineId) {
        return nodeService.getTreeByDisciplineId(disciplineId);
    }
}
