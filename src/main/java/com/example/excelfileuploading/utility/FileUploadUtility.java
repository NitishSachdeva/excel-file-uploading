package com.example.excelfileuploading.utility;

import com.example.excelfileuploading.interfaces.LogAspect;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class FileUploadUtility {
    //   public final String upload_Dir = "C:\\Users\\Dell\\IdeaProjects\\pdf-file-uploading\\src\\main\\resources\\static\\assets\\pdf-files";
    public final String upload_Dir = new ClassPathResource("static/assets/excel-files").getFile().getPath();

    public FileUploadUtility() throws IOException {
    }

    public boolean uploadFile(MultipartFile multiFile) {
        checkingAspect();
        boolean fileUploaded = false;
        try {
        /*
            InputStream fis = multiFile.getInputStream();
            byte[] data = new byte[fis.available()];
            fis.read(data);
            FileOutputStream fos = new FileOutputStream(upload_Dir+ File.separator+multiFile.getOriginalFilename());
            fos.write(data);
            fos.close();
            fis.close();
            fileUploaded=true;
         */

            Files.copy(multiFile.getInputStream(), Paths.get(upload_Dir + File.separator + multiFile.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            fileUploaded = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUploaded;
    }
    @LogAspect
    public static void  checkingAspect(){
        System.out.println("checking if aspects is working");
    }
}