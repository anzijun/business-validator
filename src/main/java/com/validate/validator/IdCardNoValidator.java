package com.validate.validator;

import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author anzj
 * @date 2022/12/20 14:26
 */
public class IdCardNoValidator extends ValidatorHandler<String> implements Validator<String> {
    private final static int[] PARITYBIT = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };
    private final static int[] POWER_LIST = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

    private final static Map<Integer, String> zoneNum = new HashMap<Integer, String>();
    static {//注意文件为UTF-8
        zoneNum.put(11, "北京");
        zoneNum.put(12, "天津");
        zoneNum.put(13, "河北");
        zoneNum.put(14, "山西");
        zoneNum.put(15, "内蒙古");
        zoneNum.put(21, "辽宁");
        zoneNum.put(22, "吉林");
        zoneNum.put(23, "黑龙江");
        zoneNum.put(31, "上海");
        zoneNum.put(32, "江苏");
        zoneNum.put(33, "浙江");
        zoneNum.put(34, "安徽");
        zoneNum.put(35, "福建");
        zoneNum.put(36, "江西");
        zoneNum.put(37, "山东");
        zoneNum.put(41, "河南");
        zoneNum.put(42, "湖北");
        zoneNum.put(43, "湖南");
        zoneNum.put(44, "广东");
        zoneNum.put(45, "广西");
        zoneNum.put(46, "海南");
        zoneNum.put(50, "重庆");
        zoneNum.put(51, "四川");
        zoneNum.put(52, "贵州");
        zoneNum.put(53, "云南");
        zoneNum.put(54, "西藏");
        zoneNum.put(61, "陕西");
        zoneNum.put(62, "甘肃");
        zoneNum.put(63, "青海");
        zoneNum.put(64, "宁夏");
        zoneNum.put(65, "新疆");
        zoneNum.put(71, "台湾");
        zoneNum.put(81, "香港");
        zoneNum.put(82, "澳门");
        zoneNum.put(91, "外国");
    }


    @Override
    public boolean validate(ValidatorContext context, String s) {
        if (s == null )
            return false;

        if (s.length() == 0 || s.length()!=18) {
            return false;
        }
        // 校验位数
        final char[] idCardCharArr = s.toUpperCase().toCharArray();
        int power = 0;
        for (int i = 0; i < idCardCharArr.length; i++) {
            if (i == idCardCharArr.length - 1 && idCardCharArr[i] == 'X')
                break;// 最后一位可以 是X或x
            if (i < idCardCharArr.length - 1) {
                power += (idCardCharArr[i] - '0') * POWER_LIST[i];
            }
        }
        boolean check=idCardCharArr[idCardCharArr.length - 1] == PARITYBIT[power % 11];
        if(!check)
            return false;

        // 校验区位码
        if (!zoneNum.containsKey(Integer.valueOf(s.substring(0, 2)))) {
            return false;
        }
        // 校验年份
        String year = null;
        year = s.substring(6, 10);
        final int iyear = Integer.parseInt(year);
        if (iyear < 1900 || iyear > Calendar.getInstance().get(Calendar.YEAR))
            return false;// 1900年的PASS，超过今年的PASS

        // 校验月份
        String month = s.substring(10, 12);
        final int imonth = Integer.parseInt(month);
        if (imonth < 1 || imonth > 12) {
            return false;
        }
        // 校验天数
        String day = s.substring(12, 14);
        final int iday = Integer.parseInt(day);
        if (iday < 1 || iday > 31)
            return false;
        //格式
        String reg = "^\\d{15}$|^\\d{17}[0-9Xx]$";
        if (!s.matches(reg)) {
            return false;
        }

        return true;
    }
}
