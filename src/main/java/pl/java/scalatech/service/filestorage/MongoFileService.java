package pl.java.scalatech.service.filestorage;

import static pl.java.scalatech.util.FileOperations.convertByteToInputStream;
import static pl.java.scalatech.util.FileOperations.convertInputStreamToByte;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import com.mongodb.gridfs.GridFSInputFile;

import static java.util.stream.Collectors.toList;

import static com.google.common.base.Preconditions.checkNotNull;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.service.filestorage.pojo.FileData;
import pl.java.scalatech.util.FileOperations;

/**
 * @author Sławomir Borowiec
 *         Module name : Cep
 *         Creating time : 11 wrz 2014 11:37:16
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MongoFileService implements FileService {

    @Autowired
    private final @NonNull DB mongoDB;

    @Autowired
    private final @NonNull MongoTemplate mongoTemplate;

    @Autowired
    private final @NonNull GridFsTemplate gridFsTemplate;

    private GridFS gfs;

    @PostConstruct
    public void postConstruct() {
        gfs = new GridFS(mongoDB);
    }

    @Override
    public FileData put(FileData input, String login) {
        GridFSFile gridFsFile = null;
        DBObject metaData = mapToMetaData(input.getExtra());
        gridFsFile = gridFsTemplate.store(convertByteToInputStream(input.getContent()), input.getFileName(), input.getType(), metaData);
        log.debug("md5   : {}  , login : {}", gridFsFile.getMD5(), login);
        @SuppressWarnings("unchecked")
        FileData newFile = new FileData(gridFsFile.getFilename(), gridFsFile.getLength(), gridFsFile.getMD5(), gridFsFile.getContentType(), login,
                gridFsFile.getMetaData().toMap());
        return newFile;
    }

    @Override
    public List<FileData> findAll() {
        List<GridFSDBFile> files = this.gridFsTemplate.find(queryFileAll());
        return wrapFileData("", files);
    }

    private Query queryFileAll() {
        return (new Query());
    }

    private DBObject mapToMetaData(Map<String, String> addInfoMap) {
        DBObject metaData = new BasicDBObject();
        metaData.putAll(addInfoMap);
        return metaData;
    }

    public FileData saveFile(FileData fileData, String login) {
        GridFSInputFile file = gfs.createFile(fileData.getContent());
        file.setFilename(fileData.getFileName());
        file.save();
        @SuppressWarnings("unchecked")
        FileData newFile = new FileData(fileData.getFileName(), fileData.getContent(), file.getLength(), file.getMD5(), file.getContentType(), login,
                file.getMetaData().toMap());
        return newFile;
    }

    public byte[] getFile(String md5) {
        DBObject query = new BasicDBObject("md5", md5);
        return convertInputStreamToByte(gfs.findOne(query).getInputStream());
    }

    @Override
    public void removeFile(String name, String login) {
        gfs.remove(name);
    }

    @Override
    public FileData retrieveFileDataByFileName(String fileName) {
        GridFSDBFile file = gridFsTemplate.findOne(queryFileDemandByFileName(fileName));
        checkNotNull(file, "File not exists in fs storage");

        log.debug("file content type {}", file.getContentType());
        log.debug("file meta :  {}", file.getMetaData());

         
        @SuppressWarnings("unchecked")
        FileData fileData = new FileData(file.getFilename(), convertInputStreamToByte(file.getInputStream()), file.getLength(), file.getContentType(), file.getMetaData().get("login").toString(),
                file.getMD5(), file.getMetaData().toMap());

        return fileData;
    }

    private Query queryFileDemandByFileName(String fileName) {
        return (new Query().addCriteria(Criteria.where(FileOperations.FILE_NAME).is(fileName)));
    }

    @Override
    public FileData retrieveFileDataByMD5(String md5) {
        GridFSDBFile file = this.gridFsTemplate.findOne(queryFileDemandByMd5(md5));
        checkNotNull(file, "File not exists in fs storage");
        log.debug("file content type {}", file.getContentType());
        log.debug("file meta :  {}", file.getMetaData());

        byte[] content = convertInputStreamToByte(file.getInputStream());
        log.debug("+++++++++++++ content size {}", content.length);
        @SuppressWarnings("unchecked")
        FileData result = new FileData(file.getFilename(), content, file.getLength(), file.getMD5(), file.getContentType(), file.getMetaData().get("login").toString(), file.getMetaData().toMap());
        return result;
    }

    private Query queryFileDemandByMd5(String md5) {
        return (new Query().addCriteria(Criteria.where("md5").is(md5)));
    }

    @Override
    public List<FileData> retrieveFileDateByLogin(String login) {
        List<GridFSDBFile> files = this.gridFsTemplate.find(queryFileDemandByLogin(login));
        checkNotNull(files, "File not exists in fs storage");
        log.debug("file content type {}", files.stream().map(t -> t.getContentType()).collect(toList()));
        log.debug("file meta :  {}", files.stream().map(t -> t.getMetaData().toString()).collect(toList()));
        return wrapFileData(login, files);
    }

    private List<FileData> wrapFileData(String login, List<GridFSDBFile> files) {
        Function<GridFSDBFile, FileData> mapGridToData = file -> {
            FileData result = new FileData(file.getFilename(), file.getLength(), file.getMD5(), file.getContentType(), login, file.getMetaData().toMap());
            return result;
        };
        List<FileData> resultFileData = files.stream().map(mapGridToData).collect(toList());
        return resultFileData;
    }

    private Query queryFileDemandByLogin(String login) {
        return (new Query().addCriteria(Criteria.where("login").is(login)));
    }

}