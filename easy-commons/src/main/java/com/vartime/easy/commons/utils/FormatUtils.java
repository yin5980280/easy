package com.vartime.easy.commons.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FormatUtils {

	private static final DecimalFormat DECIMAL_FORMAT = new  DecimalFormat("#####0.00");

	public static final String LONG_DATE_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";

	public static String formatMoney(BigDecimal money){
		return DECIMAL_FORMAT.format(money);
	}

}
