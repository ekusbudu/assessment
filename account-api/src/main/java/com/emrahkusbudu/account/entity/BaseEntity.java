package com.emrahkusbudu.account.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {
    private boolean deleted = Boolean.FALSE;
    @Column(name = "createDateTime", nullable = false, updatable = false)
    private LocalDateTime createDateTime;

    @Column(name = "updateTime", nullable = false)
    private LocalDateTime updateTime;

    public BaseEntity() {
        this.createDateTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @PreUpdate
    public void preUpdate() {
        this.updateTime = LocalDateTime.now();
    }
}
