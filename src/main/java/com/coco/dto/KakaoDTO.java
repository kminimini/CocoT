package com.coco.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class KakaoDTO {

    private String id;
    private String email;
    private String nickname;

}