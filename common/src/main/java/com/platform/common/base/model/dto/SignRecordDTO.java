package com.platform.common.base.model.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * 类说明：该类型负责所属模块中的核心功能实现与协作。
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年03月05日 17:38
 */
@Data
public class SignRecordDTO {

    private Long userId;

    private LocalDate signDate;

    private String message;

}
