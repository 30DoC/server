package com.thirty.api.dto;

import lombok.*;

import java.io.Serializable;

/**
 * Created by ByeongChan on 2018. 2. 13..
 *
 * 사용자가 등록할 퀴즈 Request Parameter
 */

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistQuiz implements Serializable{
    /**
     * 등록할 질문
     * 등록할 답변
     */

    private String question;

    private boolean answer;

    public static RegistQuiz build(String question, boolean answer){
        return RegistQuiz.builder()
                .question(question)
                .answer(answer)
                .build();
    }
}
