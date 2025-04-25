package faculty.degree.controller;

import faculty.degree.entity.Discipline;
import faculty.degree.entity.Node;
import faculty.degree.entity.NodeType;
import faculty.degree.repository.DisciplineRepository;
import faculty.degree.repository.NodeRepository;
import faculty.degree.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/nodes")
public class NodeController {

    @Autowired private NodeRepository nodeRepository;
    @Autowired private DisciplineRepository disciplineRepository;
    @Autowired
    private StorageService storageService;

    @PostMapping("/folder")
    public ResponseEntity<?> createFolder(@RequestParam String name,
                                          @RequestParam Long disciplineId,
                                          @RequestParam(required = false) Long parentId) throws IOException {
        Discipline discipline = disciplineRepository.findById(disciplineId).orElseThrow();
        Node parent = parentId != null ? nodeRepository.findById(parentId).orElse(null) : null;

        String relativePath = parent != null ? parent.getFilePath() + "/" + name : "discipline_" + disciplineId + "/" + name;
        String fullPath = storageService.createFolder("", relativePath);

        Node folder = new Node();
        folder.setName(name);
        folder.setType(NodeType.FOLDER);
        folder.setDiscipline(discipline);
        folder.setParent(parent);
        folder.setFilePath(relativePath);

        return ResponseEntity.ok(nodeRepository.save(folder));
    }

    @PostMapping("/file")
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file,
                                        @RequestParam Long disciplineId,
                                        @RequestParam(required = false) Long parentId) throws IOException, IOException {
        Discipline discipline = disciplineRepository.findById(disciplineId).orElseThrow();
        Node parent = parentId != null ? nodeRepository.findById(parentId).orElse(null) : null;

        String relativePath = parent != null ? parent.getFilePath() : "discipline_" + disciplineId;
        String fullPath = storageService.saveFile(file, relativePath);

        Node fileNode = new Node();
        fileNode.setName(file.getOriginalFilename());
        fileNode.setType(NodeType.FILE);
        fileNode.setFilePath(fullPath);
        fileNode.setDiscipline(discipline);
        fileNode.setParent(parent);

        return ResponseEntity.ok(nodeRepository.save(fileNode));
    }
}

