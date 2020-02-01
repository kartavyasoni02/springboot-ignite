package com.teacheron.sales.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.teacheron.sales.domain.UserDomain;
import com.teacheron.sales.dto.UserDto;

@Component
public class UserMapper {

	public List<UserDto> mapToDtos(List<UserDomain> userEntryList){
		List<UserDto> list = new ArrayList<>();
		userEntryList.stream().forEach(en -> list.add(mapToDto(en)));
		return list;
	}
	
	public List<UserDomain> mapToEntities(List<UserDto> userDtoList){
		List<UserDomain> list = new ArrayList<>();
		userDtoList.stream().forEach(dto -> list.add(mapToDomain(dto)));
		return list;
	}
	
	public UserDto mapToDto(UserDomain userEntry){
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userEntry, userDto);
		return userDto;
	}
	
	public UserDomain mapToDomain(UserDto userDto){
		UserDomain userEntry = new UserDomain();
		BeanUtils.copyProperties(userDto, userEntry);
		return userEntry;
	}
	
}
