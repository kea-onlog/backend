package kea.keapoint.onlog.feign.dto.clova;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clova Summary API 응답 DTO
 *
 * @see <a href="https://api.ncloud-docs.com/docs/ai-naver-clovasummary-api">Clova Summeary API 명세서</a>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SummaryResponseDto {

    /**
     * 요약된 문서
     */
    private String summary;
}
