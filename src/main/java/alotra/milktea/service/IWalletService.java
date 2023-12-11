package alotra.milktea.service;

import alotra.milktea.entity.Wallet;

import java.util.List;
import java.util.Optional;

public interface IWalletService {
    List<Wallet> findAll();
    Optional<Wallet> findOne(int id);
    void saveWallet(Wallet wallet);
    void deleteWallet(int id);
}
