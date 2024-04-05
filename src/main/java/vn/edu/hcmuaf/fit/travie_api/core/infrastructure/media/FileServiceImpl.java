package vn.edu.hcmuaf.fit.travie_api.core.infrastructure.media;

import com.google.cloud.storage.BlobId;
import com.google.firebase.cloud.StorageClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.fit.travie_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.core.handler.exception.ServiceBusinessException;

@Log4j2
@Service
public class FileServiceImpl implements FileService {
    @Value("${firebase.storage.bucket}")
    private String firebaseStorageBucket;

    public void removeFromFirebaseStorage(String fileName) throws BaseException {
        try {
            BlobId blobId = BlobId.of(firebaseStorageBucket, fileName);
            StorageClient.getInstance().bucket().get(blobId.getName()).delete();
        } catch (Exception e) {
            log.error("Không thể xóa tệp.");
            throw new ServiceBusinessException("Không thể xóa tệp.");
        }
    }
}
