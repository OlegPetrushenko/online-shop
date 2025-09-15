package ait.cohort63.online_shop.controller;

import ait.cohort63.online_shop.exception_handling.Response;
import ait.cohort63.online_shop.service.interfaces.FileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    public Response uploadFile(
            @RequestParam MultipartFile file,
            @RequestParam String productTitle) {

        String url = fileService.upload(file, productTitle);

        return new Response("Upload successful", url);
    }
}
