package com.wz.mail.service;

import com.wz.common.model.Result;
import com.wz.mail.bean.MailMsg;

import java.util.concurrent.Future;

/**
 * @projectName: wz-component
 * @package: com.wz.mail.service
 * @className: AsyncMailService
 * @description:
 * @author: Zhi
 * @date: 2019-12-06 12:11
 * @version: 1.0
 */
public interface AsyncMailService {

    Future<Result> send(MailMsg msg, boolean isHtml);
}
