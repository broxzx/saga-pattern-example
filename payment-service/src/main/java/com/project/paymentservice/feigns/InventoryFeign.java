package com.project.paymentservice.feigns;

import com.project.paymentservice.model.ItemResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "inventory-service", path = "/items")
public interface InventoryFeign {

    @GetMapping("/{id}")
    ResponseEntity<ItemResponse> getItemById(@PathVariable("id") String itemId);

    @PutMapping("/{id}/available")
    void updateItemAmount(@PathVariable("id") String itemId, @RequestBody long newAmount);

}
