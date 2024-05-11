package com.hackaton.hackatonv100.controller;

import com.hackaton.hackatonv100.service.utilservice.IImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@Tag(name = "Image Controller", description = "Контроллер для получения изображений из БД")
@RequestMapping("/api/image")
public class ImageController {

    private IImageService imageService;

    @GetMapping(value = "{img}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @Operation(description = "Получить изображение по ссылке")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Изображение не найдено")
    })
    public ResponseEntity<byte[]> getImg(@PathVariable("img") String img) {
        if(imageService.imageExists(img)) {
            var image = imageService.getImage(img);
            return ResponseEntity.ok(image.getBytes());

        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
