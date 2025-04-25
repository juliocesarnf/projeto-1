package faculty.degree.entity;

import java.util.ArrayList;
import java.util.List;

public class NodeTreeDTO {
    private Long id;
    private String name;
    private String type; // FILE ou FOLDER
    private List<NodeTreeDTO> children = new ArrayList<>();

    public NodeTreeDTO(Long id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<NodeTreeDTO> getChildren() {
        return children;
    }

    public void setChildren(List<NodeTreeDTO> children) {
        this.children = children;
    }
}

