package cn.hestialx.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lixu
 * @create 2023-02-21-17:06
 */
@Data
public class LoginUser implements UserDetails {
    private User userInfo;

    private List<String> perms;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return perms.stream().map(a->new SimpleGrantedAuthority(a)).collect(Collectors.toList());
        //自定义权限控制方法，不需要getAuthorities方法
        return null;
    }

    @Override
    public String getPassword() {
        return userInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return userInfo.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
