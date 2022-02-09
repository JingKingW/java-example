package com.xunmall.example.java.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @Author: WangYanjing
 * @Date: 2019/6/12 10:27
 * @Description:
 */
public class FileUtils {
    public static ByteArrayInputStream parse(final ByteArrayOutputStream out) throws Exception {
        final ByteArrayInputStream swapStream = new ByteArrayInputStream(out.toByteArray());
        return swapStream;
    }
}
