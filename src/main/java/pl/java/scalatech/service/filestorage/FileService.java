package pl.java.scalatech.service.filestorage;

import java.util.List;

import pl.java.scalatech.service.filestorage.pojo.FileData;

/**
 * @author Sławomir Borowiec
 *         Module name : Cep
 *         Creating time : 3 wrz 2014 18:09:28
 */
public interface FileService {
    
    void removeFile(String name,String login);

    FileData put(FileData fileData,String login);

    FileData retrieveFileDataByFileName(String fileName);

    FileData retrieveFileDataByMD5(String md5);
    
    List<FileData> retrieveFileDateByLogin(String login);
    
    List<FileData> findAll();

}
