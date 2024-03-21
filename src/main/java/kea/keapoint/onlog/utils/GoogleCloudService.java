package kea.keapoint.onlog.utils;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import kea.keapoint.onlog.dto.FileUploadResponseDto;
import kea.keapoint.onlog.dto.TranslationResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Service
public class GoogleCloudService {

    @Value("${gcp.translation.credentials.location}")
    private String translationCredentialsLocation;

    @Value("${gcp.storage.credentials.location}")
    private String cloudStorageCredentialsLocation;

    @Value("${gcp.storage.bucket-name}")
    private String cloudStorageBucketName;

    public TranslationResponseDto translation(String text, String sourceLanguage, String targetLanguage) throws IOException {
        log.debug("Google Cloud Service를 이용한 번역을 수행합니다.");

        try {
            // 번역 서비스 설정
            Translate translate = TranslateOptions.newBuilder()
                    .setCredentials(
                            GoogleCredentials.fromStream(ResourceUtils.getURL(translationCredentialsLocation).openStream())
                    )
                    .build()
                    .getService();

            // 번역
            Translation translation = translate.translate(
                    text,
                    Translate.TranslateOption.sourceLanguage(sourceLanguage),
                    Translate.TranslateOption.targetLanguage(targetLanguage)
            );

            // 번역 결과 반환
            return TranslationResponseDto.builder()
                    .originalText(text)
                    .translatedText(translation.getTranslatedText())
                    .sourceLanguage(sourceLanguage)
                    .targetLanguage(targetLanguage)
                    .build();

        } catch (Exception e) {
            log.error("Google Cloud Service를 이용한 번역 중 오류가 발생했습니다.", e);
            throw e;
        }
    }

    public FileUploadResponseDto uploadFile(String filePath, MultipartFile file) throws IOException {
        log.debug("Google Cloud Service에 파일을 업로드합니다.");

        try {
            // 파일 정보
            String originalFileName = file.getOriginalFilename(); // 파일명
            String contentType = file.getContentType(); // 파일 타입
            long fileSize = file.getSize(); // 파일 사이즈
            String randomUUID = java.util.UUID.randomUUID().toString(); // 파일명 중복 방지를 위한 UUID
            String fileName = filePath + '/' + randomUUID + '_' + originalFileName; // bucket에 저장될 파일명

            // GCP Cloud Storage에 파일 업로드
            Storage storage = StorageOptions.newBuilder()
                    .setCredentials(
                            GoogleCredentials.fromStream(ResourceUtils.getURL(cloudStorageCredentialsLocation).openStream())
                    )
                    .build()
                    .getService();

            BlobInfo blobInfo = BlobInfo.newBuilder(cloudStorageBucketName, fileName)
                    .setContentType(contentType)
                    .build();

            storage.create(blobInfo, file.getBytes());

            // 업로드된 파일 경로 반환
            return FileUploadResponseDto.builder()
                    .fileName(fileName)
                    .fileDownloadUri("https://storage.googleapis.com/" + cloudStorageBucketName + "/" + fileName)
                    .fileType(contentType)
                    .size(fileSize)
                    .uploadTime(LocalDateTime.now())
                    .build();

        } catch (Exception e) {
            log.error("Google Cloud Service에 파일 업로드 중 오류가 발생했습니다.", e);
            throw e;
        }
    }

}
