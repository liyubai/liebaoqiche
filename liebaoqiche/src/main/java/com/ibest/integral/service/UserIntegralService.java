package com.ibest.integral.service;

import java.util.Date;
import java.util.List;
import com.ibest.framework.common.utils.StringUtils;
import com.ibest.framework.common.utils.UserUtils;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ibest.framework.common.utils.PageList;

import com.ibest.integral.dao.UserIntegralDao;
import com.ibest.integral.entity.UserIntegral;
import com.ibest.integral.entity.UserIntegralCount;
import com.ibest.user.dao.UserDao;
import com.ibest.user.entity.User;
import com.ibest.user.entity.UserCars;
import com.ibest.user.service.UserCarsService;
import com.ibest.utils.RandomUtils;
import com.ibest.integral.dto.input.IntegralInputDTO;
import com.ibest.integral.dto.input.UserIntegralCountInputDTO;
import com.ibest.integral.dto.input.UserIntegralInputDTO;

@Service
@Transactional(readOnly=true)
public class UserIntegralService {

	@Autowired
	protected UserIntegralDao userIntegralDao;
	@Autowired
	protected UserDao userDao;
	@Autowired
	protected UserIntegralCountService userIntegralCountService;
	@Autowired
	protected UserCarsService usercars;
	
	public UserIntegral findById(String id) throws Exception{
		return userIntegralDao.findById(id);
	}
	
	public List<UserIntegral> findByUserId(String userId) throws Exception{
		return userIntegralDao.findByUserId(userId);
	}
	
	
	
	@Transactional(readOnly=false)
	public boolean integralUpdateFrank(UserIntegral userIntegral) throws Exception {
		boolean flag =true;
		UserIntegral userIntegral2=new UserIntegral();
		userIntegral2.setId(RandomUtils.RandomUUID());
		userIntegral2.setUserId(userIntegral.getUserId());
		userIntegral2.setAccessSystemId("后台操作系统");
		userIntegral2.setVinNo(userIntegral.getVinNo());
		userIntegral2.setAppId("05");
		userIntegral2.setType(userIntegral.getType());
		userIntegral2.setConsumeNum(Integer.valueOf(userIntegral.getConsumeNum()));
		userIntegral2.setCreateDate(new Date());
		userIntegral2.setModifyTime(new Date());			
		int insert = this.insert(userIntegral2);
		if(insert == 0){
			flag = false;
		}
		
		UserIntegralCountInputDTO inputDto=new UserIntegralCountInputDTO();		
		inputDto.setAccessSystemId("后台操作系统");
		inputDto.setAppId("05");
		inputDto.setUserId(userIntegral.getUserId());
		inputDto.setVinNo(userIntegral.getVinNo());
		inputDto.setType(userIntegral.getType());
		
		UserIntegralCount userIntegralCount=new UserIntegralCount();
		userIntegralCount.setAccessSystemId("后台操作系统");	
		userIntegralCount.setAppId("05");
		userIntegralCount.setUserId(userIntegral.getUserId());	
		userIntegralCount.setVinNo(userIntegral.getVinNo());
		userIntegralCount.setType(userIntegral.getType());
	
		
		UserIntegralCount userIntegralCount2=userIntegralCountService.findOneByObject(inputDto);
		String type=userIntegral.getType();		
		if(userIntegralCount2 == null) {
			userIntegralCount.setId(RandomUtils.RandomUUID());
			if(type.equals("00")) {
				userIntegralCount.setNumber(Integer.valueOf(userIntegral.getConsumeNum()));
			}else if (type.equals("01")) {
				userIntegralCount.setNumber(-Integer.valueOf(userIntegral.getConsumeNum()));
			}
			userIntegralCount.setCreateDate(new Date());
			userIntegralCount.setModifyTime(new Date());
			int insert2 = userIntegralCountService.insert(userIntegralCount);
			if(insert2 == 0){
				flag = false;
			}
		}else {	
			int num=0;
			if(type.equals("00")) {
				num=userIntegralCount2.getNumber()+Integer.valueOf(userIntegral.getConsumeNum());
			}else if (type.equals("01")) {
				num=userIntegralCount2.getNumber()-Integer.valueOf(userIntegral.getConsumeNum());
			}			
			userIntegralCount2.setNumber(num);
			userIntegralCount2.setModifyTime(new Date());
			int update = userIntegralCountService.integralUpdate(userIntegralCount2);
			if(update == 0){
				flag = false;
			}
		}
		return flag;
		
	}
	
