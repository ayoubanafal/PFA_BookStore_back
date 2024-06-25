package com.SBS.springbookseller.auth;

import com.SBS.springbookseller.Config.JwtService;
import com.SBS.springbookseller.DAO.Repositories.CartRepository;
import com.SBS.springbookseller.DAO.Repositories.UserRepository;
import com.SBS.springbookseller.DAO.entities.Cart;
import com.SBS.springbookseller.DAO.entities.Role;
import com.SBS.springbookseller.DAO.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CartRepository cartRepository;
    public AuthenticationResponse register(RegisterRequest request) {
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            return null;
        }
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .CreatedAt(new Date())
                .build();
        Cart cart=new Cart();
        var returnedUser = userRepository.save(user);
        cart.setUser(returnedUser);
        cartRepository.save(cart);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().Id(user.getId()).email(user.getEmail()).username(user.getName()).role(user.getRole()).token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthRequest request) {
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found!!"));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var JwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().Id(user.getId()).email(user.getEmail()).username(user.getName()).role(user.getRole()).token(JwtToken).build();
    }
}
