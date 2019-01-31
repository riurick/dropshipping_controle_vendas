package com.dropshipping.promocoes;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PromocaoRepository extends JpaRepository<Promocao, Integer> {


}
