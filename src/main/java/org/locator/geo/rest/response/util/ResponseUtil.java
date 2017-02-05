package org.locator.geo.rest.response.util;

import org.locator.geo.rest.response.dto.ErrorResponseDto;
import org.locator.geo.rest.response.dto.SuccessResponseDto;


public class ResponseUtil {

	public static SuccessResponseDto<Object> success(){
		return new SuccessResponseDto<Object>();
	}
	
	public static ErrorResponseDto error(){
		return new ErrorResponseDto();
	}
}
