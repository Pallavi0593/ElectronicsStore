package com.Bikkadit.ElectronicsStore.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;


@Setter
@Getter
@ToString
@MappedSuperclass
public class CustomFields implements Serializable {

    @Column(name = "is_active_switch")
    private String isactive;

    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    @Column(name = "create_date", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdOn;

    @Column(name = "modified_by")
    @LastModifiedBy
    private String lastModifiedBy;

    @Column(name = "update_date", updatable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedOn;
}
