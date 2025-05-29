package blueMonkey.user.application.service;

import blueMonkey.security.dto.AuthLoginRequest;
import blueMonkey.security.dto.AuthReponse;
import blueMonkey.security.entity.RoleEnum;
import blueMonkey.security.exceptions.EmailNotValidException;
import blueMonkey.security.exceptions.RoleNotFoundException;
import blueMonkey.security.repository.RoleRepository;
import blueMonkey.security.util.JwtUtils;
import blueMonkey.user.application.mapper.UserMapper;
import blueMonkey.user.domain.models.UserEntity;
import blueMonkey.user.infraestructure.repository.UserRepository;
import blueMonkey.user.infraestructure.dtos.input.InputUserDto;
import blueMonkey.user.infraestructure.dtos.output.OutputUserDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";  // Expresión regular para correo electrónico
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])([A-Za-z\\d$@$!%*?&]|[^ ]){8,40}$";

    @Autowired private RoleRepository roleRepository;

    @Autowired private UserRepository userRepository;
    @Autowired private UserMapper userMapper;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtils jwtUtils;

    /**
     * Carga un usuario por su correo electrónico (implementación de UserDetailsService).
     *
     * @param email El correo electrónico del usuario a buscar.
     * @return Un objeto UserDetails con los detalles del usuario (correo, contraseña y roles).
     * @throws UsernameNotFoundException Si el usuario no se encuentra en la base de datos.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        Set<GrantedAuthority> authorities = userEntity.getRoles().stream()
                .map(roleEntity -> new SimpleGrantedAuthority("ROLE_" + roleEntity.getRoleEnum().name()))
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
                userEntity.getEmail(),
                userEntity.getPassword(),
                authorities);
    }

    /**
     * Metodo para logearme, en postman sirve para recibir el token.
     * Autentica a un usuario mediante su correo electrónico y contraseña, y genera un token JWT.
     *
     * @param authLoginRequest Los datos de inicio de sesión (correo y contraseña).
     * @return Un objeto AuthReponse que contiene el token JWT y el mensaje de éxito.
     */
    public AuthReponse loginUser(AuthLoginRequest authLoginRequest) {
        String email = authLoginRequest.username();
        String password = authLoginRequest.password();

        Authentication authentication = this.authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication);
        return new AuthReponse(email, "User loged successfuly", accessToken, true);
    }

    /**
     * Verifica las credenciales de un usuario (correo electrónico y contraseña).
     *
     * @param email El correo electrónico del usuario.
     * @param password La contraseña del usuario.
     * @return Un objeto Authentication con los detalles de la autenticación.
     * @throws BadCredentialsException Si las credenciales son incorrectas.
     */
    public Authentication authenticate(String email, String password) {
        UserDetails userDetails = this.loadUserByUsername(email);
        if (userDetails == null) {
            throw new BadCredentialsException("Email invalido");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Contraseña invalida");
        }
        return new UsernamePasswordAuthenticationToken(email, password, userDetails.getAuthorities());
    }

    public OutputUserDto addUser(InputUserDto inputUsuarioDto) {
        UserEntity userEntity = userMapper.toEntity(inputUsuarioDto);
        if (!isValidEmail(userEntity.getEmail())) {
            throw new EmailNotValidException("El correo electrónico no es válido. " + inputUsuarioDto.getEmail());
        }
         userEntity.setRoles(inputUsuarioDto.getRoles().stream()
                .map(roleName -> roleRepository.findByRoleEnum(RoleEnum.valueOf(roleName))
                        .orElseThrow(() -> new RoleNotFoundException("Rol no encontrado: " + roleName)))
                .collect(Collectors.toSet()));

        userRepository.save(userEntity);
        return userMapper.toDTO(userEntity);
    }

    public OutputUserDto updateUser(Long id, InputUserDto inputUsuarioDto) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe usuario con id: "+ id));
        if(inputUsuarioDto.getName()!= null) user.setName(inputUsuarioDto.getName());
        if(inputUsuarioDto.getEmail()!= null) user.setEmail(inputUsuarioDto.getEmail());
        if(inputUsuarioDto.getPassword()!= null) user.setPassword(inputUsuarioDto.getPassword());
        if(inputUsuarioDto.getRoles() != null) {
            user.setRoles(inputUsuarioDto.getRoles().stream()
                    .map(roleName -> roleRepository.findByRoleEnum(RoleEnum.valueOf(roleName))
                            .orElseThrow(() -> new RoleNotFoundException("Rol no encontrado: " + roleName)))
                    .collect(Collectors.toSet()));
        }

        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    public ResponseEntity<String> deleteUser(Long id){
        UserEntity usuario = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe user con id: "+ id));
        userRepository.save(usuario);
        return ResponseEntity.status(200).body("Se ha borrado correctamente el usuario");
    }

    public List<OutputUserDto> getAllUsers(){
        return userRepository.findAll().stream()
                .map(user -> userMapper.toDTO(user))
                .collect(Collectors.toList());
    }

    public OutputUserDto getUser(Long id){
        UserEntity usuario = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe usuario con id: "+ id));
        return userMapper.toDTO(usuario);
    }

    /**
     * Valida si un correo electrónico tiene un formato válido usando una expresión regular.
     *
     * @param email El correo electrónico a validar.
     * @return `true` si el correo es válido, `false` en caso contrario.
     */
    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Valida si una contraseña cumple con los requisitos establecidos mediante una expresión regular.
     *
     * @param password La contraseña a validar.
     * @return `true` si la contraseña es válida, `false` en caso contrario.
     */
    private boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

}
