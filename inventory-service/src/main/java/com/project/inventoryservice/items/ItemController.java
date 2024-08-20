package com.project.inventoryservice.items;

import com.project.inventoryservice.items.data.dto.ItemRequest;
import com.project.inventoryservice.items.data.dto.ItemResponse;
import com.project.inventoryservice.items.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemResponse> addItem(@RequestBody ItemRequest itemRequest) {
        return ResponseEntity.ok(itemService.saveItem(itemRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> getItemById(@PathVariable("id") String itemId) {
        return ResponseEntity.ok(itemService.getItemById(itemId));
    }

    @PutMapping("/{id}/available")
    public void updateItemAmount(@PathVariable("id") String itemId, @RequestBody long newAmount) {
        itemService.updateItemAmount(itemId, newAmount);
    }

}
