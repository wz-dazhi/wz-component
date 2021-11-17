package com.wz.excel.read.handler;

import com.wz.excel.read.service.ReadService;

import java.util.List;

/**
 * @projectName: wz-component
 * @package: com.wz.excel.read
 * @className: DefaultReadHandler
 * @description:
 * @author: zhi
 * @date: 2021/11/16
 * @version: 1.0
 */
public class DefaultReadHandler<T> extends AbstractReadHandler<T> {
    private final ReadService<T> readService;

    public DefaultReadHandler(ReadService<T> readService) {
        this(DEFAULT_BATCH_COUNT, readService);
    }

    public DefaultReadHandler(int batchCount, ReadService<T> readService) {
        super(batchCount);
        this.readService = readService;
    }

    @Override
    public void processCachedData(List<T> cachedList) {
        log.debug(">>> processing cached data: [{}]", cachedList.size());
        readService.process(cachedList);
    }

}
