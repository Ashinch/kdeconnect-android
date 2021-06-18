/*
    🍬 SmsCode is the non-xposed version of XposedSmsCode,
    see <https://github.com/tianma8023/SmsCode/>.

    Copyright (C) 2020 tianma8023

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

    --------------------------------------------------------------------------------
    Modifier: Ashinch
    Email: Ashinch@outlook.it
 */

package org.kde.kdeconnect.Helpers.SmsCodeHelpers;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证码相关Utils
 */
public class SmsCodeUtils {
    interface SmsCodeConst {

        String VERIFICATION_KEYWORDS_REGEX =
                /**/   "验证码|校验码|检验码|确认码|激活码|动态码|安全码" +
                /**/  "|验证代码|校验代码|检验代码|激活代码|确认代码|动态代码|安全代码" +
                /**/  "|登入码|认证码|识别码" +
                /**/  "|短信口令|动态密码|交易码|上网密码|随机码|动态口令|代码" +
                /**/  "|驗證碼|校驗碼|檢驗碼|確認碼|激活碼|動態碼" +
                /**/  "|驗證代碼|校驗代碼|檢驗代碼|確認代碼|激活代碼|動態代碼" +
                /**/  "|登入碼|認證碼|識別碼" +
                /**/  "|Code|code|CODE" +
                /* 匹配 Apple 中文短信 */ "|Apple.*ID";

        String PHONE_NUMBER_KEYWORDS =
                /**/ "手机号|电话号" +
                /**/ "|手機號|電話號" +
                /*(?i) 表示忽略大小写*/ "|(?i)phone(?-i)" +
                /*(?i) 表示忽略大小写*/ "|(?i)number(?-i)";
    }

    private SmsCodeUtils() {
    }

    /**
     * 是否包含中文
     *
     * @param text text
     */
    private static boolean containsChinese(String text) {
        String regex = "[\u4e00-\u9fa5]|。";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }

    /**
     * 是否包含验证码短信关键字
     *
     * @param content content
     */
    public static boolean containsCodeKeywords(String content) {
        String keywordsRegex = loadCodeKeywords();
        String keyword = parseKeyword(keywordsRegex, content);
        return !TextUtils.isEmpty(keyword);
    }

