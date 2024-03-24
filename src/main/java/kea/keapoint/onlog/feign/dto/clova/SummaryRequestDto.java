package kea.keapoint.onlog.feign.dto.clova;

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
public class SummaryRequestDto {

    /**
     * 요약할 문서
     */
    private Document document;

    /**
     * 요약 옵션
     */
    private Option option;

    public SummaryRequestDto(String text) {
        this.document = new Document(text);
        this.option = new Option();
    }
}
