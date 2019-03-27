package cn.org.easysite.commons.utils;

import org.apache.commons.lang3.RandomUtils;

import java.util.UUID;

public class OrderIdGenerator {

    /**
     * 通过randId生成一个32位的订单号
     * @param randId
     * @return
     */
    public static String generator(Long randId) {
        randId = randId == null ? 0 : randId;
        //对10000取摩避免溢出
        randId = randId % 10000;
        String dateStr = DateUtils.now(DateUtils.DateFormat.SHORT_DATE_PATTERN_SECOND);
        String randomStr = String.valueOf(RandomUtils.nextLong(100000, 899999));
        return dateStr + String.format("%018d", Long.valueOf(randId + randomStr));
    }

    public static Long generatorLongOrder(Long randId) {
        randId = randId == null ? 0 : randId;
        //对10000取摩避免溢出
        randId = (randId + RandomUtils.nextLong(100000, 899999)) % 100000;
        String dateStr = DateUtils.now(DateUtils.DateFormat.SHORT_DATE_PATTERN_SECOND);
        return Long.valueOf(dateStr + randId);
    }

    /**
     * 生成uuid
     * @return
     */
    public static String generatorUUID() {
        return UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
    }

    public static void main(String[] args) {
        System.out.println(generator(1L));
        System.out.println(Long.MAX_VALUE);
        System.out.println(generatorLongOrder(20L));
    }
}
