package br.com.zupacademy.giovannimoratto.ecommerce.add_image;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @Author giovanni.moratto
 */

public class ImageRequest {

    @NotNull
    @Size(min = 1)
    private final List <MultipartFile> images;

    public ImageRequest(List <MultipartFile> images) {
        this.images = images;
    }

    public List <MultipartFile> getImages() {
        return images;
    }

}