	/**
	 * 积分更新
	 * @param userIntegral
	 * @throws Exception
	 */
	@Transactional(readOnly=false)
	public boolean integralUpdate(UserIntegral userIntegral)throws Exception{
		boolean flag =true;
		/**
		 * 1，用户积分积分记录所有的操作
		 * 2，用户积分统计表中只能有一个用户的数据
		 */
		List<UserIntegral> findByUserId = this.findByUserId(userIntegral.getUserId());
		List<UserCars> cars = usercars.findVinNoByUserId(userIntegral.getUserId());
		String vinNo =null;
		if(cars!=null && cars.size()>0){
			vinNo =cars.get(0).getVinCode();
		}
		UserIntegral newUser = new UserIntegral();
		newUser.setId(RandomUtils.RandomUUID());
		if(findByUserId!=null && findByUserId.size()>0){
			UserIntegral oldUser = findByUserId.get(0);
			newUser.setAccessSystemId(oldUser.getAccessSystemId());
			newUser.setAppId(oldUser.getAppId());
		}else{
			newUser.setAccessSystemId("后台操作系统");
			newUser.setAppId("05");
		}
			newUser.setUserId(userIntegral.getUserId());
			newUser.setVinNo(vinNo==null?"没有车":vinNo);
			newUser.setConsumeNum(userIntegral.getConsumeNum());
			newUser.setType(userIntegral.getType());
			newUser.setCreater(UserUtils.getCurrentUser().getRealname());
			newUser.setCreateTime(new Date());
			newUser.setModifier(UserUtils.getCurrentUser().getRealname());
			newUser.setModifyTime(new Date());
			int result = this.insert(newUser);
			if(result == 0){
				flag = false;
			}
			
		/*List<UserIntegralCount> userIntegralCounts= 
					userIntegralCountService.findByUserId(newUser.getUserId());*/
		
	/*	if(userIntegralCounts.size()==0){*/
			String type =newUser.getType();
			UserIntegralCount userCount= new UserIntegralCount();
			userCount.setId(RandomUtils.RandomUUID());
			userCount.setAccessSystemId(newUser.getAccessSystemId());
			userCount.setAppId(newUser.getAppId());
			userCount.setUserId(newUser.getUserId());
			userCount.setVinNo(vinNo==null?"没有车":vinNo);
			userCount.setType(newUser.getType());
			if(type.equals("00")){
				userCount.setNumber(newUser.getConsumeNum());
			}
			if(type.equals("01")){
				String num ="-"+newUser.getConsumeNum();
				userCount.setNumber(Integer.parseInt(num));;
			}
			userCount.setCreater(UserUtils.getCurrentUser().getRealname());
			userCount.setCreateTime(new Date());
			userCount.setModifier(UserUtils.getCurrentUser().getRealname());
			userCount.setModifyTime(new Date());
			int insert = userIntegralCountService.insert(userCount);
			if(insert == 0){
				flag = false;
			}
		/*}else{
			UserIntegralCount userIntegralCount = userIntegralCounts.get(0);
			String type = newUser.getType();
			int num=0;
			if(type.equals("00")) {
				num=userIntegralCount.getNumber()+Integer.valueOf(newUser.getIntegral());
			}else if (type.equals("01")) {
				num=userIntegralCount.getNumber()-Integer.valueOf(newUser.getIntegral());
			}
			userIntegralCount.setNumber(num);
			userIntegralCount.setModifier(UserUtils.getCurrentUser().getRealname());
			userIntegralCount.setModifyTime(new Date());
			int update = userIntegralCountService.integralUpdate(userIntegralCount);
			if(update == 0){
				flag =false;
			}
		}*/
		
		
		
		
		
		/*UserIntegral integral = this.findByUserId(userIntegral.getUserId());
		User user = userDao.findById(userIntegral.getUserId());
		List<UserCars> cars = usercars.findVinNoByUserId(userIntegral.getUserId());
		String vinNo =null;
		if(cars!=null && cars.size()>0){
			vinNo =cars.get(0).getVinCode();
		}
		if(integral!=null){
			integral.setIntegral(userIntegral.getIntegral());
			integral.setType(userIntegral.getType());
			integral.setModifier(UserUtils.getCurrentUser().getRealname());
			integral.setModifyTime(new Date());
			this.update(integral);
		}else{
			userIntegral.setId(RandomUtils.RandomUUID());
			userIntegral.setAccessSystemId("积分卡券系统");
			userIntegral.setAppId("后台PC");
			userIntegral.setUserId(userIntegral.getUserId());
			userIntegral.setVinNo(vinNo==null?"没有车":vinNo);
			userIntegral.setIntegral(userIntegral.getIntegral());
			userIntegral.setType(userIntegral.getType());
			userIntegral.setCreater(UserUtils.getCurrentUser().getRealname());
			userIntegral.setCreateTime(new Date());
			int result = this.insert(userIntegral);
			if(result == 0){
				flag = false;
			}
		}
			UserIntegralCount userIntegralCount = userIntegralCountService.findByUserId(userIntegral.getUserId());
			
			if(userIntegralCount == null){
				UserIntegralCount userCount= new UserIntegralCount();
				userCount.setId(RandomUtils.RandomUUID());
				userCount.setAccessSystemId(userIntegral.getAccessSystemId());
				userCount.setAppId(userIntegral.getAppId());
				userCount.setUserId(userIntegral.getUserId());
				userCount.setVinNo(vinNo==null?"没有车":vinNo);
				userCount.setType(userIntegral.getType());
				userCount.setNumber(Integer.valueOf(userIntegral.getIntegral()));
				userCount.setCreater(UserUtils.getCurrentUser().getRealname());
				userCount.setCreateTime(new Date());
				int insert = userIntegralCountService.insert(userCount);
				if(insert == 0){
					flag = false;
				}
			}else{
				String type = userIntegral.getType();
				int num=0;
				if(type.equals("00")) {
					num=userIntegralCount.getNumber()+Integer.valueOf(userIntegral.getIntegral());
				}else if (type.equals("01")) {
					num=userIntegralCount.getNumber()-Integer.valueOf(userIntegral.getIntegral());
				}
				
				userIntegralCount.setNumber(num);
				userIntegralCount.setModifier(UserUtils.getCurrentUser().getRealname());
				userIntegralCount.setModifyTime(new Date());
				int update = userIntegralCountService.integralUpdate(userIntegralCount);
				if(update == 0){
					flag =false;
				}
			}*/
			
		return flag;
	}
	
