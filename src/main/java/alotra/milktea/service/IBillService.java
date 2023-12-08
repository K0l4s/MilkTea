package alotra.milktea.service;

import alotra.milktea.entity.Bill;

import java.util.List;

public interface IBillService {
    List<Bill> findAll();
}
