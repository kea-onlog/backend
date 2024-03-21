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
    private String originalText;
    private String translatedText;
    private String sourceLanguage;
    private String targetLanguage;
}
