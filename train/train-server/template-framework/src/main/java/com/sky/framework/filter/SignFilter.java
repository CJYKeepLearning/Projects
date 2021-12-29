package com.sky.framework.filter;

import com.sky.common.core.result.CommonError;
import com.sky.common.core.result.Result;
import com.sky.common.util.AESUtil;
import com.sky.common.util.ServletUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by skyyemperor on 2021-01-30 14:00
 * Description :
 */
public class SignFilter implements Filter {

    private static final String SIGN_HEADER = "SIGN";

    public static final String DATE_PARAM_PREFIX = "date";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (verify()) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ServletUtil.responseResult((HttpServletResponse) servletResponse,
                    Result.getResult(CommonError.VERIFY_WRONG));
        }
    }

    @Override
    public void destroy() {

    }

    private boolean verify() {
        String sign = ServletUtil.getRequest().getHeader(SIGN_HEADER);
        if (sign == null || sign.length() != 32) {
            return false; //检查SIGN请求头是否存在
        }

        String cipherText = sign.substring(0, 16);
        String key = sign.substring(sign.length() - 16);

        Map<String, String> params = ServletUtil.getAllParameters();

        //校验时间
        try {
            long date = Long.parseLong(params.get(DATE_PARAM_PREFIX));
            long now = System.currentTimeMillis();
            if (date > now || now - date > 3000 * 1000) {
                //请求时间与当前时间差大于30秒
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        List<String> box = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            box.add(entry.getKey());
            box.add(entry.getValue());
        }
        Collections.sort(box);

        StringBuilder plainText = new StringBuilder();
        for (int i = 0; i < box.size(); i++) {
            if (i > 0) plainText.append('#');
            plainText.append(box.get(i));
        }

        //AES加密校验
        String desCipherText = AESUtil.encryptData(key, plainText.toString());
        if (desCipherText != null && desCipherText.length() > 16) {
            desCipherText = desCipherText.substring(0, 16);
        }
        return cipherText.equals(desCipherText);
    }

    private String encrypt(Map<String, String> params) {
        long date = System.currentTimeMillis();
        System.out.println(date);
        params.put("date", String.valueOf(date));

        List<String> box = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            box.add(entry.getKey());
            box.add(entry.getValue());
        }
        Collections.sort(box);

        StringBuilder plainText = new StringBuilder();
        for (String str : box) {
            plainText.append(str).append('#');
        }
        plainText.deleteCharAt(plainText.length() - 1);

        String key = AESUtil.generateKey();
        String cipherText = AESUtil.encryptData(key, plainText.toString());

        return cipherText + key;
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("a", "1");
        map.put("b", "7");
        System.out.println(new SignFilter().encrypt(map));

    }
}
