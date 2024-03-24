package kea.keapoint.onlog.feign.dto.karlo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Karlo 이미지 정보를 담고 있는 객체
 *
 * @see <a href="https://developers.kakao.com/docs/latest/ko/karlo/rest-api#text-to-image">Karlo 이미지 생성 API 명세서</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    /**
     * 이미지 ID
     */
    private String id;

    /**
     * 이미지 생성 시 사용된 시드 값. 재요청을 통해 같은 콘텐츠의 이미지를 다시 생성하는 데 사용
     */
    private Long seed;

    /**
     * 이미지 파일 (return_type 파라미터 값에 따라 Base64 인코딩한 값, 또는 이미지 파일 URL)
     */
    @JsonProperty("image")
    private String file;

    /**
     * 이미지의 NSFW 콘텐츠 포함 여부
     */
    @JsonProperty("nsfw_content_detected")
    private Boolean nsfwContentDetected;

    /**
     * 이미지의 NSFW 콘텐츠 포함 확률
     */
    @JsonProperty("nsfw_score")
    private Double nsfwScore;
}
