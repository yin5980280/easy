package cn.org.easysite.commons.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.org.easysite.commons.constants.BaseConstants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FormatUtils {

	private static final DecimalFormat DECIMAL_FORMAT = new  DecimalFormat("#####0.00");

	public static final String LONG_DATE_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";

	public static String formatMoney(BigDecimal money){
		return DECIMAL_FORMAT.format(money);
	}

	/**
	 * <p>字符串转日期
	 * 2013-9-17 下午3:15:59 edit by LiangBin
	 *
	 * @param dateStr
	 * @param dateStyle
	 * @return
	 */
	public static Date parseDate(String dateStr, String dateStyle) {
		if (StringUtils.isBlank(dateStr)) {
			return null;
		}
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(dateStyle);
			dateFormat.setTimeZone(BaseConstants.TIME_ZONE);
			return dateFormat.parse(dateStr);
		} catch (ParseException e) {
			log.error(e.getLocalizedMessage());
		}
		return null;
	}
}
