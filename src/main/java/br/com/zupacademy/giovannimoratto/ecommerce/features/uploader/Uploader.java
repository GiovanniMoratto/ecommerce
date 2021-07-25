package br.com.zupacademy.giovannimoratto.ecommerce.features.uploader;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author giovanni.moratto
 */

@Component
public class Uploader {

    // Send to cloud and return the link
    public Set <String> upload(List <MultipartFile> files) {
        return files.stream().map(file -> "http://ecommerce/files.com/" + file.getOriginalFilename())
                .collect(Collectors.toSet());
    }
}
