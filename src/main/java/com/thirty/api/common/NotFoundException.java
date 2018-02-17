package com.thirty.api.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by ByeongChan on 2018. 2. 17..
 */

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason = "NOT FOUND ROOM") // 404
public class NotFoundException extends RuntimeException{

}
