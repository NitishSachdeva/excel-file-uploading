package com.example.excelfileuploading.restcontrollers;

import com.example.excelfileuploading.utility.FileUploadUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadRestController {
    @Autowired
    private FileUploadUtility fileUpload;

    @PostMapping(value = "/upload-excel-file",produces = {MediaType.APPLICATION_JSON_VALUE ,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> fileUpload(@RequestParam("file") MultipartFile file) {
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());
        System.out.println(file.getContentType());
        System.out.println(file.getName());
        //try {
        if (file.isEmpty())
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Please select a excel file");

        if (!file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) //for xlsx format
            if (!file.getContentType().equals("application/vnd.ms-excel")) //for xls format
                if (!file.getContentType().equals("text/csv")) //for CSV Format
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Please select only excel file");

        //pdf-upload-logic
        boolean excelUploaded = fileUpload.uploadFile(file);
        if (excelUploaded)
            return ResponseEntity.ok("Excel file uploaded successfully");
        //return ResponseEntity.ok(ServletUriComponentsBuilder.fromCurrentContextPath().path("/pdf-files/").path(file.getOriginalFilename()).toUriString());
      /*  }catch (Exception e){
            e.printStackTrace();
        }*/

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong!There is some error in upload the pdf file, please try again");
    }
}
