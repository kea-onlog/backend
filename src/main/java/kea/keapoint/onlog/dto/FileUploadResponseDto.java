package kea.keapoint.onlog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadResponseDto {
    private String fileName; // 파일 이름
    private String fileDownloadUri; // 파일 다운로드 URI
    private String fileType; // 파일 타입
    private long size; // 파일 크기
    private LocalDateTime uploadTime; // 파일 업로드 시간
}
