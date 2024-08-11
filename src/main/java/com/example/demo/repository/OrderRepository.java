package com.example.demo.repository;

import com.example.demo.domain.Order;
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
    public void save(Order order) {
        em.persist(order);
    }

    // 주문 내용 단건 조회
    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAll(OrderSearch orderSearch) {


        // JPQL을 이용한 동적 쿼리
        String jpql = "select o from Order o join o.member m";
        boolean isFirstCondition = true;

        // 주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jpql += "  where";
                isFirstCondition = false;
            } else {
                jpql += "  and";
            }
            jpql += " o.status = :status";
        }

        // 회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) {
                jpql += "  where";
                isFirstCondition = false;
            } else {
                jpql += "  and";
            }
            jpql += "  m.name like :memberName";
        }

        TypedQuery<Order> query = em.createQuery(jpql, Order.class).setMaxResults(1000);

        if (orderSearch.getOrderStatus() != null) {
            query.setParameter("status", orderSearch.getOrderStatus());
        }
        if (orderSearch.getMemberName() != null) {
            query.setParameter("memberName", orderSearch.getMemberName());
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
    public List<Order> findAllByCriteria(OrderSearch orderSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
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
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);
        return query.getResultList();
    }
}
