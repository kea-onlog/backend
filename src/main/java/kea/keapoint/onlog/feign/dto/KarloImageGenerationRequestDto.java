package kea.keapoint.onlog.feign.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Karlo 이미지 생성 API 요청 DTO
 *
 * @see <a href="https://developers.kakao.com/docs/latest/ko/karlo/rest-api#text-to-image">Karlo 이미지 생성 API 명세서</a>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KarloImageGenerationRequestDto {

    /**
     * 요청에 적용할 칼로 모델 버전
     */
    private String version = "v2.1";

    /**
     * 이미지를 묘사하는 제시어, 영문만 지원
     */
    private String prompt;

    /**
     * 이미지 생성 시 제외할 요소를 묘사하는 부정 제시어, 영문만 지원
     */
    @JsonProperty("negative_prompt")
    private String negativePrompt = null;

    /**
     * 이미지 가로 크기(단위: 픽셀)
     */
    private Integer width = 1024;

    /**
     * 이미지 세로 크기(단위: 픽셀)
     */
    private Integer height = 1024;

    /**
     * 이미지 크기 확대 여부
     */
    private Boolean upscale = true;

    /**
     * 확대 배율
     */
    private Integer scale = 2;

    /**
     * 이미지 파일 형식(기본값: webp)
     */
    @JsonProperty("image_format")
    private String imageFormat = null;

    /**
     * 이미지 저장 품질
     */
    @JsonProperty("image_quality")
    private Integer imageQuality = 100;

    /**
     * 생성할 이미지 수
     */
    private Integer samples = 8;

    /**
     * 응답의 이미지 파일 반환 형식, 다음 중 하나
     * - base64_string: 이미지 파일을 Base64 인코딩한 값
     * - url: 이미지 파일 URL (기본값)
     */
    @JsonProperty("return_type")
    private String returnType = "url";

    /**
     * 이미지 생성 과정의 노이즈 제거 단계 수
     */
    @JsonProperty("prior_num_inference_steps")
    private Integer priorNumInferenceSteps = 100;

    /**
     * 이미지 생성 과정의 노이즈 제거 척도
     */
    @JsonProperty("prior_guidance_scale")
    private Double priorGuidanceScale = null;

    /**
     * 디코더를 통한 노이즈 제거 단계 수
     */
    @JsonProperty("num_inference_steps")
    private Integer numInferenceSteps = null;

    /**
     * 디코더를 통한 노이즈 제거 척도
     */
    @JsonProperty("guidance_scale")
    private Double guidanceScale = null;

    /**
     * 디코더를 통한 노이즈 제거 단계에서 사용할 스케줄러
     */
    private String scheduler = null;

    /**
     * 각 이미지 생성 작업에 사용할 시드(Seed) 값
     */
    private Integer[] seed = null;

    /**
     * 생성할 이미지에 대한 NSFW 검사하기 수행 여부
     */
    @JsonProperty("nsfw_checker")
    private Boolean nsfwChecker = true;

    /**
     * 얼굴 형태 조정 기능 설정 파라미터
     */
    @JsonProperty("face_refiner")
    private KarloFaceRefiner faceRefiner = null;

    public KarloImageGenerationRequestDto(String prompt) {
        this.prompt = prompt;
    }
}
