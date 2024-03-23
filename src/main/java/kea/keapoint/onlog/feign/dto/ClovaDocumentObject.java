package kea.keapoint.onlog.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 요약할 문서의 정보를 담는 객체
 *
 * @see <a href="https://api.ncloud-docs.com/docs/ai-naver-clovasummary-api">Clova Summeary API 명세서</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClovaDocumentObject {

    /**
     * 제목
     */
    private String title;

    /**
     * 본문
     */
    private String content;

    public ClovaDocumentObject(String text) {
        this.title = null;
        this.content = text;
    }
}
