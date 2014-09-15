package pl.java.scalatech.bootstrap;

import static pl.java.scalatech.util.FileOperations.createFile;
import static pl.java.scalatech.util.FileOperations.fileToBytes;
import static pl.java.scalatech.util.FileOperations.getOnlyFileNameFromPath;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.fest.assertions.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.java.scalatech.config.MongoDBConfig;
import pl.java.scalatech.service.filestorage.FileService;
import pl.java.scalatech.service.filestorage.MongoFileService;
import pl.java.scalatech.service.filestorage.pojo.FileData;

import com.google.common.collect.Maps;

/**
 * @author Sławomir Borowiec 
 * Module name : Cep
 * Creating time :  5 wrz 2014 12:47:00
 
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { MongoDBConfig.class, MongoFileService.class })
@Slf4j
public class MongoDbConfigTest {

    @Resource
    private Environment environment;

    @Autowired
    private FileService fileService;

    @Value("${mongoDBName}")
    private String mongoDBName;

    @Value("${slawek}")
    private String slawek;
    private String fileName;
    private File f;
    private Map<String, String> extra;

    @Before
    public void init() {
        
        fileName = "src/test/resources/2.png";
        f = new File(fileName);
        fileName = getOnlyFileNameFromPath(fileName);

        extra = Maps.newHashMap();
        extra.put("name", "mike");
        extra.put("surname", "tyson");
    }

    @Test
    public void shouldBootstrapWork() {
        log.info("+++      mongoDbName : {}", mongoDBName);
        log.info("+++      slawek : {}", environment.getProperty("slawek"));
        log.info("+++      slawek2 : {}", slawek);
    }
   // @Ignore
    @Test(expected = NullPointerException.class)
    public void a_shouldExceptionThrowWhenFileNotExistsInStorage() {
        log.info("order 1");
        fileService.retrieveFileDataByMD5("f117eda589f22ca5a8910173367d971a");
    }
   // @Ignore
    @Test(expected = NullPointerException.class)
    public void b_shouldExceptionThrowWhenFileNotExistsInStorage_CreateVersion() {
        log.info("order 2");
        fileService.retrieveFileDataByFileName("2.png");
    }

    @Test
    public void c_shouldPutAndRetrieveFile() throws IOException {
        log.info("order 3");
        // given
        Assert.assertNotNull(fileService);
        FileData input = new FileData(fileName, fileToBytes(f), extra);
        // when
        FileData newFileInStorage = fileService.put(input);
        Assertions.assertThat(newFileInStorage.getMd5()).isNotEmpty();
        // then
        FileData fd2 = fileService.retrieveFileDataByFileName(fileName);
        Assertions.assertThat(fd2).isNotNull();
        Assertions.assertThat(fd2.getFileName()).isEqualTo(fileName);
        Assertions.assertThat(fd2.getLength()).isGreaterThan(0);
        // then
        FileData fd1 = fileService.retrieveFileDataByMD5(newFileInStorage.getMd5());
        Assertions.assertThat(fd1).isNotNull();
        Assertions.assertThat(fd1.getFileName()).isEqualTo(fileName);
        Assertions.assertThat(fd1.getLength()).isGreaterThan(0);

    }

    @Test
    public void d_shouldSaveFileFromStorage() {
        log.info("order 4");
        // clean
        FileData fd2 = fileService.retrieveFileDataByFileName(fileName);
        fd2.setFileName("file_from_fd_store.png");
        createFile(fd2);

    }
   // @Ignore
    @Test(expected = NullPointerException.class)
    public void e_shouldRemoveFileFromStorage() {
        log.info("order 5");
        // clean
        fileService.removeFile(fileName);
        fileService.retrieveFileDataByFileName(fileName);

    }
    

}
