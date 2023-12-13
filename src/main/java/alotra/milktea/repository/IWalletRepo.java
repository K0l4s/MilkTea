package alotra.milktea.repository;

import alotra.milktea.entity.User;
import alotra.milktea.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface IWalletRepo extends JpaRepository<Wallet,Integer> {
    Wallet findWalletByCustomerUser(User user);

    Optional<Wallet> findByCustomerCustomerID(Long customer);
}
