package com.b209.hansotbab.fridge.entity;

import com.b209.hansotbab.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Fridge extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fridgeId;

    @Column(nullable = false)
    private Integer fridgeNumber;

    @Column(nullable = false)
    private String fridgeLocationName;

    @Column(nullable = false)
    private String fridgeLocationAddress;

    @ElementCollection(fetch = FetchType.LAZY)
    @Column(nullable = false)
    private List<String> fridgeAdmins;

    @OneToMany(mappedBy = "fridge", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    @Builder
    public Fridge(Long fridgeId, Integer fridgeNumber, String fridgeLocationName,
                  String fridgeLocationAddress, List<String> fridgeAdmins) {
        this.fridgeId = fridgeId;
        this.fridgeNumber = fridgeNumber;
        this.fridgeLocationName = fridgeLocationName;
        this.fridgeLocationAddress = fridgeLocationAddress;
        this.fridgeAdmins = fridgeAdmins;
    }

    public void updateDelete(){
        this.isDelete = true;
    }
}
