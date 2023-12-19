package alotra.milktea.service;

import alotra.milktea.config.StorageProperties;
import alotra.milktea.exception.StorageException;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Blob;

@Service
public class CloudStorageServiceImpl implements IStorageService{
    @Value("${gcp.bucket.name}")
    private String bucketName;
    Storage storage = StorageOptions.getDefaultInstance().getService();
    @Override
    public void init() {

    }

    @Override
    public void delete(String storeFilename) throws Exception {
        Blob blob = (Blob) storage.get(bucketName, storeFilename);
        blob.free();
    }

    @Override
    public Path load(String filename) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Resource loadAsResource(String filename) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void store(MultipartFile file, String storeFilename) {
        BlobId blobId = BlobId.of(bucketName, storeFilename);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
        try {
            Blob blob = (Blob) storage.create(blobInfo, file.getBytes());
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new StorageException("Failed to store", e);
        }
    }

    @Override
    public String getStorageFilename(MultipartFile file, String id) {
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        return "p" + id + "." + ext;
    }
}
