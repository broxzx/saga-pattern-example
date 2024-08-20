package com.project.inventoryservice.items.services;

import com.project.inventoryservice.items.data.Item;
import com.project.inventoryservice.items.data.dto.ItemRequest;
import com.project.inventoryservice.items.data.dto.ItemResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;

    public ItemResponse saveItem(ItemRequest itemRequest) {
        Item mappedItem = modelMapper.map(itemRequest, Item.class);
        Item savedItem = itemRepository.save(mappedItem);

        return modelMapper.map(savedItem, ItemResponse.class);
    }

    public ItemResponse getItemResponseById(String itemId) {
        Item item = getItemById(itemId);

        return modelMapper.map(item, ItemResponse.class);
    }

    public void updateItemAmount(String itemId, long newAmount) {
        Item obtainedItem = getItemById(itemId);
        obtainedItem.setAvailable(newAmount);

        itemRepository.save(obtainedItem);
    }

    private Item getItemById(String itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("item with id '%s' is not found".formatted(itemId)));
    }
}
