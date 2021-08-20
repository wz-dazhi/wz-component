package com.wz.push.bean;

/**
 * @projectName: wz-component
 * @package: com.wz.push.bean
 * @className: PushSuccessResp
 * @description:
 * @author: zhi
 * @date: 2021/8/20
 * @version: 1.0
 */
public class PushSuccessResp extends AbstractPushResp {
    public static final PushSuccessResp SUCCESS = new PushSuccessResp();

    private PushSuccessResp() {
        setCode("0");
        setMsg("成功");
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
