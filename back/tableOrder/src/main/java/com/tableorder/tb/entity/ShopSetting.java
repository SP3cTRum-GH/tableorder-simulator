package com.tableorder.tb.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SequenceGenerator(name = "shop_setting_seq_gen", sequenceName = "shop_setting_seq", allocationSize = 1)
public class ShopSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shop_setting_seq_gen")
    private Long id;

    private String shopImageUrl;
}
