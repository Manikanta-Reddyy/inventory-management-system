package com.example.inventory.service.implementation;

import com.example.inventory.dto.response.InventoryStatusDto;
import com.example.inventory.exception.InsufficientStockException;
import com.example.inventory.exception.InventoryNotFoundException;
import com.example.inventory.exception.ItemNotFoundException;
import com.example.inventory.mapper.InventoryMapper;
import com.example.inventory.model.InventoryCounter;
import com.example.inventory.model.Item;
import com.example.inventory.repository.InventoryRepository;
import com.example.inventory.repository.ItemRepository;
import com.example.inventory.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class InventoryServiceImplementation implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private InventoryMapper inventoryMapper;

    @Override
    public InventoryCounter getOrCreateByItemId(UUID itemId) {
        log.debug("Checking inventory for itemId: {}", itemId);

        return inventoryRepository.findByItemId(itemId)
                .orElseGet(() -> {
                    Item item = itemRepository.findById(itemId)
                            .orElseThrow(() -> new ItemNotFoundException(itemId));
                    log.info("Inventory not found for itemId: {} - creating new record", itemId);

                    InventoryCounter newInventory = InventoryCounter.builder()
                            .item(item)
                            .availableQuantity(0)
                            .reservedQuantity(0)
                            .build();

                    InventoryCounter saved = inventoryRepository.save(newInventory);
                    log.info("New Inventory created for itemId: {} with id: {}", itemId, saved.getId());
                    return saved;
                });
    }

    @Override
    @Transactional
    public void addStock(UUID itemId, int quantity) {
        log.info("Adding {} supply units to inventory for itemId: {}", quantity, itemId);

        InventoryCounter inventory = getOrCreateByItemId(itemId);
        int previousQuantity = inventory.getAvailableQuantity();
        inventory.setAvailableQuantity(previousQuantity+ quantity);
        inventoryRepository.save(inventory);

        log.info("Inventory updated for itemId: {} - previousQuantity: {} - presentQuantity: {}",
                itemId, previousQuantity, inventory.getAvailableQuantity());
    }

    @Override
    @Transactional
    public void reserveStock(UUID itemId, int quantity) {
        log.info("Attempting to reserve {} units for itemId: {}", quantity, itemId);

        InventoryCounter inventory = inventoryRepository.findByItemId(itemId)
                .orElseThrow(() -> new InventoryNotFoundException(itemId));

        if (inventory.getAvailableQuantity() < quantity) {
            log.warn("Insufficient stock for itemId: {}. Requested: {}, Available: {}",
                    itemId, quantity, inventory.getAvailableQuantity());
            throw new InsufficientStockException(itemId, quantity, inventory.getAvailableQuantity()
            );
        }

        inventory.setAvailableQuantity(inventory.getAvailableQuantity() - quantity);
        inventory.setReservedQuantity(inventory.getReservedQuantity() + quantity);
        inventoryRepository.save(inventory);
        log.info("Successfully reserved {} units for itemId: {}. AvailableQuantity: {}, ReservedQuantity: {}",
                quantity, itemId, inventory.getAvailableQuantity(), inventory.getReservedQuantity());
    }

    @Override
    @Transactional
    public void releaseReservedStock(UUID itemId, int quantity) {
        log.info("Attempting to release {} reserved units for itemId: {}", quantity, itemId);

        InventoryCounter inventory = inventoryRepository.findByItemId(itemId)
                .orElseThrow(() -> new InventoryNotFoundException(itemId));

        if (inventory.getReservedQuantity() < quantity) {
            log.warn("Cannot release more stock than reserved for itemId: {}. Reserved: {}, Requested: {}",
                    itemId, inventory.getReservedQuantity(), quantity);
            throw new IllegalStateException(itemId +
                            "Attempted to release more stock than reserved." + " Reserved: " + inventory.getReservedQuantity() + ", Requested: " + quantity);
        }

        inventory.setReservedQuantity(inventory.getReservedQuantity() - quantity);
        inventory.setAvailableQuantity(inventory.getAvailableQuantity() + quantity);
        inventoryRepository.save(inventory);
        log.info("Successfully released {} units for itemId: {}, AvailableQuantity: {}, ReservedQuantity: {}",
                quantity, itemId, inventory.getAvailableQuantity(), inventory.getReservedQuantity());
    }

    @Override
    public InventoryStatusDto getInventoryByItemId(UUID itemId) {
        log.info("Fetching inventory for itemId: {}", itemId);

        InventoryCounter inventory = inventoryRepository.findByItemId(itemId)
                .orElseThrow(() -> {
                    log.warn("Inventory not found for itemId: {}", itemId);
                    return new InventoryNotFoundException(itemId);
                });

        log.info("Fetched inventory successfully for itemId: {}, availableQuantity: {}, reservedQuantity: {}",
                itemId, inventory.getAvailableQuantity(), inventory.getReservedQuantity());
        return inventoryMapper.toDto(inventory);
    }
}
