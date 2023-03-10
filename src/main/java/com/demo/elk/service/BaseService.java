package com.demo.elk.service;

import com.demo.elk.dto.authentication.LoginRequestDTO;
import com.demo.elk.dto.authentication.LoginResponseDTO;
import com.demo.elk.dto.authentication.SignUpRequestDTO;
import com.demo.elk.dto.shop.shopee.ItemBasicDTO;
import com.demo.elk.dto.shop.shopee.ItemDTO;
import com.demo.elk.dto.shop.shopee.ShopDTO;
import com.demo.elk.entity.role.Role;
import com.demo.elk.entity.shop.Item;
import com.demo.elk.entity.shop.ItemBasic;
import com.demo.elk.entity.shop.Shop;
import com.demo.elk.entity.types.State;
import com.demo.elk.entity.types.UserRole;
import com.demo.elk.entity.user.User;
import com.demo.elk.exception.ErrorException;
import com.demo.elk.exception.MessageResponse;
import com.demo.elk.jwt.JwtTokenProvider;
import com.demo.elk.mapper.ModelMapper;
import com.demo.elk.repository.elasticsearch.UserElasticsearchRepository;
import com.demo.elk.repository.jpa.*;
import com.demo.elk.service.impl.OtpService;
import com.demo.elk.util.CacheHandle;
import com.demo.elk.util.Helper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Log4j2
public class BaseService {
    protected static final String BLANK_CHARACTER = "";
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected UserElasticsearchRepository userElasticsearchRepository;
    @Autowired
    protected ShopRepository shopRepository;
    @Autowired
    protected AddOnDealInfoRepository addOnDealInfoRepository;
    @Autowired
    protected ItemBasicRepository itemBasicRepository;
    @Autowired
    protected ItemRepository itemRepository;
    @Autowired
    protected OptionRepository optionRepository;
    @Autowired
    protected TierVariationRepository tierVariationRepository;
    @Autowired
    protected VoucherRepository voucherRepository;
    @Autowired
    protected ModelMapper modelMapper;
    @Autowired
    protected JwtTokenProvider tokenProvider;
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Autowired
    protected AuthenticationManager authenticationManager;
    @Autowired
    protected OtpService otpService;
    @Autowired
    protected CacheHandle cacheHandle;
    @Autowired
    protected JavaMailSender javaMailSender;
    @Autowired
    protected IRoleRepository iRoleRepository;
    @Autowired
    protected ICreditCardRepository iCreditCardRepository;
    @Value("${app.jwtSecret.token}")
    protected String secretKey;
    @Value(("${time.register}"))
    protected int expiryTimeRegister;
    @Value(("${time.forgot-password}"))
    protected int expiryTimeForgot;
    @Value("${time.refresh-token}")
    protected int expiryTimeRefreshToken;

    protected User assignUserDTOToUser(SignUpRequestDTO signUpRequestDTO, boolean isAdmin, HttpServletRequest request) {
        State state = isAdmin ? State.ACTIVE : State.NONACTIVE;
        return User.builder()
                .username(signUpRequestDTO.getUsername())
                .phoneNumber(signUpRequestDTO.getPhoneNumber())
                .email(signUpRequestDTO.getEmail())
                .password(encodePassword(signUpRequestDTO.getPassword()))
                .fullName(signUpRequestDTO.getFullName())
                .uid(Helper.generateUid())
                .roles(assignNameRoleToObject(signUpRequestDTO.getRoles(), isAdmin))
                .state(state)
                .loginFailure(0)
                .remoteAddress(request.getRemoteAddr())
                .build();
    }

    protected void saveShop(ShopDTO shopDTO) {
        try {
            if (Objects.nonNull(shopDTO)) {
                Shop shop = Shop.builder()
                        .algorithm(shopDTO.getAlgorithm())
                        .totalCount(shopDTO.getTotalCount())
                        .build();
                shop = shopRepository.save(shop);
                Shop saved = new Shop();
                saved.setId(shop.getId());
                shopDTO.getItems().forEach(itemDTO -> saveItem(itemDTO, saved));
            }
        } catch (Exception e) {
            log.info(e);
        }
    }

