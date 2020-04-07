package com.huangxj.base.security;

import com.huangxj.common.core.enums.LoginType;
import com.huangxj.common.core.security.AuthAuthority;
import com.huangxj.common.core.security.AuthUserDetails;
import com.huangxj.common.core.security.LoginTypeContext;
import com.huangxj.base.system.api.AuthorityServiceClient;
import com.huangxj.base.system.api.UserServiceClient;
import com.huangxj.base.system.enums.UserStatus;
import com.huangxj.base.system.service.VerifyCodeService;
import com.huangxj.base.system.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName UserDetailsServiceImpl
 * @Description TODO
 * @Author: huangxj
 * @Create: 2019-07-31 15:06
 * @Version V1.0
 **/
@Service
public class UserServiceDetail implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserServiceClient userServiceClient;

    @Autowired
    AuthorityServiceClient authorityServiceClient;

    @Autowired
    VerifyCodeService verifyCodeService;

    @Value("${clientId}")
    String clientId;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String loginType = LoginTypeContext.get();
        UserVo user = userServiceClient.getUserByUsername(username).getData();
        if (user == null) {
            throw new UsernameNotFoundException("用户 " + username + " 不存在!，请先注册");
        }

        if (user.getTenantExpireTime() != null && System.currentTimeMillis() > user.getTenantExpireTime().getTime()) {
            throw new UsernameNotFoundException("租户 " + user.getTenantName() + " 已过期!");
        }
        boolean accountNonLocked = user.getStatus().intValue() != UserStatus.LOCKED.getCode();
        boolean credentialsNonExpired = true;
        boolean enabled = user.getStatus().intValue() == UserStatus.NORMAL.getCode();
        boolean accountNonExpired = true;
        if (Objects.equals(LoginType.CODE.getCode(), loginType)) {
            String code = verifyCodeService.checkCode(username);
            user.setPassword(passwordEncoder.encode(code));
        }
        if (Objects.equals(LoginType.MINIAPP.getCode(), loginType)) {
            user.setPassword(passwordEncoder.encode(username));
        }
        AuthUserDetails userDetails = new AuthUserDetails();
        userDetails.setAccountType(user.getUserType());
        userDetails.setUserId(user.getId());
        userDetails.setUsername(user.getUserName());
        userDetails.setNickName(user.getNickName());
        userDetails.setPassword(user.getPassword());
        userDetails.setTenantCode(user.getTenantCode());
        userDetails.setAccountNonLocked(accountNonLocked);
        userDetails.setAccountNonExpired(accountNonExpired);
        userDetails.setCredentialsNonExpired(credentialsNonExpired);
        userDetails.setEnabled(enabled);
        userDetails.setClientId(clientId);
        List<AuthAuthority> userAuthoritys = authorityServiceClient.findAuthorityUser(user.getId()).getData();
        userDetails.setAuthorities(userAuthoritys);
        //设置部门权限
        Map<String, Object> attrs = userDetails.getAttrs();
        attrs.put("departmentPermission",user.getDepartmentPermissionList());
        userDetails.setAttrs(attrs);
        return userDetails;
    }
}
