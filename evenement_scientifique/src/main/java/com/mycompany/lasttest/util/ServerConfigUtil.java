/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lasttest.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author fatimaAc
 */
public class ServerConfigUtil {

    private static final String UPLOAD_FOLDER = "C:\\Users\\HP\\Documents\\EGDownloads\\EvenementScientifique-master(1)\\EvenementScientifique-master\\lastTest\\src\\main\\webapp\\resources\\articles";

    public static void upload(UploadedFile uploadedFile) {
        try {
            String nameOfUploadedFile = uploadedFile.getFileName();
            File file = new File(nameOfUploadedFile.replace('\\', '/'));
            String fileName = file.getName();
            String fileSavePath = UPLOAD_FOLDER + "\\" + fileName;
            InputStream fileContent = uploadedFile.getInputstream();
            Path path = new File(fileSavePath).toPath();
            System.out.println(path);
            Files.copy(fileContent, path, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            System.out.println("------- error upload file");
            JsfUtil.addErrorMessage(e, "Erreur Upload file ");

        }

    }

}
