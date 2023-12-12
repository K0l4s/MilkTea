package alotra.milktea.service;

import alotra.milktea.entity.User;
import alotra.milktea.entity.Wallet;
import alotra.milktea.repository.IWalletRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WalletServiceImpl implements  IWalletService{
    @Autowired
    IWalletRepo walletRepo;

    @Override
    public List<Wallet> findAll() {
        return walletRepo.findAll();
    }

    @Override
    public Optional<Wallet> findOne(int id) {
        return walletRepo.findById(id);
    }

    @Override
    public void saveWallet(Wallet wallet) {
        walletRepo.save(wallet);
    }

    @Override
    public void deleteWallet(int id) {
        walletRepo.deleteById(id);
    }

    @Override
    public Wallet findByUser(User user){
        return walletRepo.findWalletByCustomerUser(user);
    }
}
