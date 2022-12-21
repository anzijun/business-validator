package com.validate.validator;

import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author anzj
 * @date 2022/12/21 14:45
 */
public class UnifiedSocialCreditIdentifierValidator extends ValidatorHandler<String> implements Validator<String> {
    private static final String REGEX = "[1-9NY]{1}[1-9]{1}[1-6]{1}[0-9]{5}[0123456789ABCDEFGHJKLMNPQRTUWXY]{10}";

    private static final int[] WEIGHT = { 1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28 };

    private static final String BASE_CODE_STRING = "0123456789ABCDEFGHJKLMNPQRTUWXY";

    private static final char[] BASE_CODE_ARRAY = BASE_CODE_STRING.toCharArray();

    private static final List<Character> BASE_CODES = new ArrayList<>();

    /**
     * 登记管理部门+机构类别代码
     */
    private static final List<String> REGIST_CODE = Arrays.asList(
            "11", "12", "13", "19",
            "21", "29",
            "31", "32", "33", "34", "35", "39",
            "41", "49",
            "51", "52", "53", "59",
            "61", "62", "69",
            "71", "72", "79",
            "81", "89",
            "91", "92", "93",
            "A1", "A9",
            "N1", "N2", "N3", "N9",
            "Y1"
    );

    /**
     * 省级行政区划代码
     */
    private static final List<String> PROVINCE_REGION_CODE = Arrays.asList(
            "11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43", "44", "45", "46",
            "50", "51", "52", "53", "54", "61", "62", "63", "64", "65", "71", "81", "82", "91"
    );
    static {
        for (char c : BASE_CODE_ARRAY) {
            BASE_CODES.add(c);
        }
    }

    @Override
    public boolean validate(ValidatorContext context, String s) {
        if(s==null){
            return false;
        }
        if (s.length() == 0 || s.length()!=18) {
            return false;
        }
        char[] businessCodeArray = s.toCharArray();
        String check = Character.toString(businessCodeArray[17]);
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            char key = businessCodeArray[i];
            sum += (BASE_CODES.indexOf(key) * WEIGHT[i]);
        }
        int checkCode = 31 - sum % 31;
        String s1 = Character.toString(BASE_CODE_ARRAY[checkCode % 31]);
        //对比时将两个字符转为字符串，不区分大小写对比最后一位
        if(!s1.equalsIgnoreCase(check))
            return false;

        if (!Pattern.matches(REGEX, s)) {
            return false;
        }
        if (!PROVINCE_REGION_CODE.contains(s.substring(2, 4))) {
            return false;
        }
        if (!REGIST_CODE.contains(s.substring(0, 2))) {
            return false;
        }

        return super.validate(context, s);
    }
}
