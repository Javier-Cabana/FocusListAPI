package com.fcojcz.FocusListAPI.repository;

import com.fcojcz.FocusListAPI.model.entity.PasswordResetToken;
import com.fcojcz.FocusListAPI.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, UUID> {
    Optional<PasswordResetToken> findByToken(String token);
    void deleteByUsuario(Usuario usuario);
}
