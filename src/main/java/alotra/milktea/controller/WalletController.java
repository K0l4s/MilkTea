package alotra.milktea.controller;

import alotra.milktea.entity.Bill;
import alotra.milktea.entity.Wallet;
import alotra.milktea.service.IWalletService;
import alotra.milktea.service.WalletServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class WalletController {
    @Autowired
    IWalletService walletService = new WalletServiceImpl();
    @GetMapping("/admin/wallet")
    public String findAll(Model model){
        model.addAttribute("list",walletService.findAll());
        return "/wallet/list";
    }
    @GetMapping("/admin/wallet/add")
    public String addWallet(Model model){
        Wallet wallet = new Wallet();
        model.addAttribute("list",wallet);
        return "/wallet/add";
    }
    @PostMapping("/wallet/save")
    public String saveWallet(@ModelAttribute("list") Wallet wallet) {
        walletService.saveWallet(wallet);
        return "redirect:/admin/wallet";
    }

    @GetMapping("/admin/wallet/edit/{id}")
    public String editWallet(@PathVariable("id") int id, Model model){
        Optional<Wallet> wallet = walletService.findOne(id);
        if(wallet.isPresent()){
            model.addAttribute("list", wallet);
            return "wallet/edit";
        }
        return "error";
    }
    @GetMapping("/admin/wallet/delete/{id}")
    public String deleteWallet(@PathVariable("id") int id){
        walletService.deleteWallet(id);
        return "redirect:/admin/wallet";
    }
}
