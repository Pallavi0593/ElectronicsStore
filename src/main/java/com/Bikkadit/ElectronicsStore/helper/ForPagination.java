package com.Bikkadit.ElectronicsStore.helper;

import com.Bikkadit.ElectronicsStore.dtos.PageableResponse;
import com.Bikkadit.ElectronicsStore.dtos.UserDto;
import com.Bikkadit.ElectronicsStore.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class ForPagination {


    //U Entity V Dto
    public static <U,V> PageableResponse<V> getPageableResponse(Page<U> page,Class<V> type)
    {
        List<U> entity= page.getContent();

        List<V> Dto = entity.stream().map(Object -> new ModelMapper().map(Object,type)).collect(Collectors.toList());

        PageableResponse<V> response=new PageableResponse<>();
        response.setContent(Dto);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalPages(page.getTotalPages());
        response.setTotalElements(page.getTotalElements());
        response.setLastpage(page.isLast());

        return response;
    }
}
