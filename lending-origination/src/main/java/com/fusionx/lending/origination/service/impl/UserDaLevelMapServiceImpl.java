package com.fusionx.lending.origination.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.domain.DelegationAuthorityGroup;
import com.fusionx.lending.origination.domain.UserDaLevelMap;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.DaLimitRepository;
import com.fusionx.lending.origination.repository.DelegationAuthorityGroupRepository;
import com.fusionx.lending.origination.repository.UserDaLevelMapRepository;
import com.fusionx.lending.origination.resource.UserDaLevelMapAddResource;
import com.fusionx.lending.origination.resource.UserDaLevelMapUpdateResource;
import com.fusionx.lending.origination.resource.UserProfileResponce;
import com.fusionx.lending.origination.service.RemoteService;
import com.fusionx.lending.origination.service.UserDaLevelMapService;

/**
 * DA Limit User map Service Implementation
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   1   06-05-2021      		     FX-6269	Amal       Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class UserDaLevelMapServiceImpl implements UserDaLevelMapService {

	@Autowired
	private Environment environment;
	
	@Autowired
	private UserDaLevelMapRepository userDaLevelMapRepository;
	
	@Autowired
	private DelegationAuthorityGroupRepository dAGroupRepository;
	
	@Autowired
	DaLimitRepository daLimitRepository;
	
	@Autowired
	private RemoteService remoteService;
	
    @Value("${user-profile}")
	private String urlUserProfile;
	
	/*@Override
	public Page<UserDaLevelMap> findAll(Pageable pageable, Predicate predicate) {
		
		BooleanBuilder bb = new BooleanBuilder(predicate);
		Page<UserDaLevelMap> userDaLevelMapPage = null;
		userDaLevelMapPage = (Page<UserDaLevelMap>) userDaLevelMapRepository.findAll(bb.getValue(), pageable).getContent();
		for (UserDaLevelMap userDaLevelMap :userDaLevelMapPage) {
			userDaLevelMap.setAuthorityGroupId(userDaLevelMap.getDelegationAuthorityGroup().getId().toString());
			userDaLevelMap.setAuthorityGroupCode(userDaLevelMap.getDelegationAuthorityGroup().getCode());
			userDaLevelMap.setAuthorityGroupName(userDaLevelMap.getDelegationAuthorityGroup().getName());
		}
		return 	userDaLevelMapPage;
	}*/

	@Override
	public List<UserDaLevelMap> findAll() {
		  List<UserDaLevelMap> userDaLevelMapList= new ArrayList<>();
		   List<UserDaLevelMap> userDaLevelMaps = userDaLevelMapRepository.findAll();
		   for (UserDaLevelMap userDaLevelMap :userDaLevelMaps) {
			   userDaLevelMap.setAuthorityGroupId(userDaLevelMap.getDelegationAuthorityGroup().getId().toString());
			   userDaLevelMap.setAuthorityGroupCode(userDaLevelMap.getDelegationAuthorityGroup().getCode());
			   userDaLevelMap.setAuthorityGroupName(userDaLevelMap.getDelegationAuthorityGroup().getName());
			   userDaLevelMapList.add(userDaLevelMap);
		   }
		return userDaLevelMapList;
	}
    
	@Override
	public Optional<UserDaLevelMap> findById(Long id) {
		Optional<UserDaLevelMap> userDaLevelMap = userDaLevelMapRepository.findById(id);
		if (userDaLevelMap.isPresent()) {
			userDaLevelMap.get().setAuthorityGroupId(userDaLevelMap.get().getDelegationAuthorityGroup().getId().toString());
			userDaLevelMap.get().setAuthorityGroupCode(userDaLevelMap.get().getDelegationAuthorityGroup().getCode());
			userDaLevelMap.get().setAuthorityGroupName(userDaLevelMap.get().getDelegationAuthorityGroup().getName());
			return Optional.ofNullable(userDaLevelMap.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<UserDaLevelMap> findByStatus(String status) {
		List<UserDaLevelMap> userDaLevelMapList= new ArrayList<>();
		   List<UserDaLevelMap> userDaLevelMaps = userDaLevelMapRepository.findByStatus(status);
		   for (UserDaLevelMap userDaLevelMap :userDaLevelMaps) {
			   userDaLevelMap.setAuthorityGroupId(userDaLevelMap.getDelegationAuthorityGroup().getId().toString());
			   userDaLevelMap.setAuthorityGroupCode(userDaLevelMap.getDelegationAuthorityGroup().getCode());
			   userDaLevelMap.setAuthorityGroupName(userDaLevelMap.getDelegationAuthorityGroup().getName());
			   userDaLevelMapList.add(userDaLevelMap);
		   }
		return userDaLevelMapList;
	}


	@Override
	public Boolean existsById(Long id) {
		return userDaLevelMapRepository.existsById(id);
	}

	@Override
	public Long saveAndValidateUserDaLevelMap(String tenantId, String createdUser, UserDaLevelMapAddResource userDaLevelMapAddResource) {
		
		LoggerRequest.getInstance().logInfo("UserDaLevelMap********************************Validate Unique record*********************************************");
	
	   	Boolean isExists = userDaLevelMapRepository.existsByDelegationAuthorityGroupIdAndDaLevelAndUserIdAndStatus(Long.parseLong(userDaLevelMapAddResource.getAuthorityGroupId()),
	   			userDaLevelMapAddResource.getDaLevel(),Long.parseLong(userDaLevelMapAddResource.getUserId()),"ACTIVE");
		
		if(isExists!=null && isExists) {
			throw new ValidateRecordException(environment.getProperty("common.unique"), "message");
		}
	
		LoggerRequest.getInstance().logInfo("UserDaLevelMap********************************Save DA Limit User Map *********************************************");
		
		UserDaLevelMap userDaLevelMap=saveUserDaLevelMap(tenantId, createdUser, userDaLevelMapAddResource);
		
		return userDaLevelMap.getId();
	}

	private UserDaLevelMap saveUserDaLevelMap(String tenantId, String createdUser, UserDaLevelMapAddResource userDaLevelMapAddResource) {
	
		UserDaLevelMap userDaLevelMap = new UserDaLevelMap();

		
	   Optional<DelegationAuthorityGroup> dAGroup = dAGroupRepository.findById(Long.parseLong(userDaLevelMapAddResource.getAuthorityGroupId()));
		
		if (dAGroup.isPresent()) {
			if(!dAGroup.get().getName().equals(userDaLevelMapAddResource.getAuthorityGroupName())) {
				throw new ValidateRecordException(environment.getProperty("invalid.authorityGroup"), "authorityGroupName");
			}else {
				userDaLevelMap.setDelegationAuthorityGroup(dAGroup.get());
			}
			
		}else {
			throw new ValidateRecordException(environment.getProperty("invalid.authorityGroupId"), "authorityGroupId");
		}
		
		
	 	Boolean isExists = daLimitRepository.existsByDelegationAuthorityGroupIdAndDaLevelAndStatus(Long.parseLong(userDaLevelMapAddResource.getAuthorityGroupId()),
	   			userDaLevelMapAddResource.getDaLevel(),"ACTIVE");
		
		if(isExists==null || !isExists) {
			throw new ValidateRecordException(environment.getProperty("daLevel.invalid"), "message");
		}
		
	
		
		UserProfileResponce userProfileResponce = userProfileValidate(tenantId,userDaLevelMapAddResource.getUserId(),userDaLevelMapAddResource.getUserName());
		if(userProfileResponce!=null) {
			userDaLevelMap.setUserId(Long.parseLong(userDaLevelMapAddResource.getUserId()));
			userDaLevelMap.setUserIdCode(userProfileResponce.getUserId());
			userDaLevelMap.setUserName(userProfileResponce.getUserName());
		}
		
		userDaLevelMap.setDaLevel(userDaLevelMapAddResource.getDaLevel());
		userDaLevelMap.setTenantId(tenantId);
		userDaLevelMap.setStatus(userDaLevelMapAddResource.getStatus());
		userDaLevelMap.setCreatedUser(createdUser);
		userDaLevelMap.setCreatedDate(getCreateOrModifyDate());
		userDaLevelMap.setSyncTs(getCreateOrModifyDate());
		userDaLevelMap = userDaLevelMapRepository.saveAndFlush(userDaLevelMap);
		
		return userDaLevelMap;
	}
	
	@Override
	public Long updateAndValidateUserDaLevelMap(String tenantId, String createdUser, Long id, UserDaLevelMapUpdateResource userDaLevelMapUpdateResource) {
		
		LoggerRequest.getInstance().logInfo("UserDaLevelMap********************************Validate Version*********************************************");
		Optional<UserDaLevelMap> isUserDaLevelMap = userDaLevelMapRepository.findById(id);
		
		if(isUserDaLevelMap.isPresent() && !isUserDaLevelMap.get().getVersion().equals(Long.parseLong(userDaLevelMapUpdateResource.getVersion())))
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");
		
		LoggerRequest.getInstance().logInfo("UserDaLevelMap********************************Validate Unique*********************************************");

		Boolean isExists = userDaLevelMapRepository.existsByDelegationAuthorityGroupIdAndDaLevelAndUserIdAndStatusAndIdNotIn(Long.parseLong(userDaLevelMapUpdateResource.getAuthorityGroupId()),
				userDaLevelMapUpdateResource.getDaLevel(),Long.parseLong(userDaLevelMapUpdateResource.getUserId()),"ACTIVE",id);
		
		if(isExists!=null && isExists) {
			throw new ValidateRecordException(environment.getProperty("common.unique"), "message");
		}
		
		LoggerRequest.getInstance().logInfo("UserDaLevelMap********************************Update User DaLevel Map *********************************************");
		UserDaLevelMap userDaLevelMap = updateUserDaLevelMap(createdUser, userDaLevelMapUpdateResource, id);
		
		return userDaLevelMap.getId();
	}
	
	private UserDaLevelMap updateUserDaLevelMap(String createdUser,UserDaLevelMapUpdateResource userDaLevelMapUpdateResource, Long id) {
		
		UserDaLevelMap userDaLevelMap = userDaLevelMapRepository.getOne(id);
	
	   Optional<DelegationAuthorityGroup> dAGroup = dAGroupRepository.findById(Long.parseLong(userDaLevelMapUpdateResource.getAuthorityGroupId()));
		
		if (dAGroup.isPresent()) {
			if(!dAGroup.get().getName().equals(userDaLevelMapUpdateResource.getAuthorityGroupName())) {
				throw new ValidateRecordException(environment.getProperty("invalid.authorityGroup"), "authorityGroupName");
			}else {
				userDaLevelMap.setDelegationAuthorityGroup(dAGroup.get());
			}
			
		}else {
			throw new ValidateRecordException(environment.getProperty("invalid.authorityGroupId"), "authorityGroupId");
		}
		
		Boolean isExists = daLimitRepository.existsByDelegationAuthorityGroupIdAndDaLevelAndStatus(Long.parseLong(userDaLevelMapUpdateResource.getAuthorityGroupId()),
				userDaLevelMapUpdateResource.getDaLevel(),"ACTIVE");
		
		if(isExists==null || !isExists) {
			throw new ValidateRecordException(environment.getProperty("daLevel.invalid"), "message");
		}
			
		UserProfileResponce userProfileResponce = userProfileValidate(userDaLevelMap.getTenantId(),userDaLevelMapUpdateResource.getUserId(),userDaLevelMapUpdateResource.getUserName());
		if(userProfileResponce!=null) {
			userDaLevelMap.setUserId(Long.parseLong(userDaLevelMapUpdateResource.getUserId()));
			userDaLevelMap.setUserIdCode(userProfileResponce.getUserId());
			userDaLevelMap.setUserName(userProfileResponce.getUserName());
		}
		userDaLevelMap.setDaLevel(userDaLevelMapUpdateResource.getDaLevel());
		userDaLevelMap.setStatus(userDaLevelMapUpdateResource.getStatus());
		userDaLevelMap.setModifiedUser(createdUser);
		userDaLevelMap.setModifiedDate(getCreateOrModifyDate());
		userDaLevelMap.setSyncTs(getCreateOrModifyDate());
		userDaLevelMap = userDaLevelMapRepository.saveAndFlush(userDaLevelMap);
		
		return userDaLevelMap;
	}

	private Timestamp getCreateOrModifyDate() {
		Calendar calendar = Calendar.getInstance();
    	java.util.Date now = calendar.getTime();
    	return new Timestamp(now.getTime());
	}
	
	
	//----------------------Validate User profile from the remote service---------------------------
	public UserProfileResponce userProfileValidate(String tenantId, String userId, String userName) {
	
		UserProfileResponce userProfileResponce=(UserProfileResponce)remoteService.checkIsExist(tenantId, userId, urlUserProfile,  UserProfileResponce.class);
			if(userProfileResponce == null||userProfileResponce.getProfileStatus()==null)
				throw new ValidateRecordException(environment.getProperty("invalid.userId"), "userId");
			else if(!(userProfileResponce.getProfileStatus().equals("ACTIVE")) || !(userProfileResponce.getUserStatus().equals("ACTIVE"))) {
				throw new ValidateRecordException(environment.getProperty("invalid.userId"), "userId");
			}else if(userProfileResponce.getUserName()!=null && !userProfileResponce.getUserName().equalsIgnoreCase(userName)) {
				throw new ValidateRecordException(environment.getProperty("invalid.userName"), "userName");
			}
			return userProfileResponce;
	}

	@Override
	public List<UserDaLevelMap> findByDelegationAuthorityGroupIdAndDaLevel(Long groupid, String level) {
		List<UserDaLevelMap> userDaLevelMapList= new ArrayList<>();
		   List<UserDaLevelMap> userDaLevelMaps =  userDaLevelMapRepository.findByDelegationAuthorityGroupIdAndDaLevel(groupid, level);
		   for (UserDaLevelMap userDaLevelMap :userDaLevelMaps) {
			   userDaLevelMap.setAuthorityGroupId(userDaLevelMap.getDelegationAuthorityGroup().getId().toString());
			   userDaLevelMap.setAuthorityGroupCode(userDaLevelMap.getDelegationAuthorityGroup().getCode());
			   userDaLevelMap.setAuthorityGroupName(userDaLevelMap.getDelegationAuthorityGroup().getName());
			   userDaLevelMapList.add(userDaLevelMap);
		   }
		return userDaLevelMapList;
	}

}
