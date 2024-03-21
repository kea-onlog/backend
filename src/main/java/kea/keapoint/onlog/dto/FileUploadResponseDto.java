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
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;
    private LocalDateTime uploadTime;
}
