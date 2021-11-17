package com.wz.excel.read.handler;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.excel.read.metadata.holder.ReadSheetHolder;
import com.wz.excel.model.RowExceptionResult;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: wz-component
 * @package: com.wz.excel.read
 * @className: AbstractReadHandler
 * @description:
 * @author: zhi
 * @date: 2021/11/16
 * @version: 1.0
 */
public abstract class AbstractReadHandler<T> implements ReadHandler<T> {
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    private final int batchCount;
    private final List<T> cachedList;

    @Getter
    private List<RowExceptionResult> exceptionResults;

    public AbstractReadHandler() {
        this(DEFAULT_BATCH_COUNT);
    }

    public AbstractReadHandler(int batchCount) {
        if (batchCount < 0) {
            throw new IllegalArgumentException("批量数量错误.");
        }

        this.batchCount = batchCount;
        this.cachedList = new ArrayList<>(batchCount);
    }

    /**
     * 一行一行读取
     */
    @Override
    public void invoke(T t, AnalysisContext c) {
        this.parseInvoke(t, c);
        cachedList.add(t);
        if (cachedList.size() >= batchCount) {
            this.processCachedData(cachedList);
            cachedList.clear();
        }
    }

    /**
     * 读取完成
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext c) {
        this.processCachedData(cachedList);
        cachedList.clear();
    }

    @Override
    public void onException(Exception e, AnalysisContext ac) {
        ReadRowHolder rowHolder = ac.readRowHolder();
        ReadSheetHolder sheetHolder = ac.readSheetHolder();
        Integer sheetNo = sheetHolder.getSheetNo();
        Integer rowIndex = rowHolder.getRowIndex();
        log.error(">>> 读取Excel发生了异常, sheetNo: [{}], rowIndex: [{}], msg: {}", sheetNo, rowIndex, e.getMessage());
        if (null == exceptionResults) {
            exceptionResults = new ArrayList<>();
        }
        RowExceptionResult exceptionResult = new RowExceptionResult(sheetNo, rowIndex, e);
        exceptionResults.add(exceptionResult);
    }

    public boolean isException() {
        return exceptionResults != null && !exceptionResults.isEmpty();
    }

}
