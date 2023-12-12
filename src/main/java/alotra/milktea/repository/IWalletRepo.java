package alotra.milktea.repository;

import alotra.milktea.entity.User;
import alotra.milktea.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IWalletRepo extends JpaRepository<Wallet,Integer> {
    Wallet findWalletByCustomerUser(User user);
}
