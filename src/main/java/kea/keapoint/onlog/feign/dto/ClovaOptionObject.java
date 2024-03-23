package kea.keapoint.onlog.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 요약 옵션의 정보를 담는 객체
 *
 * @see <a href="https://api.ncloud-docs.com/docs/ai-naver-clovasummary-api">Clova Summeary API 명세서</a>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClovaOptionObject {

    /**
     * 문서의 언어
     */
    private String language = "ko";

    /**
     * 요약에 사용할 모델
     */
    private String model = "general";

    /**
     * 요약된 문서의 어투
     */
    private int tone = 0;

    /**
     * 요약된 문서의 문장 수
     */
    private int summaryCount = 3;
}
