package org.workwattbackend.usageHistory;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Entity
@Getter
@RequiredArgsConstructor
public class UsageHistoryEntity {
    @Id
    private long id;
    private String user_id;
    private Date start;
    private Date stop;
    private long computerId;
}
