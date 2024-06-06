package vn.edu.hcmuaf.fit.travie_api.core.infrastructure.media;

import com.google.cloud.storage.*;
import com.google.common.io.Files;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.hcmuaf.fit.travie_api.core.exception.ServiceBusinessException;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.MediaType;

import java.io.File;
import java.util.UUID;

@Log4j2
@Service
public class FileService {
    @Value("${firebase.storage.bucket}")
    private String firebaseStorageBucket;

    public String upload(MultipartFile file, MediaType type, String childPath) throws Exception {
        String fileName = file.getOriginalFilename();

        if (fileName == null) {
            throw new ServiceBusinessException("File không hợp lệ. Vui lòng chọn lại file.");
        }

        String mediaPath = switch (type) {
            case IMAGE -> {
                if (!fileName.matches("(\\S+(\\.(?i)(jpg|png|gif|bmp))$)")) {
                    throw new ServiceBusinessException("File không hợp lệ. Vui lòng chọn file ảnh.");
                }
                yield "images";
            }
            case VIDEO -> {
                if (!fileName.matches("(\\S+(\\.(?i)(mp4|avi|flv|mov))$)")) {
                    throw new ServiceBusinessException("File không hợp lệ. Vui lòng chọn file video.");
                }
                yield "videos";
            }
        };

        String extension = getExtension(fileName);
        String newFileName = UUID.randomUUID().toString().concat(".").concat(extension);
        String pathFile = mediaPath.concat("/").concat(childPath).concat("/").concat(newFileName);

        File tempFile = converToFile(file, newFileName);
        uploadFile(tempFile, pathFile);
        return fileName;
    }

    public void delete(String fileName) throws Exception {
        try {
            BlobId blobId = BlobId.of(firebaseStorageBucket, fileName);
            Storage storage = StorageOptions.getDefaultInstance().getService();
            storage.delete(blobId);
        } catch (Exception e) {
            log.error("Error deleting file", e);
            throw new ServiceBusinessException("Xảy ra lỗi khi xóa file. Vui lòng thử lại sau.");
        }
    }

    private File converToFile(MultipartFile file, String fileName) throws Exception {
        try {
            File tempFile = new File(fileName);
            file.transferTo(tempFile);
            return tempFile;
        } catch (Exception e) {
            log.error("Error converting file", e);
            throw new ServiceBusinessException("Xảy ra lỗi khi chuyển đổi file. Vui lòng thử lại sau.");
        }
    }

    private void uploadFile(File file, String pathFile) throws Exception {
        try {
            BlobId blobId = BlobId.of(firebaseStorageBucket, pathFile);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();

            Storage storage = StorageOptions.getDefaultInstance().getService();
            storage.createFrom(blobInfo, Files.asByteSource(file).openStream());
        } catch (Exception e) {
            log.error("Error uploading file", e);
            throw new ServiceBusinessException("Xảy ra lỗi khi tải lên file. Vui lòng thử lại sau.");
        }
    }

    private String getExtension(String fileName) {
        return Files.getFileExtension(fileName);
    }
}
