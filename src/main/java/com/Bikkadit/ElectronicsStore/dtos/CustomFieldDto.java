package com.Bikkadit.ElectronicsStore.dtos;

import lombok.*;

import java.time.LocalDateTime;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomFieldDto {
    private String isactive;
    private String createdBy;
    private LocalDateTime createdOn;
    private String lastModifiedBy;
    private LocalDateTime modifiedOn;
}
