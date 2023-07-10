package com.Bikkadit.ElectronicsStore.entities;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseResponse<T> {
    private Integer code;

    private  String message;

    private T data;

}
