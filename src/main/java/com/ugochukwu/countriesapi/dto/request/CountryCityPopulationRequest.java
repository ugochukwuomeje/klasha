package com.ugochukwu.countriesapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CountryCityPopulationRequest {

    //@Schema(required=true) add swagger!
//    @NotNull(message = "numberOfCities cannot be null")
//    private Integer numberOfCities;

//    public CountryCityPopulationRequest(Integer limit, String order, String orderBy, String country){
//
//        this.limit = limit;
//        this.order = order;
//        this.orderBy = orderBy;
//        this.country = country;
//    }
    @NotNull(message = "limit cannot be null")
    private Integer limit;

    @NotEmpty(message = "order cannot be empty")
    private String order;

    @NotEmpty(message = "orderBy cannot be empty")
    private String orderBy;

    @NotEmpty(message = "country cannot be empty")
    private String country;

}
