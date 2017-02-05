package org.locator.geo.rest.response.util;

import org.locator.geo.rest.response.dto.ErrorResponseDto;
import org.locator.geo.rest.response.dto.SuccessResponseDto;
import org.springframework.stereotype.Component;

@Component
public class HttpResponse {

	public SuccessResponseDto<Object> success(){
		return new SuccessResponseDto<Object>();
	}
	
	public ErrorResponseDto error(){
		return new ErrorResponseDto();
	}
}
