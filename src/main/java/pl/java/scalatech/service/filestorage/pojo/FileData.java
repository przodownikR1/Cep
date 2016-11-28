package pl.java.scalatech.service.filestorage.pojo;

import static pl.java.scalatech.util.FileOperations.FILE_EXT;
import static pl.java.scalatech.util.FileOperations.getNameAndExtFromFile;

import java.io.Serializable;
import java.util.Map;

import com.mongodb.gridfs.GridFSDBFile;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "content")
public class FileData implements Serializable {
    public static final String URL_RESOURCE_MAPPING = "/api/resource/md5/{md5}";
    private static final long serialVersionUID = -7570766590890541663L;
    private String fileName;
    private byte[] content;
    private long length;
    private String md5;
    private String type;
    private String login;
    private String resourceUrl;

    public String getType() {
        return getExtFromFile(fileName);
    }

    private Map<String, String> extra;

    public FileData(String fileName, byte[] content, String contentType,String login, Map<String, String> extra) {
        this.fileName = fileName;
        this.content = content;
        this.extra = extra;
        this.type = getExtFromFile(fileName);
        this.login = login;

    }

    public FileData(String fileName, long length, String md5,String contentType, String login, Map<String, String> extra) {
        this.fileName = fileName;
        this.length = length;
        this.md5 = md5;      
        this.extra = extra;
        this.type = getExtFromFile(fileName);
        this.login = login;
    }

    public FileData(String fileName, byte[] content, long length, String md5,String contentType,String login, Map<String, String> extra) {
        this(fileName, content,contentType,login, extra);
        this.length = length;
        this.md5 = md5;
        this.type = getExtFromFile(fileName);
        this.login = login;

    }

    private String getExtFromFile(String fileName) {
        return getNameAndExtFromFile(fileName).get(FILE_EXT);
    }
    
    static FileData createFileDataFromGrid(GridFSDBFile file,String login){
        return new FileData(file.getFilename(), null, file.getLength(), file.getMD5(), file.getContentType(), login, file.getMetaData().toMap());
    }
}
