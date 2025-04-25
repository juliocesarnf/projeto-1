package faculty.degree.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageService {

    @Value("${storage.base-path}")
    private String basePath;

    public String saveFile(MultipartFile file, String path) throws IOException {
        Path directory = Paths.get(basePath, path);
        Files.createDirectories(directory);

        Path filePath = directory.resolve(file.getOriginalFilename());
        file.transferTo(filePath.toFile());

        return filePath.toString();
    }

    public String createFolder(String path, String folderName) throws IOException {
        Path fullPath = Paths.get(basePath, path, folderName);
        Files.createDirectories(fullPath);
        return fullPath.toString();
    }
}
