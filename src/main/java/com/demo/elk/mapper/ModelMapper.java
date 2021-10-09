package com.demo.elk.mapper;

import com.demo.elk.dto.userDTO.CreditCardDTO;
import com.demo.elk.dto.userDTO.UserDTO;
import com.demo.elk.dto.shop.shopee.ItemBasicDTO;
import com.demo.elk.dto.shop.shopee.ItemDTO;
import com.demo.elk.dto.shop.shopee.ShopDTO;
import com.demo.elk.entity.role.Role;
import com.demo.elk.entity.shop.Item;
import com.demo.elk.entity.shop.ItemBasic;
import com.demo.elk.entity.shop.Shop;
import com.demo.elk.entity.user.CreditCard;
import com.demo.elk.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ModelMapper {

    @Mappings({
            @Mapping(target = "itemId", source = "id")
    })
    ItemBasicDTO convertItemBasicToDTO(ItemBasic itemBasic);

    @Mapping(target = "itemBasicDTO", source = "itemBasic")
    ItemDTO convertItemToDTO(Item item);

    @Mappings({})
    ShopDTO convertShopToDTO(Shop shop);

    @Mappings({
            @Mapping(target = "uid", source = "uid"),
            @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRoleObjectToString")
    })
    UserDTO convertUserToDTO(User user);

    static Set<String> mapRoleObjectToString(Set<Role> roles) {
        return roles.stream().map(Role::getName).collect(Collectors.toSet());
    }

    @Mappings({
            @Mapping(target = "bankName", source = "bankName"),
            @Mapping(target = "creditType", source = "creditType"),
            @Mapping(target = "userId", source = "user.id"),
            @Mapping(target = "createdAt", source = "createdAt")
    })
    CreditCardDTO convertCreditCardToCreditCardDTO(CreditCard creditCard);
}
