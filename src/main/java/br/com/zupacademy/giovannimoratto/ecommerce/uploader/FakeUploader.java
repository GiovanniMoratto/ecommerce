package br.com.zupacademy.giovannimoratto.ecommerce.uploader;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author giovanni.moratto
 */

@Component
public class FakeUploader {

    public Set <String> sendToCloudAndReturnLinks(List <MultipartFile> images) {
        return images.stream().map(image -> "http://bucket.io/" + image.getOriginalFilename())
                .collect(Collectors.toSet());
    }
}
