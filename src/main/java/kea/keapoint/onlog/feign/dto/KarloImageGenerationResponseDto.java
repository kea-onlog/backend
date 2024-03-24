package kea.keapoint.onlog.feign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Karlo 이미지 생성 API 응답 DTO
 *
 * @see <a href="https://developers.kakao.com/docs/latest/ko/karlo/rest-api#text-to-image">Karlo 이미지 생성 API 명세서</a>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KarloImageGenerationResponseDto {

    /**
     * 요청 처리 작업 ID
     */
    private String id;

    /**
     * 요청 처리 시 사용된 칼로 버전
     */
    @JsonProperty("model_version")
    private String modelVersion;

    /**
     * 생성된 이미지 정보를 담은 배열
     */
    private KarloImage[] images;

}
