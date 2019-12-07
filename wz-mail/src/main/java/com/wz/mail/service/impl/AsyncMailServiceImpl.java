package com.wz.mail.service.impl;

import com.wz.common.model.Result;
import com.wz.mail.bean.MailMsg;
import com.wz.mail.service.AsyncMailService;
import com.wz.mail.service.MailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * @projectName: wz-component
 * @package: com.wz.mail.service.impl
 * @className: AsyncMailServiceImpl
 * @description:
 * @author: Zhi
 * @date: 2019-12-06 12:11
 * @version: 1.0
 */
@Slf4j
@Async
@Service
@AllArgsConstructor
public class AsyncMailServiceImpl implements AsyncMailService {
    private final MailService mailService;

    @Override
    public Future<Result> send(MailMsg msg, boolean isHtml) {
        return AsyncResult.forValue(mailService.send(msg, isHtml));
    }

}
