package com.hackaton.hackatonv100.service.utilservice;

import com.hackaton.hackatonv100.model.Image;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface IImageService {

    Image getImage(String name);
    Image uploadImage(MultipartFile file) throws IOException;
    boolean imageExists(String img);
    void deleteImg(String img);
    void deleteImgs(List<String> names);

}
