package org.launchcode.capstoneprojectjm.controllers;

import org.launchcode.capstoneprojectjm.models.Data.CategoryDao;
import org.launchcode.capstoneprojectjm.models.Data.EventDao;
import org.launchcode.capstoneprojectjm.models.Data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;




@Controller

public class FileUploadController {

    public FileUploadController() {}

    public static String uploadDirectory = System.getProperty("user.dir")+"/uploads";




    @RequestMapping(value = "/upload-view")
    public String displayUploadView(Model model) {

        return "upload-view";
    }

    @RequestMapping(value = "/upload")
    public String upload(Model model,@RequestParam("files") MultipartFile[] files) {
        StringBuilder fileNames = new StringBuilder();
        for (MultipartFile file : files) {
            Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
            fileNames.append(file.getOriginalFilename()+" ");
            try {
                Files.write(fileNameAndPath, file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("msg", "Successfully uploaded files "+fileNames.toString());
        return "uploadstatusview";
    }


}