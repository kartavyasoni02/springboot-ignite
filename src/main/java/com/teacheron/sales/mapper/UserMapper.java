package com.teacheron.sales.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.teacheron.sales.dto.UserDto;
import com.teacheron.sales.entities.UserEntry;

@Component
public class UserMapper {

	public List<UserDto> mapToDtos(List<UserEntry> userEntryList){
		List<UserDto> list = new ArrayList<>();
		userEntryList.stream().forEach(en -> list.add(mapToDto(en)));
		return list;
	}
	
	public List<UserEntry> mapToEntities(List<UserDto> userDtoList){
		List<UserEntry> list = new ArrayList<>();
		userDtoList.stream().forEach(dto -> list.add(mapToEntry(dto)));
		return list;
	}
	
	public UserDto mapToDto(UserEntry userEntry){
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userEntry, userDto);
		return userDto;
	}
	
	public UserEntry mapToEntry(UserDto userDto){
		UserEntry userEntry = new UserEntry();
		BeanUtils.copyProperties(userDto, userEntry);
		return userEntry;
	}
	
}
