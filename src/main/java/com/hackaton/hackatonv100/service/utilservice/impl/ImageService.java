package com.hackaton.hackatonv100.service.utilservice.impl;

import com.hackaton.hackatonv100.model.Image;
import com.hackaton.hackatonv100.repository.ImageRepository;
import com.hackaton.hackatonv100.service.utilservice.IImageService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
@Log4j
@AllArgsConstructor
public class ImageService implements IImageService {

    private ImageRepository imageRepository;

    @Override
    @Transactional
    public Image getImage(String name) {
        return imageRepository.findByName(name).orElseThrow();
    }

    @Override
    @Transactional
    public Image uploadImage(MultipartFile file) throws IOException {
        String name = file.getName();
        while (imageRepository.existsByName(name)) {
            name += (int) (Math.random() * (1_000_000));
        }

        var image = Image.builder()
                .bytes(file.getBytes())
                .name(name)
                .build();

        return imageRepository.save(image);
    }

    @Override
    public boolean imageExists(String img) {
        return imageRepository.existsByName(img);
    }

    @Override
    public void deleteImg(String img) {
        if(img != null) {
            imageRepository.deleteByName(img);
        }
    }

    @Override
    public void deleteImgs(List<String> names) {
        imageRepository.deleteByNames(names);
    }


}
