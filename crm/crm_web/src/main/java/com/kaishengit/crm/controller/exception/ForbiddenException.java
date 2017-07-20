package com.kaishengit.crm.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by zjs on 2017/7/20.
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN ,reason = "权限不足")
public class ForbiddenException extends RuntimeException{
}
