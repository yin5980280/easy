package cn.org.easysite.commons.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yinlin
 *
 */
@Slf4j
public class FormatUtils {

	private static final DecimalFormat DECIMAL_FORMAT = new  DecimalFormat("#####0.00");

	/**
	 * 转换为人民币形式保留两位小数
	 * @param money 钱
	 * @return
	 */
	public static String formatMoney(BigDecimal money){
		return DECIMAL_FORMAT.format(money);
	}

}
