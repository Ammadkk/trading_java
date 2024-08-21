package com.market.trading.repository;

import com.market.trading.Model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetRep extends JpaRepository<Asset, Long> {

    List<Asset> findByUserId(Long userId);

    Asset findAssetByUserIdAndCoinId(Long userId, String coinId);



}
