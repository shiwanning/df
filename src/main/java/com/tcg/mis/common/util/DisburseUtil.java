package com.tcg.mis.common.util;


import com.tcg.mis.common.log.TcgLogFactory;
import com.tcg.mis.common.response.Period;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Description: disburse util <br/>
 *
 * @author Eddie
 */
public class DisburseUtil {
    private static final Logger LOGGER = TcgLogFactory.getLogger(DisburseUtil.class);
    private static final String WEEK = "W";
    private static final String HALF_MONTH = "H";
    private static final String MONTH = "M";

    public static Period getPeriod(String frequency) {
        DateTime now = DateTime.now();
        Date startDate = DateUtil.getStartTime(now.toDate());
        Date endDate = DateUtil.getStartTime(now.toDate());
        frequency = StringUtils.upperCase(frequency);
        switch (frequency) {
            case WEEK:
                // sun to sat
                // joda return Monday to Sunday
                startDate = now.withDayOfWeek(DateTimeConstants.MONDAY).minusDays(1).toDate();
                endDate = now.withDayOfWeek(DateTimeConstants.SATURDAY).toDate();
                break;
            case HALF_MONTH:
                // 1-15 or 16 - 30
                int monthOfDay = now.getDayOfMonth();
                if (monthOfDay <= 15) {
                    startDate = now.withDayOfMonth(1).toDate();
                    endDate = now.withDayOfMonth(15).toDate();
                } else {
                    startDate = now.withDayOfMonth(16).toDate();
                    endDate = now.dayOfMonth().withMaximumValue().toDate();
                }
                break;
            case MONTH:
                // 1st to 30th, 31th
                startDate = now.dayOfMonth().withMinimumValue().toDate();
                endDate = now.dayOfMonth().withMaximumValue().toDate();
                break;
            default:
                LOGGER.warn("Not support frequency: {}", frequency);
        }
        return new Period(startDate, endDate);
    }

    public static boolean bringForwardLastNetProfit(String resetFrequency, String frequency, Date endDate) {
        if ("1".equals(resetFrequency)) {
            return true;
        }

        int endDay = new DateTime(endDate).getDayOfMonth();
        return "2".equals(resetFrequency) && HALF_MONTH.equals(frequency) && endDay > 15;
    }

    public static BigDecimal getProductChargeValue(BigDecimal teamBetting, BigDecimal teamWinning,
                                                   BigDecimal merchantCharge, BigDecimal minimumCharge, BigDecimal maximumCharge) {

        BigDecimal productChargeValue = BigDecimal.ZERO;
        if ((teamBetting.compareTo(BigDecimal.ZERO) != 0 || teamWinning.compareTo(BigDecimal.ZERO) != 0)
            && merchantCharge.compareTo(BigDecimal.ZERO) != 0) {
            // 公式: (bet - win) / 100, 目前只保留小数点4位
            productChargeValue = ((teamBetting.subtract(teamWinning)).multiply(merchantCharge).divide(BigDecimal.valueOf(100))).setScale(4, BigDecimal.ROUND_DOWN);
            if (productChargeValue.compareTo(minimumCharge) < 0) {
                return minimumCharge;
            } else if (productChargeValue.compareTo(maximumCharge) > 0) {
                return maximumCharge;
            }
        }

        return productChargeValue;

    }

}
