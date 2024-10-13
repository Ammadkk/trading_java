package com.market.trading.service;

import com.market.trading.Model.Asset;
import com.market.trading.Model.Coin;
import com.market.trading.Model.User;
import com.market.trading.repository.AssetRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class AssetServiceImpl implements AssetService {

    @Autowired
    private AssetRep assetRep;



    @Override
    public Asset createAsset(User user, Coin coin, double quantity) {
        Asset asset = new Asset();
        asset.setUser(user);
        asset.setCoin(coin);
        asset.setQuantity(quantity);
        asset.setBuyPrice(coin.getCurrentPrice());


        return assetRep.save(asset);
    }


    @Override
    public Asset getAssetById(Long assetId) throws Exception {
        return assetRep.findById(assetId).orElseThrow(() -> new Exception("Asset not found"));
    }


    @Override
    public Asset getAssetByUserIdAndId(Long userId, Long assetId) {

        return null;
    }

    @Override
    public List<Asset> getUserAssets(Long userId) {

        return assetRep.findByUserId(userId);
    }

    @Override
    public Asset updateAsset(Long assetId, double quantity) throws Exception {

        Asset oldAsset = getAssetById(assetId);
        oldAsset.setQuantity(quantity + oldAsset.getQuantity());
        return assetRep.save(oldAsset);


    }
//changed from string to long
    @Override
    public Asset findAssetByUserIdAndCoinId(Long userId, String coinId) {
        return assetRep.findAssetByUserIdAndCoinId(userId, coinId);
    }

    @Override
    public void deleteAsset(Long assetId) {
        assetRep.deleteById(assetId);

    }
}
