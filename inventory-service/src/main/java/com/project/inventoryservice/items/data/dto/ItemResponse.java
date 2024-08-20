package com.project.inventoryservice.items.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ItemResponse {

    private String id;

    private String name;

    private long available;

    private long price;

}
