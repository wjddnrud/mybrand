package com.example.demo.repository;

import com.example.demo.domain.item.ItemEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    /**
     * 상품 등록
     * @param itemEntity
     */
    public void save(ItemEntity itemEntity) {
        if (itemEntity.getId() == null) { // 새로 생성한 객체인 경우
            em.persist(itemEntity);
        } else {    // JPA를 통해 이미 DB에 등록되었던걸 가져온 경우
            em.merge(itemEntity); // update로 이해하고 넘어가자
        }
    }

    /**
     * 상품 단건 조회
     * @param id
     * @return
     */
    public ItemEntity findOne(Long id) {
        return em.find(ItemEntity.class, id);
    }

    /**
     * 상품 전체 조회
     * @return
     */
    public List<ItemEntity> findAll() {
        return em.createQuery("select i from ItemEntity i", ItemEntity.class)
                .getResultList();
    }

}
