package com.example.demo.domain;

import com.example.demo.domain.item.ItemEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "category")
@Getter @Setter
public class CategoryEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))          // 일대다 다대일 관계로 풀어낼 중간 테이블이 필요
    private List<ItemEntity> itemEntities = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private CategoryEntity parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<CategoryEntity> child = new ArrayList<>();

    // [연관관계 편의 메서드]
    public void addChildCategory(CategoryEntity child) {
        this.child.add(child);
        child.setParent(this);
    }

}
