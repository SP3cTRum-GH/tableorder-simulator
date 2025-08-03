package com.tableorder.tb.service;

import com.tableorder.tb.DTO.ShopSettingDTO;
import com.tableorder.tb.entity.ShopSetting;
import com.tableorder.tb.repository.ShopSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopSettingServiceImpl implements ShopSettingService {

    private final ShopSettingRepository shopSettingRepository;

    @Override
    public ShopSettingDTO saveShopSetting(ShopSettingDTO dto) {
        ShopSetting shopSetting;
        if (dto.getId() != null) {
            shopSetting = shopSettingRepository.findById(dto.getId())
                    .orElse(new ShopSetting());
        } else {
            List<ShopSetting> existingSettings = shopSettingRepository.findAll();
            if (!existingSettings.isEmpty()) {
                shopSetting = existingSettings.get(0);
            } else {
                shopSetting = new ShopSetting();
            }
        }
        shopSetting.setShopImageUrl(dto.getShopImageUrl());
        ShopSetting savedSetting = shopSettingRepository.save(shopSetting);
        return toDTO(savedSetting);
    }

    @Override
    public ShopSettingDTO getShopSetting() {
        List<ShopSetting> settings = shopSettingRepository.findAll();
        if (!settings.isEmpty()) {
            return toDTO(settings.get(0));
        } else {
            return null; 
        }
    }

    private ShopSettingDTO toDTO(ShopSetting shopSetting) {
        return ShopSettingDTO.builder()
                .id(shopSetting.getId())
                .shopImageUrl(shopSetting.getShopImageUrl())
                .build();
    }
}
