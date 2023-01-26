package com.etc.jobsystem.entity.Factory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyProcess {
    private String noticeSenderName;

    private String noticeReceiverName;

    private String noticeContent;
}
