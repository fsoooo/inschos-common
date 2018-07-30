package com.inschos.common.assist.kit;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringKit {

    public static String toString(Object obj) {
        StringBuffer buffer = new StringBuffer();
        if (obj != null) {
            buffer.append(obj);
        }
        return buffer.toString();
    }

    /**
     * 是否为NUll或""
     *
     * @param input
     * @return
     */
    public static boolean isEmpty(String input) {
        if (input == null || input.trim().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否为整数
     *
     * @param input
     * @return
     */
    public static boolean isInteger(String input) {
        if (isEmpty(input)) {
            return false;
        } else {
            Pattern pattern = Pattern.compile("^-?[0-9]*");
            return pattern.matcher(input).matches();
        }
    }

    /**
     * 判断是否为数字，包括整数和小数
     *
     * @param input
     * @return
     */
    public static boolean isNumeric(String input) {
        if (isEmpty(input)) {
            return false;
        } else {
            Pattern pattern = Pattern.compile("^-?[0-9]+.?[0-9]*");
            return pattern.matcher(input).matches();
        }
    }

    public static boolean isMobileNO(String input) {
        if (isEmpty(input)) {
            return false;
        } else {
            Pattern pattern = Pattern.compile("^[1][0-9]{10}$");
            return pattern.matcher(input).find();
        }
    }

    public static boolean isEmail(String input) {
        if (isEmpty(input)) {
            return false;
        } else {
            Pattern pattern = Pattern.compile("^[a-z0-9]+([._\\\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");
            return pattern.matcher(input).find();
        }
    }

    public static String[] split(String input, String regex) {
        if (!isEmpty(input) && !isEmpty(regex)) {
            return input.split(regex);
        }
        return null;
    }

    public static String splitLast(String input, String regex) {
        if (!isEmpty(input) && !isEmpty(regex)) {
            if (input.lastIndexOf(regex) >= 0) {
                return input.substring(input.lastIndexOf(regex) + 1);
            }
        }

        return "";
    }

    public static List<String> toList(String input, String regex) {
        if (isEmpty(input)) {
            return new ArrayList<>();
        } else if (isEmpty(regex)) {
            List<String> list = new ArrayList<>();
            return list;

        }
        return new ArrayList<>(Arrays.asList(input.split(regex)));
    }

    public static <T> List<T> listUniq(List<T> list) {
        if (list != null) {
            HashSet<T> h = new HashSet<>(list);
            list.clear();
            list.addAll(h);
        }
        return list;
    }

    public static String randNum(int lenth) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < lenth; i++) {
            builder.append(random.nextInt(9));
        }
        return builder.toString();
    }

    public static String randStr(int lenth) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < lenth; i++) {
            builder.append(random.nextInt(9));

        }
        return builder.toString();
    }

    public static String join(Collection<?> collection, String separator) {
        return collection == null?null:join(collection.iterator(), separator);
    }

    public static String join(Object[] array, String separator) {
        if(array == null) {
            return null;
        } else {
            if(separator == null) {
                separator = "";
            }

            StringBuffer buf = new StringBuffer();

            for(int i = 0,len=array.length; i < len; ++i) {
                if(i > 0) {
                    buf.append(separator);
                }

                if(array[i] != null) {
                    buf.append(array[i]);
                }
            }

            return buf.toString();
        }
    }

    public static String join(Iterator iterator, String separator) {

        // handle null, zero and one elements before building a buffer
        if (iterator == null) {
            return null;
        }
        if (!iterator.hasNext()) {
            return "";
        }
        Object first = iterator.next();
        if (!iterator.hasNext()) {
            return first == null ? "" : first.toString();
        }

        // two or more elements
        StringBuffer buf = new StringBuffer(256); // Java default is 16, probably too small
        if (first != null) {
            buf.append(first);
        }

        while (iterator.hasNext()) {
            if (separator != null) {
                buf.append(separator);
            }
            Object obj = iterator.next();
            if (obj != null) {
                buf.append(obj);
            }
        }
        return buf.toString();
    }

    /**
     * 将指定字符串首字母转换成大写字母
     *
     * @param str 指定字符串
     * @return 返回首字母大写的字符串
     */
    public static String firstCharUpperCase(String str) {
        StringBuffer buffer = new StringBuffer(str);
        if (buffer.length() > 0) {
            char c = buffer.charAt(0);
            buffer.setCharAt(0, Character.toUpperCase(c));
        }
        return buffer.toString();
    }

    /**
     * 格式化输出float
     *
     * @param input
     * @return
     */
    public static String formatFloat(float input, int fraction) {
        BigDecimal value = new BigDecimal(String.valueOf(input)).setScale(fraction, BigDecimal.ROUND_FLOOR).stripTrailingZeros();
        if (value.compareTo(BigDecimal.ZERO) == 0) {
            return "0";
        } else {
            return value.toPlainString();
        }
    }

    /**
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {
        if (source != null) {
            Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]|[\u2300-\u23FF]",
                    Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
            Matcher emojiMatcher = emoji.matcher(source);
            if (emojiMatcher.find()) {
                source = emojiMatcher.replaceAll("");
                return source;
            }
            return source;
        }
        return source;
    }

    public static boolean isHttpUrl(String url) {
        return !StringKit.isEmpty(url) && (url.startsWith("http://") || url.startsWith("https://"));
    }

    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) {
            return true;
        }
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }
}
