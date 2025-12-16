package com.portfolio.auth.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponseDto {
	private String message;
}
