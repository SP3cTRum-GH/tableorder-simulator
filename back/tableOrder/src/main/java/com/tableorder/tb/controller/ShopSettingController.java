package com.tableorder.tb.controller;

import com.tableorder.tb.DTO.ShopSettingDTO;
import com.tableorder.tb.service.ShopSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shop-setting")
@RequiredArgsConstructor
public class ShopSettingController {

    private final ShopSettingService shopSettingService;

    @PostMapping
    public ResponseEntity<ShopSettingDTO> saveShopSetting(@RequestBody ShopSettingDTO dto) {
        return ResponseEntity.ok(shopSettingService.saveShopSetting(dto));
    }

    @GetMapping
    public ResponseEntity<ShopSettingDTO> getShopSetting() {
        ShopSettingDTO shopSetting = shopSettingService.getShopSetting();
        if (shopSetting != null) {
            return ResponseEntity.ok(shopSetting);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
