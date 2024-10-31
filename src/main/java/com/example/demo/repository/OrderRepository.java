package com.example.demo.repository;

import com.example.demo.domain.OrderEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.OffsetScrollPositionHandlerMethodArgumentResolver;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;
    private final OffsetScrollPositionHandlerMethodArgumentResolver offsetResolver;

    // 주문 내용 저장
    public void save(OrderEntity orderEntity) {
        em.persist(orderEntity);
    }

    // 주문 내용 단건 조회
    public OrderEntity findOne(Long id) {
        return em.find(OrderEntity.class, id);
    }

    public List<OrderEntity> findAll(OrderSearch orderSearch) {


        // JPQL을 이용한 동적 쿼리
        String jpql = "select o from OrderEntity o join o.member m";
        boolean isFirstCondition = true;

        // 주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.status = :status";
        }

        // 회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }

        TypedQuery<OrderEntity> query = em.createQuery(jpql, OrderEntity.class).setMaxResults(1000);

        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }

        return query.getResultList();



//        return em.createQuery("select o from Order o join o.member m" +
//                        " where o.status = :status " +
//                        " and m.name like :name", Order.class)
//                .setParameter("status", orderSearch.getOrderStatus())
//                .setParameter("name", orderSearch.getMemberName())
//                // .setFirstResult(100)    // 페이징할때 시작 페이지 셋팅
//                .setMaxResults(1000)       // 최대 1000건 조회
//                .getResultList();
    }

    /**
     * JPA Criteria    -> 실무에서 사용 X. 권장 X
     * @param orderSearch
     * @return
     */
    public List<OrderEntity> findAllByCriteria(OrderSearch orderSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<OrderEntity> cq = cb.createQuery(OrderEntity.class);
        Root<OrderEntity> o = cq.from(OrderEntity.class);
        Join<Object, Object> m = o.join("member", JoinType.INNER);

        List<Predicate> criteria = new ArrayList<>();

        // 주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            Predicate name = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            criteria.add(name);
        }

        // 회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name = cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName() + "%");
            criteria.add(name);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<OrderEntity> query = em.createQuery(cq).setMaxResults(1000);
        return query.getResultList();
    }
}
