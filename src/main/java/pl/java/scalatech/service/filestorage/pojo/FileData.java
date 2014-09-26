package pl.java.scalatech.service.filestorage.pojo;

import static pl.java.scalatech.util.FileOperations.getNameAndExtFromFile;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;
import lombok.ToString;
import pl.java.scalatech.util.FileOperations;

@Data
@ToString(exclude = "content")
public class FileData implements Serializable {

    private static final long serialVersionUID = -7570766590890541663L;
    private String fileName;
    private byte[] content;
    private long length;
    private String md5;
    private String type;
    private String contentType;
    private String login;

    public String getType() {
        return getExtFromFile(fileName);
    }

    private Map<String, String> extra;

    public FileData(String fileName, byte[] content, String contentType,String login, Map<String, String> extra) {
        this.fileName = fileName;
        this.content = content;
        this.extra = extra;
        this.type = getExtFromFile(fileName);

    }

    public FileData(String fileName, long length, String md5,String contentType, String login, Map<String, String> extra) {
        this.fileName = fileName;
        this.length = length;
        this.md5 = md5;
        this.extra = extra;
        this.type = getExtFromFile(fileName);
    }

    public FileData(String fileName, byte[] content, long length, String md5,String contentType,String login, Map<String, String> extra) {
        this(fileName, content,contentType,login, extra);
        this.length = length;
        this.md5 = md5;
        this.type = getExtFromFile(fileName);

    }

    private String getExtFromFile(String fileName) {
        return getNameAndExtFromFile(fileName).get(FileOperations.FILE_EXT);
    }
}