    protected void saveItem(ItemDTO itemDTO, Shop shop) {
        Item item;
        try {
            ItemBasic itemBasic = saveItemBasic(itemDTO.getItemBasicDTO());
            if (Objects.nonNull(itemBasic)) {
                item = Item.builder()
                        .itemBasic(itemBasic)
                        .shop(shop)
                        .build();
                itemRepository.save(item);
            }
        } catch (Exception e) {
            log.info(e);
        }
    }

    protected ItemBasic saveItemBasic(ItemBasicDTO itemBasicDTO) {
        ItemBasic itemBasic = null;
        try {
            ItemBasic existingItemBasic = itemBasicRepository.findItemBasicByItemId(itemBasicDTO.getItemId()).orElse(null);
            if (Objects.isNull(existingItemBasic)) {
                itemBasic = ItemBasic.builder()
                        .name(itemBasicDTO.getName())
                        .itemId(itemBasicDTO.getItemId())
                        .currency(itemBasicDTO.getCurrency())
                        .stock(itemBasicDTO.getStock())
                        .status(itemBasicDTO.getStatus())
                        .ctime(itemBasicDTO.getCtime())
                        .sold(itemBasicDTO.getSold())
                        .historicalSold(itemBasicDTO.getHistoricalSold())
                        .likedCount(itemBasicDTO.getLikedCount())
                        .viewCount(itemBasicDTO.getViewCount())
                        .catId(itemBasicDTO.getCatId())
                        .brand(itemBasicDTO.getBrand())
                        .cmtCount(itemBasicDTO.getCmtCount())
                        .flag(itemBasicDTO.getFlag())
                        .cbOption(itemBasicDTO.getCbOption())
                        .itemStatus(itemBasicDTO.getItemStatus())
                        .price(itemBasicDTO.getPrice())
                        .priceMin(itemBasicDTO.getPriceMin())
                        .priceMax(itemBasicDTO.getPriceMax())
                        .priceMinBeforeDiscount(itemBasicDTO.getPriceMinBeforeDiscount())
                        .priceMaxBeforeDiscount(itemBasicDTO.getPriceMaxBeforeDiscount())
                        .priceBeforeDiscount(itemBasicDTO.getPriceBeforeDiscount())
                        .showDiscount(itemBasicDTO.getShowDiscount())
                        .rawDiscount(itemBasicDTO.getRawDiscount())
                        .shopLocation(itemBasicDTO.getShopLocation())
                        .build();
                return itemBasicRepository.save(itemBasic);
            }
        } catch (Exception e) {
            log.info(e);
        }
        return itemBasic;
    }

    protected Integer getJwt(String accessToken) {
        if (StringUtils.hasText(accessToken) && accessToken.startsWith("Bearer ")) {
            String jwt = accessToken.substring(7);
            return tokenProvider.getIdFromSubjectJWT(jwt);
        }
        return null;
    }

    protected String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    protected LoginResponseDTO getLoginResponse(LoginRequestDTO dto, User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getUsername(),
                        dto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = tokenProvider.generateAccessToken(user);
        String refreshToken = tokenProvider.generateRefreshToken(user);

        return new LoginResponseDTO(accessToken, refreshToken);
    }

    protected Set<Role> assignNameRoleToObject(List<String> roleNames, boolean isAdmin) {
        List<Role> roles;
        if (CollectionUtils.isEmpty(roleNames)) {
            roleNames = new ArrayList<>();
        }
        if (isAdmin) {
            roleNames.add(UserRole.ROLE_ADMIN.name());
        }
        if (CollectionUtils.isEmpty(roleNames)) {
            roleNames.add(UserRole.ROLE_USER.name());
        }
        roles = iRoleRepository.findAllByNames(roleNames)
                .orElseThrow(() -> new ErrorException(MessageResponse.ROLE_IS_NOT_VALID));
        return new HashSet<>(roles);
    }

    protected User findUserById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new ErrorException(MessageResponse.USER_HAS_NOT_EXIST));
    }

}
