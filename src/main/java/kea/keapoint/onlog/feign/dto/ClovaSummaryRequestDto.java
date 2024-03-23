package kea.keapoint.onlog.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clova Summary API 요청 DTO
 *
 * @see <a href="https://api.ncloud-docs.com/docs/ai-naver-clovasummary-api">Clova Summeary API 명세서</a>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClovaSummaryRequestDto {

    /**
     * 요약할 문서
     */
    private ClovaDocumentObject document;

    /**
     * 요약 옵션
     */
    private ClovaOptionObject option;

    public ClovaSummaryRequestDto(String text) {
        this.document = new ClovaDocumentObject(text);
        this.option = new ClovaOptionObject();
    }
}
