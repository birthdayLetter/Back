package com.springboot.letterbackend.friend.dto.response;

import com.springboot.letterbackend.common.CommonResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FriendResultDto {
    private boolean success;
    private int code;
    private String msg;

    public void setAddSuccessResult(FriendResultDto friendResultDto) {
        friendResultDto.setSuccess(true);
        friendResultDto.setCode(CommonResponse.SUCCESS.getCode());
        friendResultDto.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    public void setApplySuccessResult(FriendResultDto friendResultDto) {
        friendResultDto.setSuccess(true);
        friendResultDto.setCode(CommonResponse.SUCCESS.getCode());
        friendResultDto.setMsg(CommonResponse.PENDING.getMsg());
    }
    public void setFailResult(FriendResultDto failResult) {
        failResult.setSuccess(false);
        failResult.setCode(CommonResponse.FAIL.getCode());
        failResult.setMsg(CommonResponse.FAIL.getMsg());

    }
}
