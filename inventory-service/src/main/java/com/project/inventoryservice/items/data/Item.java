package com.project.inventoryservice.items.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "items")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Item {

    private String id;

    private String name;

    private long available;

}
