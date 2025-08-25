package com.bnt.rcp.entity;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.sql.Timestamp;
import java.util.Optional;

@Component
public class AuditListener {

    @PrePersist
    public void setCreatedOn(AuditEntity auditEntity) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        auditEntity.setCreatedOn(now);
        auditEntity.setUpdatedOn(now);

        getCurrentUserId().ifPresent(userId -> {
            auditEntity.setCreatedBy(userId);
            auditEntity.setUpdatedBy(userId);
        });
    }

    @PreUpdate
    public void setUpdatedOn(AuditEntity auditEntity) {
        auditEntity.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
        getCurrentUserId().ifPresent(auditEntity::setUpdatedBy);
    }

    private Optional<Integer> getCurrentUserId() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                String userIdHeader = request.getHeader("user-id");
                if (userIdHeader != null && !userIdHeader.isEmpty()) {
                    return Optional.of(Integer.parseInt(userIdHeader));
                }
            }
            return Optional.of(1);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}