	@Transactional(readOnly=false)
	public int insert(UserIntegral userIntegral) throws Exception{
		userIntegral.preInsert();
		int result = userIntegralDao.insert(userIntegral);
		return result;
	}
	
	@Transactional(readOnly=false)
	public int deleteById(String id) throws Exception{
		int result = userIntegralDao.deleteById(id);
		return result;
	}
	
	@Transactional(readOnly=false)
	public int deleteByIds(String ids) throws Exception{
		int result = userIntegralDao.deleteByIds(StringUtils.tokenizeToList(ids));
		return result;
	}
	
	@Transactional(readOnly=false)
	public int update(UserIntegral userIntegral) throws Exception{
		userIntegral.preUpdate();
		int result = userIntegralDao.update(userIntegral);
		return result;
	}
	
	/**
	 * 显示应用程序中文名
	 * @param findByObject
	 */
	public void showAppId(List<UserIntegral> findByObject){
		for (UserIntegral userIntegral : findByObject) {
			if(userIntegral.getAppId().equals("00")){
				userIntegral.setAppId("APP");
			}
			if(userIntegral.getAppId().equals("01")){
				userIntegral.setAppId("PC");
			}
			if(userIntegral.getAppId().equals("02")){
				userIntegral.setAppId("Android");
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
	public PageList<UserIntegral> findByPage(PageList<UserIntegral> page, UserIntegralInputDTO inputDto) throws Exception{
		
		if(page == null){
			page = new PageList<UserIntegral>();
		}
		/*List<UserIntegral> findByObject = userIntegralDao.findByObject(inputDto);
		this.showAppId(findByObject);*/
		long totalCount = userIntegralDao.countByObject(inputDto);
		if(totalCount > 0){
			// 设置记录总条数
			page.setTotal(totalCount);
			
			// 设置分页参数，查询数据
			inputDto.setLimitStart((page.getPage() - 1) * page.getPageSize());
			inputDto.setLimitSize(page.getPageSize());
			page.setRows(userIntegralDao.findByObject(inputDto));
		}
		
		return page;
	}
	
	/**
	* 查询列表
	*/
	public UserIntegral findByObject(UserIntegralInputDTO inputDto) throws Exception{
		return userIntegralDao.findOneByObject(inputDto);
	}

}
