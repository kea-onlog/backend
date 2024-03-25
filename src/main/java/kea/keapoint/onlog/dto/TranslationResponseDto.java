package kea.keapoint.onlog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TranslationResponseDto {
    private String originalText; // 번역 전 텍스트
    private String translatedText; // 번역 후 텍스트
    private String sourceLanguage; // 번역 전 언어
    private String targetLanguage; // 번역 후 언어
}
