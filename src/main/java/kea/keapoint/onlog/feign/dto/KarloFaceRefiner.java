package kea.keapoint.onlog.feign.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KarloFaceRefiner {

    /**
     * 얼굴 형태 조정 기능을 적용할 전체 이미지 대비 얼굴 영역의 비율, 실제 얼굴 영역이 설정 크기 이하인 경우만 기능 적용
     */
    @JsonProperty("bbox_size_threshold")
    private Double bboxSizeThreshold = null;

    /**
     * 이미지가 사람의 얼굴인지 여부를 판단하는 임계값, 0인 경우 기능 적용 안함
     */
    @JsonProperty("bbox_filter_threshold")
    private Double bboxFilterThreshold = null;

    /**
     * 얼굴 형태 조정 기능 적용 횟수
     */
    @JsonProperty("restoration_repeats")
    private Double restorationRepeats = null;

    /**
     * 원본 이미지 반영 가중치, 높을수록 원본 이미지 유지 수준 높음
     */
    @JsonProperty("weight_sft")
    private Double weightSft = null;

}