    /**
     * 解析文本内容中的验证码关键字，如果有则返回第一个匹配到的关键字，否则返回 空字符串
     */
    private static String parseKeyword(String keywordsRegex, String content) {
        Pattern pattern = Pattern.compile(keywordsRegex);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    private static String loadCodeKeywords() {
        return SmsCodeConst.VERIFICATION_KEYWORDS_REGEX;
    }

    /**
     * 解析文本中的验证码并返回，如果不存在返回空字符
     */
    public static String parseSmsCodeIfExists(String content) {
//        String result = parseByCustomRules(context, content);
//        if (TextUtils.isEmpty(result)) {
//            result = parseByDefaultRule(content);
//        }
//        return result;
        return parseByDefaultRule(content);
    }

    /**
     * Parse SMS code by default rule
     *
     * @param content message body
     * @return the SMS code if matches, otherwise return empty string
     */
    private static String parseByDefaultRule(String content) {
        String result = "";
        String keywordsRegex = loadCodeKeywords();
        String keyword = parseKeyword(keywordsRegex, content);
        if (!TextUtils.isEmpty(keyword)) {
            if (containsChinese(content)) {
                result = getSmsCodeCN(keyword, content);
            } else {
                result = getSmsCodeEN(keyword, content);
            }
        }
        return result;
    }

    /**
     * 获取中文短信中包含的验证码
     */
    private static String getSmsCodeCN(String keyword, String content) {
        // 之前的正则表达式是 [a-zA-Z0-9]{4,8}
        // 现在的正则表达式是 [a-zA-Z0-9]+(\.[a-zA-Z0-9]+)? 匹配数字和字母之间最多一个.的字符串
        // 之前的不能识别和剔除小数，比如 123456.231，很容易就把 123456 作为验证码。
        String codeRegex = "(?<![a-zA-Z0-9])[a-zA-Z0-9]{4,8}(?![a-zA-Z0-9])";
        // 先去掉所有空白字符处理
        String handledContent = removeAllWhiteSpaces(content);
        String smsCode = getSmsCode(codeRegex, keyword, handledContent);
        if (TextUtils.isEmpty(smsCode)) {
            // 没解析出就按照原文本再处理一遍
            smsCode = getSmsCode(codeRegex, keyword, content);
        }
        return smsCode;
    }

    /**
     * Remove all white spaces.
     */
    private static String removeAllWhiteSpaces(String content) {
        return content.replaceAll("\\s*", "");
    }

    /**
     * 获取英文短信包含的验证码
     */
    private static String getSmsCodeEN(String keyword, String content) {
        // 之前的正则表达式是 [0-9]{4,8} 匹配由数字组成的4到8长度的字符串
        // 现在的正则表达式是 [0-9]+(\\.[0-9]+)? 匹配数字之间最多一个.的字符串
        // 之前的不能识别和剔除小数，比如 123456.231，很容易就把 123456 作为验证码。
        String codeRegex = "(?<![0-9])[0-9]{4,8}(?![0-9])";
        String smsCode = getSmsCode(codeRegex, keyword, content);
        if (TextUtils.isEmpty(smsCode)) {
            // 没解析出就去掉所有空白字符再处理
            content = removeAllWhiteSpaces(content);
            smsCode = getSmsCode(codeRegex, keyword, content);
        }
        return smsCode;
    }

    /*
     * Parse SMS code
     *
     * @param codeRegex SMS code regular expression
     * @param keyword     SMS code SMS keywords expression
     * @param content           SMS content
     * @return the SMS code if it's found, otherwise return empty string ""
     */
    private static String getSmsCode(String codeRegex, String keyword, String content) {
        Pattern p = Pattern.compile(codeRegex);
        Matcher m = p.matcher(content);
        List<String> possibleCodes = new ArrayList<>();
        while (m.find()) {
            final String matchedStr = m.group();
            possibleCodes.add(matchedStr);
        }
        if (possibleCodes.isEmpty()) { // no possible code
            return "";
        }

        List<String> filteredCodes = new ArrayList<>();
        for (String possibleCode : possibleCodes) {
            if (isNearToKeyword(keyword, possibleCode, content)) {
                filteredCodes.add(possibleCode);
            }
        }
        if (filteredCodes.isEmpty()) { // no possible code near to keywords
            filteredCodes = possibleCodes;
        }

        int maxMatchLevel = LEVEL_NONE;
        // minimum distance for possible code to keyword
        int minDistance = content.length();
        String smsCode = "";
        for (String filteredCode : filteredCodes) {
            final int curLevel = getMatchLevel(filteredCode);
            if (curLevel > maxMatchLevel) {
                maxMatchLevel = curLevel;
                // reset the minDistance
                minDistance = distanceToKeyword(keyword, filteredCode, content);
                smsCode = filteredCode;
            } else if (curLevel == maxMatchLevel) {
                int curDistance = distanceToKeyword(keyword, filteredCode, content);
                if (curDistance < minDistance) {
                    minDistance = curDistance;
                    smsCode = filteredCode;
                }
            }
        }
        return smsCode;
    }

    /* 匹配度：6位纯数字，匹配度最高 */
    private static final int LEVEL_DIGITAL_6 = 4;
    /* 匹配度：4位纯数字，匹配度次之 */
    private static final int LEVEL_DIGITAL_4 = 3;
    /* 匹配度：纯数字, 匹配度最高*/
    private static final int LEVEL_DIGITAL_OTHERS = 2;
    /* 匹配度：数字+字母 混合, 匹配度其次*/
    private static final int LEVEL_TEXT = 1;
    /* 匹配度：纯字母, 匹配度最低*/
    private static final int LEVEL_CHARACTER = 0;
    private static final int LEVEL_NONE = -1;

    private static int getMatchLevel(String matchedStr) {
        if (matchedStr.matches("^[0-9]{6}$"))
            return LEVEL_DIGITAL_6;
        if (matchedStr.matches("^[0-9]{4}$"))
            return LEVEL_DIGITAL_4;
        if (matchedStr.matches("^[0-9]*$"))
            return LEVEL_DIGITAL_OTHERS;
        if (matchedStr.matches("^[a-zA-Z]*$"))
            return LEVEL_CHARACTER;
        return LEVEL_TEXT;
    }

    /**
     * 可能的验证码是否靠近关键字
     */
    private static boolean isNearToKeyword(String keyword, String possibleCode, String content) {
        int beginIndex = 0, endIndex = content.length() - 1;
        int curIndex = content.indexOf(possibleCode);
        int strLength = possibleCode.length();
        int magicNumber = 30;
        if (curIndex - magicNumber > 0) {
            beginIndex = curIndex - magicNumber;
        }
        if (curIndex + strLength + magicNumber < endIndex) {
            endIndex = curIndex + strLength + magicNumber;
        }
        return content.substring(beginIndex, endIndex).contains(keyword);
    }

    /**
     * 计算可能的验证码与关键字的距离
     */
    private static int distanceToKeyword(String keyword, String possibleCode, String content) {
        int keywordIdx = content.indexOf(keyword);
        int possibleCodeIdx = content.indexOf(possibleCode);
        return Math.abs(keywordIdx - possibleCodeIdx);
    }


    public static boolean isPossiblePhoneNumber(String text) {
        return text.matches("\\d{8,}");
    }

    public static boolean containsPhoneNumberKeywords(String content) {
        Pattern pattern = Pattern.compile(SmsCodeConst.PHONE_NUMBER_KEYWORDS);
        Matcher matcher = pattern.matcher(content);
        return matcher.find();
    }
}
