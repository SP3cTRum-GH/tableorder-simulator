package com.tableorder.tb.service;

import com.tableorder.tb.DTO.ShopSettingDTO;

public interface ShopSettingService {
    ShopSettingDTO saveShopSetting(ShopSettingDTO dto);
    ShopSettingDTO getShopSetting();
}
