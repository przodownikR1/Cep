package pl.java.scalatech.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.exception.StreamOperationException;
import pl.java.scalatech.service.filestorage.pojo.FileData;

import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;
import com.google.common.io.CharStreams;
import com.google.common.io.Files;

/**
 * @author Sławomir Borowiec
 *         Module name : Cep
 *         Creating time : 3 wrz 2014 16:31:18
 */
@Slf4j
public final class FileOperations {
    public final static String FILE_NAME = "filename";
    public final static String FILE_EXT = "fileExt";

    public static byte[] fileToBytes(File f) {
        try (FileInputStream input = new FileInputStream(f)) {
            ByteSource byteSource = Files.asByteSource(f);

            byte[] readBytes = byteSource.read();
            return readBytes;
        } catch (IOException e) {
            log.error(" {} ", e);
            throw new StreamOperationException("convert file to byte [] !!! :", e);
        }
    }

    public static String convertInputStreamToText(InputStream is) {
        String text = null;
        try (final Reader reader = new InputStreamReader(is)) {
            text = CharStreams.toString(reader);
        } catch (IOException e) {
            log.error(" {} ", e);
            throw new StreamOperationException("convert inputStream to String !!! :", e);
        }
        return text;
    }

    public static byte[] convertInputStreamToByte(InputStream is) {
        try {
            return ByteStreams.toByteArray(is);
        } catch (IOException e) {
            log.error("{}", e);
            throw new StreamOperationException("convert inputStream to byte [] !!! :", e);
        }
    }

    public static InputStream convertByteToInputStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }

    public static void createFile(FileData fd) {
        try {
            Files.write(fd.getContent(), new File(fd.getFileName()));
        } catch (IOException e) {
            log.error("{}", e);
            throw new StreamOperationException("create file error !!! ", e);
        }
    }

    public static String getOnlyFileNameFromPath(String path) {
        String fileName;
        fileName = path.substring(path.lastIndexOf("/") + 1, path.length());
        return fileName;
    }

    public static Map<String, String> getNameAndExtFromFile(String fileName) {
        Splitter splitter = Splitter.on('.').omitEmptyStrings().trimResults();
        List<String> parts = splitter.splitToList(fileName);
        Map<String, String> metaFile = Maps.newHashMap();
        metaFile.put(FILE_NAME, parts.get(0));
        metaFile.put(FILE_EXT, parts.get(1));
        return metaFile;
    }

}