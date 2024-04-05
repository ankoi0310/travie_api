package vn.edu.hcmuaf.fit.travie_api.core.infrastructure.media;

import vn.edu.hcmuaf.fit.travie_api.core.handler.exception.BaseException;

public interface FileService {
    void removeFromFirebaseStorage(String fileName) throws BaseException;
}
