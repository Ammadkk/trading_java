package com.market.trading.controller;

import com.market.trading.Model.Asset;
import com.market.trading.Model.User;
import com.market.trading.service.AssetService;
import com.market.trading.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asset")

public class AssetC {

    @Autowired
    private AssetService assetService;

    @Autowired
    private UserService userService;


    @GetMapping("/{assetId}")
    public ResponseEntity<Asset> getAssetById(@PathVariable Long assetId) throws Exception {
        Asset asset = assetService.getAssetById(assetId);
        return ResponseEntity.ok().body(asset);
    }

    @GetMapping("/coin/{coidId}/user")
    public ResponseEntity<Asset> getAssetByUserIdAndId(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long coidId
    ) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        Asset asset = assetService.getAssetByUserIdAndId(user.getId(), coidId);
        return ResponseEntity.ok().body(asset);

    }

    @GetMapping()
    public ResponseEntity<List<Asset>> getAssetsForUsers(
            @RequestHeader("Authorization") String jwt

    ) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);

        List<Asset> assets = assetService.getUserAssets(user.getId());
        return ResponseEntity.ok().body(assets);
    }


}
