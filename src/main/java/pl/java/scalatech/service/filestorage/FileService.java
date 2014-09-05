package pl.java.scalatech.service.filestorage;

import pl.java.scalatech.service.filestorage.pojo.FileData;

/**
 * @author Sławomir Borowiec
 *         Module name : Cep
 *         Creating time : 3 wrz 2014 18:09:28
 */
public interface FileService {
    
    void removeFile(String name);

    FileData put(FileData fileData);

    FileData retrieveFileDataByFileName(String fileName);

    FileData retrieveFileDataByMD5(String md5);

}
