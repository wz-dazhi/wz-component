package com.wz.common.util;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.Funnels;

import java.nio.charset.Charset;

/**
 * @projectName: wz-component
 * @package: com.wz.common.util
 * @className: BloomFilterUtil
 * @description:
 * @author: zhi
 * @date: 2022/5/26
 * @version: 1.0
 */
public class BloomFilterUtil<T> {
    public static final int DEFAULT_DATA_SIZE = 100000;
    public static final double DEFAULT_FPP = 0.0001;

    private final BloomFilter<T> bloomFilter;

    public BloomFilterUtil() {
        this(DEFAULT_DATA_SIZE);
    }

    public BloomFilterUtil(int expectedInsertions) {
        this(expectedInsertions, DEFAULT_FPP);
    }

    public BloomFilterUtil(Funnel<T> funnel) {
        this(funnel, DEFAULT_DATA_SIZE, DEFAULT_FPP);
    }

    public BloomFilterUtil(int expectedInsertions, double fpp) {
        this((Funnel<T>) Funnels.stringFunnel(Charset.defaultCharset()), expectedInsertions, fpp);
    }

    public BloomFilterUtil(Funnel<T> funnel, int expectedInsertions, double fpp) {
        this.bloomFilter = create(funnel, expectedInsertions, fpp);
    }

    public static <T> BloomFilter<T> create(Funnel<T> funnel, int expectedInsertions, double fpp) {
        return BloomFilter.create(funnel, expectedInsertions, fpp);
    }

    public BloomFilter<T> bloomFilter() {
        return bloomFilter;
    }

    public void add(T t) {
        bloomFilter.put(t);
    }

    public boolean contains(T t) {
        return bloomFilter.mightContain(t);
    }

}
