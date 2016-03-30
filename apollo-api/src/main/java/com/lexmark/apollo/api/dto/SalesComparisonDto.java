package com.lexmark.apollo.api.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class SalesComparisonDto implements Serializable {

	private static final long serialVersionUID = 3059471826472311418L;
	
	private List<SalesDto> currentSales;
	private List<SalesDto> previousSales;

}
