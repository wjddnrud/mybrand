package com.example.demo.service;

import com.example.demo.domain.item.Book;
import com.example.demo.domain.item.ItemEntity;
import com.example.demo.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    /**
     * 상품 등록
     * @param itemEntity
     */
    @Transactional
    public void saveItem(ItemEntity itemEntity) {
        itemRepository.save(itemEntity);
    }

    /**
     * 상품 전체 조회
     * @return
     */
    public List<ItemEntity> findItems() {
        return itemRepository.findAll();
    }

    /**
     * 상품 단건 조회
     * @param id
     * @return
     */
    public ItemEntity findOne(Long id) {
        return itemRepository.findOne(id);
    }

    @Transactional
    public void updateItem(Long itemId, Book param) {
        ItemEntity findItemEntity = itemRepository.findOne(itemId);
        findItemEntity.setPrice(param.getPrice());
        findItemEntity.setName(param.getName());
        findItemEntity.setStockQuantity(param.getStockQuantity());

        // transaction commit -> JPA flush -> 영속성 컨텍스트중에 변경된 애를 전부 감지 -> 자동으로 변경된 값은 업데이트 쿼리를 수행함
    }
}
