package com.jjgr.store_demo.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jjgr.store_demo.security.PermissionEntity;
import com.jjgr.store_demo.security.RoleEnum;
import com.jjgr.store_demo.security.RoleEntity;

import java.util.List;
import java.util.Set;

@Configuration
public class UserConfig {
    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            // Permisos de acceso
            PermissionEntity createPermission = PermissionEntity.builder()
                .name("CREATE")
                .build();

            PermissionEntity readPermission = PermissionEntity.builder()
                .name("READ")
                .build();

            PermissionEntity updatePermission = PermissionEntity.builder()
                .name("UPDATE")
                .build();

            PermissionEntity deletePermission = PermissionEntity.builder()
                .name("DELETE")
                .build();

            // Crear Roles
            RoleEntity adminRole = RoleEntity.builder()
                .role(RoleEnum.ADMIN)
                .permissions(Set.of(createPermission, readPermission, updatePermission, deletePermission))
                .build();

            RoleEntity userRole = RoleEntity.builder()
                .role(RoleEnum.USER)
                .permissions(Set.of(readPermission))
                .build();

            RoleEntity guestRole = RoleEntity.builder()
                .role(RoleEnum.GUEST)
                .permissions(Set.of(readPermission))
                .build();

            RoleEntity developerRole = RoleEntity.builder()
                .role(RoleEnum.DEVELOPER)
                .permissions(Set.of(createPermission, readPermission, updatePermission))
                .build();
                
            // Agregar usuarios
            UserEntity sebastian = UserEntity.builder()
                .name("Sebastian")
                .password("password")
                .email("rosassebastian2003@gmail.com")
                .isActive(true)
                .accountNotExpired(true)
                .accountNotLocked(true)
                .roles(Set.of(adminRole, userRole, developerRole))
                .build();
                
            UserEntity john = UserEntity.builder()
                .name("John")
                .password("password")
                .email("john@example.com")
                .isActive(true)
                .accountNotExpired(true)
                .accountNotLocked(true)
                .roles(Set.of(userRole))
                .build();
                
            UserEntity emily = UserEntity.builder()
                .name("Emily")
                .password("password")
                .email("emily@example.com")
                .isActive(true)
                .accountNotExpired(true)
                .accountNotLocked(true)
                .roles(Set.of(guestRole))
                .build();
            
            // Guardar usuarios
            userRepository.saveAll(List.of(sebastian, john, emily));
        };
    }
}
