package com.wz.mail.service;

import com.wz.mail.bean.MailMsg;
import com.wz.swagger.model.Result;

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

    Future<Result<Boolean>> send(MailMsg msg, boolean isHtml);
}
