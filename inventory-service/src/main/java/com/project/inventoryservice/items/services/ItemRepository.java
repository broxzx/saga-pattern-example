package com.project.inventoryservice.items.services;

import com.project.inventoryservice.items.data.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository extends MongoRepository<Item, String> {



}
