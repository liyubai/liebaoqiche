package com.ibest.activity.service;

import java.util.ArrayList;
import java.util.List;

import com.ibest.framework.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ibest.framework.common.utils.PageList;

import com.ibest.activity.dao.UserActivityDao;
import com.ibest.activity.entity.UserActivity;
import com.ibest.activity.dto.input.UserActivityInputDTO;

@Service
@Transactional(readOnly=true)
public class UserActivityService {

	@Autowired
	protected UserActivityDao userActivityDao;
	
	public UserActivity findById(String id) throws Exception{
		return userActivityDao.findById(id);
	}
	
	@Transactional(readOnly=false)
	public int insert(UserActivity userActivity) throws Exception{
		int result = userActivityDao.insert(userActivity);
		return result;
	}
	
	@Transactional(readOnly=false)
	public int deleteById(String id) throws Exception{
		int result = userActivityDao.deleteById(id);
		return result;
	}
	
	@Transactional(readOnly=false)
	public int deleteByIds(String ids) throws Exception{
		int result = userActivityDao.deleteByIds(StringUtils.tokenizeToList(ids));
		return result;
	}
	
	@Transactional(readOnly=false)
	public int update(UserActivity userActivity) throws Exception{
		userActivity.preUpdate();
		int result = userActivityDao.update(userActivity);
		return result;
	}
	
	public List<UserActivity> findByAccessSystemId(String activitySystemId) throws Exception{
		return userActivityDao.findByAccessSystemId(activitySystemId);
	}
	
	/**
	 * 
	 * @param findByObject
	 * @throws Exception
	 */
	public void showAppId(List<UserActivity> findByObject) throws Exception{
		for (UserActivity userActivity : findByObject) {
			if(userActivity.getAppId().equals("00")){
				userActivity.setAppId("APP");
			}
			if(userActivity.getAppId().equals("01")){
				userActivity.setAppId("PC");
			}
			if(userActivity.getAppId().equals("02")){
				userActivity.setAppId("Android");
			}
		}
	}
	
	/**
	 * 分页查询
	 * @param page
	 * @param inputDto
	 * @return
	 * @throws Exception
	 */
	public PageList<UserActivity> findByPage(PageList<UserActivity> page, UserActivityInputDTO inputDto) throws Exception{
		
		if(page == null){
			page = new PageList<UserActivity>();
		}
		/*List<UserActivity> findByObject = userActivityDao.findByObject(inputDto);
		this.showAppId(findByObject);*/
		long totalCount = userActivityDao.countByObject(inputDto);
		if(totalCount > 0){
			// 设置记录总条数
			page.setTotal(totalCount);
			
			// 设置分页参数，查询数据
			inputDto.setLimitStart((page.getPage() - 1) * page.getPageSize());
			inputDto.setLimitSize(page.getPageSize());
			page.setRows(userActivityDao.findByObject(inputDto));
		}
		
		return page;
	}
	
	/**
	* 查询列表
	*/
	public List<UserActivity> findByObject(UserActivityInputDTO inputDto) throws Exception{
		return userActivityDao.findByObject(inputDto);
	}

	public UserActivity findOneByObject(UserActivityInputDTO inputDto) throws Exception{
		return userActivityDao.findOneByObject(inputDto);
	}
}
