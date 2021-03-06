package com.ibest.activity.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.ibest.accesssystem.entity.SystemActivity;
import com.ibest.accesssystem.service.SystemActivityService;
import com.ibest.activity.entity.*;
import com.ibest.card.dao.CardDao;
import com.ibest.card.dto.input.CardInputDTO;
import com.ibest.card.dto.input.UserCardCountInputDTO;
import com.ibest.card.entity.Card;
import com.ibest.card.entity.UserCard;
import com.ibest.card.entity.UserCardCount;
import com.ibest.card.service.CardService;
import com.ibest.card.service.UserCardCountService;
import com.ibest.card.service.UserCardService;
import com.ibest.experience.dao.ExperienceDao;
import com.ibest.experience.dto.input.ExperienceInputDTO;
import com.ibest.experience.dto.input.UserExperienceCountInputDTO;
import com.ibest.experience.entity.Experience;
import com.ibest.experience.entity.UserExperience;
import com.ibest.experience.entity.UserExperienceCount;
import com.ibest.experience.service.ExperienceService;
import com.ibest.experience.service.UserExperienceCountService;
import com.ibest.experience.service.UserExperienceService;
import com.ibest.framework.common.utils.StringUtils;
import com.ibest.integral.dao.IntegralDao;
import com.ibest.integral.dto.input.IntegralInputDTO;
import com.ibest.integral.dto.input.UserIntegralCountInputDTO;
import com.ibest.integral.entity.Integral;
import com.ibest.integral.entity.IntegralCategory;
import com.ibest.integral.entity.UserIntegral;
import com.ibest.integral.entity.UserIntegralCount;
import com.ibest.integral.service.IntegralCategoryService;
import com.ibest.integral.service.IntegralService;
import com.ibest.integral.service.UserIntegralCountService;
import com.ibest.integral.service.UserIntegralService;
import com.ibest.user.service.UserService;
import com.ibest.utils.RandomUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ibest.framework.common.utils.PageList;
import com.ibest.activity.dao.ActivityCardDao;
import com.ibest.activity.dao.ActivityDao;
import com.ibest.activity.dao.ActivityDetailsDao;
import com.ibest.activity.dao.ActivityIntegralDao;
import com.ibest.activity.dto.input.ActivityCardInputDTO;
import com.ibest.activity.dto.input.ActivityDetailsInputDTO;
import com.ibest.activity.dto.input.ActivityInputDTO;
import com.ibest.activity.dto.input.ActivityIntegralInputDTO;

@Service
@Transactional(readOnly = true)
public class ActivityService {

	@Autowired
	protected ActivityDao activityDao;

	@Autowired
	protected ActivityDetailsDao activityDetailsDao;

	@Autowired
	private ActivityIntegralDao activityIntegralDao;

	@Autowired
	private ActivityCardDao activityCardDao;

	@Autowired
	private IntegralDao integralDao;

	@Autowired
	private CardDao cardDao;

	@Autowired
	private ExperienceDao expDao;

	@Autowired
	protected UserService userService;

	@Autowired
	private ActivityDetailsService activityDetailsService;

	@Autowired
	private ActivityCardService activityCardService;
	@Autowired
	private ActivityIntegralService activityIntegralService;
	@Autowired
	private SystemActivityService systemActivityService;
	@Autowired
	private UserActivityService userActivityService;

	@Autowired
	private IntegralCategoryService integralCategoryService;
	@Autowired
	private IntegralService integralService;
	@Autowired
	private UserIntegralService userIntegralService;
	@Autowired
	private UserIntegralCountService userIntegralCountService;

	@Autowired
	private CardService cardService;
	@Autowired
	private UserCardService userCardService;
	@Autowired
	private UserCardCountService userCardCountService;

	@Autowired
	private ExperienceService experienceService;
	@Autowired
	protected UserExperienceService userExperienceService;
	@Autowired
	protected UserExperienceCountService userExperienceCountService;

	public Activity findById(String id) throws Exception {
		return activityDao.findById(id);
	}

	public Activity findByDetailsId(String detailsId) throws Exception {
		return activityDao.findByDetailsId(detailsId);
	}

	@Transactional(readOnly = false)
	public int insert(Activity activity) throws Exception {
		activity.preInsert();
		int result = activityDao.insert(activity);
		return result;
	}

	@Transactional(readOnly = false)
	public int deleteById(String id) throws Exception {
		int result = activityDao.deleteById(id);
		return result;
	}

	@Transactional(readOnly = false)
	public int deleteByIds(String ids) throws Exception {
		int result = activityDao.deleteByIds(StringUtils.tokenizeToList(ids));
		return result;
	}

	@Transactional(readOnly = false)
	public int update(Activity activity) throws Exception {
		int result = activityDao.update(activity);
		return result;
	}

	public Activity findIdByDetailsId(String activityDetailsId) throws Exception {
		return activityDao.findIdByDetailsId(activityDetailsId);
	}

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @param inputDto
	 * @return
	 * @throws Exception
	 */
	public PageList<Activity> findByPage(PageList<Activity> page, ActivityInputDTO inputDto) throws Exception {

		if (page == null) {
			page = new PageList<Activity>();
		}

		long totalCount = activityDao.countByObject(inputDto);
		if (totalCount > 0) {
			// 设置记录总条数
			page.setTotal(totalCount);

			// 设置分页参数，查询数据
			inputDto.setLimitStart((page.getPage() - 1) * page.getPageSize());
			inputDto.setLimitSize(page.getPageSize());
			/*String detailsId = "";
			String id = "";
			List<Activity> activityList = activityDao.findByObject(inputDto);
			ActivityDetailsInputDTO activityDetailsInputDto = new ActivityDetailsInputDTO();
			ActivityIntegralInputDTO activityIntegralInputDto = new ActivityIntegralInputDTO();
			ActivityCardInputDTO activityCardInputDto = new ActivityCardInputDTO();
			List<ActivityDetails> activityDetails = activityDetailsDao.findByObject(activityDetailsInputDto);
			List<ActivityIntegral> activityIntegrals = activityIntegralDao.findByObject(activityIntegralInputDto);
			List<ActivityCard> activityCards = activityCardDao.findByObject(activityCardInputDto);
			if (activityList != null && activityList.size() > 0) {
				for (Activity activity : activityList) {
					detailsId = activity.getActivityDetailsId();
					if (activityDetails != null && activityDetails.size() > 0) {
						for (ActivityDetails details : activityDetails) {
							id = details.getId();
							if (detailsId.equals(id)) {
								activity.setActivityDetailsName(details.getActivityName());
							}
						}
					}
				}
			}

			ExperienceInputDTO experienceInputDto = new ExperienceInputDTO();
			List<Experience> experiences = expDao.findByObject(experienceInputDto);

			if (experiences != null && experiences.size() > 0) {
				if (activityList != null && activityList.size() > 0) {
					for (Activity activity : activityList) {
						for (Experience experience : experiences) {
							if (experience.getId().equals(activity.getExperienceId())) {
								activity.setIsExperience("已关联");
							}
						}
					}
				}
			}

			CardInputDTO cardInputDTO = new CardInputDTO();
			List<Card> cards = cardDao.findByObject(cardInputDTO);
			if (activityList != null && activityList.size() > 0) {
				for (Activity activity : activityList) {
					if (activityCards != null && activityCards.size() > 0) {
						for (ActivityCard activityCard : activityCards) {
							if (activity.getId().equals(activityCard.getActivityId())) {
								if (cards != null && cards.size() > 0) {
									for (Card card : cards) {
										if (activityCard.getCardId().equals(card.getId())) {
											activity.setIsCard("已关联");
										}
									}
								}
							}
						}
					}
				}
			}

			IntegralInputDTO integralInputDTO = new IntegralInputDTO();
			List<Integral> integrals = integralDao.findByObject(integralInputDTO);
			if (activityList != null && activityList.size() > 0) {
				for (Activity activity : activityList) {
					if (activityIntegrals != null && activityIntegrals.size() > 0) {
						for (ActivityIntegral activityIntegral : activityIntegrals) {
							if (activity.getId().equals(activityIntegral.getActivityId())) {
								if (integrals != null && integrals.size() > 0) {
									for (Integral integral : integrals) {
										if (activityIntegral.getIntegralId().equals(integral.getId())) {
											activity.setIsIntegral("已关联");
										}
									}
								}
							}
						}
					}
				}
			}
			if (activityList != null && activityList.size() > 0) {
				for (Activity activity : activityList) {
					if ((activity.getIsCard() == "" || activity.getIsCard() == null)
							&& (activity.getIsExperience() == "" || activity.getIsExperience() == null)
							&& (activity.getIsIntegral() == "" || activity.getIsIntegral() == null)) {
						activity.setState("0");
					}
				}
			}
			
			String activityDetailsName = inputDto.getActivityDetailsName();
			if (!StringUtils.isBlank(activityDetailsName)) {
				for (int activity = activityList.size()-1;  activity>=0; activity--) {
					String name = activityList.get(activity).getActivityDetailsName();
					if(!name.contains(activityDetailsName)) {
						activityList.remove(activity);
					}
				}
			}*/
			List<Activity> findByObject = activityDao.findByObject(inputDto);
			if(findByObject!=null && findByObject.size()>0){
				for (Activity activity : findByObject) {
					String experienceId = activity.getExperienceId();
					if("".equals(experienceId)){
						activity.setIsExperience("未关联");
					}else{
						activity.setIsExperience("已关联");
					}
					int card = activityCardService.checkByActivityId(activity.getId());
					if(card>0){
						activity.setIsCard("已关联");
					}else{
						activity.setIsCard("未关联");
					}
					int integral = activityIntegralService.checkByActivityId(activity.getId());
					if(integral>0){
						activity.setIsIntegral("已关联");
					}else{
						activity.setIsIntegral("未关联");
					}	
				}
			}
			page.setRows(findByObject);
		}

		return page;
	}

	/**
	 * 查询经验值的列表
	 * 
	 * @param experienceId
	 * @return
	 * @throws Exception
	 */
	public List<Activity> findByExperienceId(String experienceId) throws Exception {
		return activityDao.findByExperienceId(experienceId);
	}

	/**
	 * 查询列表
	 */
	public Activity findByObject(ActivityInputDTO inputDto) throws Exception {
		return activityDao.findOneByObject(inputDto);
	}

	/**
	 * 查询列表
	 */
	public List<Activity> findALL() throws Exception {
		return activityDao.findALL();
	}

	////////////////////////// 参与活动
	@Transactional(readOnly = false)
	public String partakeActivity(ActivityInputDTO activityInputDTO) throws Exception {
		
		
		String activitySystemId=activityInputDTO.getActivitySystemId();
		
		
		SystemActivity systemActivityOut=systemActivityService.findByFrankId(activitySystemId);
		
		
		String activityId=systemActivityOut.getActivityId();				
		Activity activity=activityDao.findById(activityId);	
		
		String activityDetailsId=activity.getActivityDetailsId();
		ActivityDetails activityDetails=activityDetailsService.findById(activityDetailsId);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
		Date endTime=sdf.parse(activityDetails.getEndTime());
		Date now=new Date();
			
		if(endTime.getTime()-now.getTime()<0) {
			return "系统活动已经过期！！！";
		}
		
		UserActivity userActivity = new UserActivity();
		userActivity.setId(RandomUtils.RandomUUID());
		userActivity.setAccessSystemId(activityInputDTO.getSystemSign());
		userActivity.setAppId(activityInputDTO.getAppSign());
		userActivity.setUserId(activityInputDTO.getUserId());
		userActivity.setVinNo(activityInputDTO.getVinNo());
		userActivity.setActivityId(activityId);
		userActivity.setActivitySystemId(activitySystemId);
		userActivity.setParticipationActivityTime(new Date());
		userActivity.setCreateDate(new Date());
		userActivity.setModifyTime(new Date());		
		userActivityService.insert(userActivity);
		
		String experienceId=activity.getExperienceId();	
		
		if(StringUtils.isNotBlank(experienceId)) {
			Experience experience=experienceService.findById(experienceId);
			
			UserExperience userExperience=new UserExperience();
			userExperience.setId(RandomUtils.RandomUUID());
			userExperience.setAccessSystemId(activityInputDTO.getSystemSign());
			userExperience.setAppId(activityInputDTO.getAppSign());
			userExperience.setUserId(activityInputDTO.getUserId());
			userExperience.setVinNo(activityInputDTO.getVinNo());
			userExperience.setActivitySystemId(activitySystemId);
			userExperience.setActivityId(activityId);
			userExperience.setExperienceId(experienceId);
			userExperience.setConsumeNum(Integer.valueOf(experience.getExp()));
			userExperience.setType("00");
			userExperience.setCreateDate(new Date());
			userExperience.setModifyTime(new Date());	
			userExperienceService.insert(userExperience);
			

			UserExperienceCountInputDTO inputDto=new UserExperienceCountInputDTO();		
			inputDto.setAccessSystemId(activityInputDTO.getSystemSign());
			inputDto.setAppId(activityInputDTO.getAppSign());
			inputDto.setUserId(activityInputDTO.getUserId());
			inputDto.setVinNo(activityInputDTO.getVinNo());
			
			UserExperienceCount userExperienceCount=new UserExperienceCount();
			userExperienceCount.setAccessSystemId(activityInputDTO.getSystemSign());	
			userExperienceCount.setAppId(activityInputDTO.getAppSign());
			userExperienceCount.setUserId(activityInputDTO.getUserId());	
			userExperienceCount.setVinNo(activityInputDTO.getVinNo());
				
			UserExperienceCount userExperienceCount2=userExperienceCountService.findOneByObject(inputDto);
			if(userExperienceCount2 == null) {
				userExperienceCount.setId(RandomUtils.RandomUUID());
				userExperienceCount.setNumber(Integer.valueOf(experience.getExp()));
				userExperienceCount.setCreateDate(new Date());
				userExperienceCount.setModifyTime(new Date());
				userExperienceCountService.insert(userExperienceCount);						
			}else {			
				int num=userExperienceCount2.getNumber()+Integer.valueOf(experience.getExp());						
				userExperienceCount2.setNumber(num);
				userExperienceCount2.setModifyTime(new Date());
				userExperienceCountService.experienceupdate(userExperienceCount2);
			}			
		}
							
		List<ActivityCard>  activityCardList=activityCardService.findByActivityId(activityId);		
		for (ActivityCard activityCard : activityCardList) {
			
			String cardId=activityCard.getCardId();			
			Card card=cardService.findById(cardId);				
			Integer vinMax=card.getNumVinMax();		
			if(StringUtils.isNotBlank(cardId)) {
				if(vinMax !=null && vinMax > 0) {
					for(int i=0;i<vinMax;i++) {
						UserCard userCard=new UserCard();
						userCard.setId(RandomUtils.RandomUUID());
						userCard.setAccessSystemId(activityInputDTO.getSystemSign());
						userCard.setAppId(activityInputDTO.getAppSign());
						userCard.setUserId(activityInputDTO.getUserId());
						userCard.setVinNo(activityInputDTO.getVinNo());
						userCard.setActivitySystemId(activitySystemId);
						userCard.setActivityId(activityId);
						userCard.setCardId(cardId);
						userCard.setCardNo(RandomUtils.getCardNo());
						userCard.setState("0");
						userCard.setCreateTime(new Date());
						userCard.setModifyTime(new Date());	
						userCardService.insert(userCard);
					}				
				}			
			}
			
			UserCardCountInputDTO userCardCountInputDTO=new UserCardCountInputDTO();
			userCardCountInputDTO.setAccessSystemId(activityInputDTO.getSystemSign());
			userCardCountInputDTO.setAppId(activityInputDTO.getAppSign());
			userCardCountInputDTO.setUserId(activityInputDTO.getUserId());
			userCardCountInputDTO.setVinNo(activityInputDTO.getVinNo());
			userCardCountInputDTO.setCardId(cardId);
			
			UserCardCount userCardCount=userCardCountService.findOneByObject(userCardCountInputDTO);
			if(userCardCount !=null) {
				int number=userCardCount.getNumber()+vinMax;
				userCardCount.setNumber(number);
				userCardCount.setModifyTime(new Date());
				userCardCountService.update(userCardCount);
			}else {
				UserCardCount userCardCount2=new UserCardCount();
				userCardCount2.setId(RandomUtils.RandomUUID());
				userCardCount2.setAccessSystemId(activityInputDTO.getSystemSign());
				userCardCount2.setAppId(activityInputDTO.getAppSign());
				userCardCount2.setUserId(activityInputDTO.getUserId());
				userCardCount2.setVinNo(activityInputDTO.getVinNo());
				userCardCount2.setCardId(cardId);
				userCardCount2.setNumber(vinMax);
				userCardCount2.setCreateTime(new Date());
				userCardCount2.setModifyTime(new Date());
				userCardCountService.insert(userCardCount2);
				
			}		
			
		}
		
		List<ActivityIntegral> activityIntegralList=activityIntegralService.findByActivityId(activityId);
		
		for (ActivityIntegral activityIntegral : activityIntegralList) {
			String integralId=activityIntegral.getIntegralId();		
			Integral integral=integralService.findById(integralId);
			String integralCategoryId=integral.getIntegralCategoryId();	
			
			UserIntegral userIntegral=new UserIntegral();
			userIntegral.setId(RandomUtils.RandomUUID());
			userIntegral.setUserId(activityInputDTO.getUserId());
			userIntegral.setAccessSystemId(activityInputDTO.getSystemSign());
			userIntegral.setVinNo(activityInputDTO.getVinNo());
			userIntegral.setAppId(activityInputDTO.getAppSign());
			userIntegral.setActivitySystemId(activitySystemId);
			userIntegral.setActivityId(activityId);
			userIntegral.setIntegralId(integralId);
			userIntegral.setConsumeNum(integral.getIntegral());
			userIntegral.setType("00");
			userIntegral.setCreateDate(new Date());
			userIntegral.setModifyTime(new Date());						
			userIntegralService.insert(userIntegral);
			
			
			IntegralCategory integralCategory=integralCategoryService.findById(integralCategoryId);
			
			UserIntegralCountInputDTO userIntegralCountInputDTO=new UserIntegralCountInputDTO();
			userIntegralCountInputDTO.setAccessSystemId(activityInputDTO.getSystemSign());
			userIntegralCountInputDTO.setAppId(activityInputDTO.getAppSign());
			userIntegralCountInputDTO.setUserId(activityInputDTO.getUserId());
			userIntegralCountInputDTO.setVinNo(activityInputDTO.getVinNo());
			userIntegralCountInputDTO.setType(integralCategory.getType());
			userIntegralCountInputDTO.setIntegralId(integralId);
			
			UserIntegralCount userIntegralCount=new UserIntegralCount();
			userIntegralCount.setAccessSystemId(activityInputDTO.getSystemSign());	
			userIntegralCount.setAppId(activityInputDTO.getAppSign());
			userIntegralCount.setUserId(activityInputDTO.getUserId());	
			userIntegralCount.setVinNo(activityInputDTO.getVinNo());
			userIntegralCount.setIntegralId(integralId);
			userIntegralCount.setType(integralCategory.getType());
			
			UserIntegralCount userIntegralCount2=userIntegralCountService.findOneByObject(userIntegralCountInputDTO);
			
			if(userIntegralCount2 == null) {
				userIntegralCount.setId(RandomUtils.RandomUUID());
				userIntegralCount.setIntegralId(integral.getId());
				userIntegralCount.setNumber(integral.getIntegral());
				userIntegralCount.setCreateDate(new Date());
				userIntegralCount.setModifyTime(new Date());
				userIntegralCountService.insert(userIntegralCount);						
			}else {
				String type=integralCategory.getType();			
				int num=0;
				if(type.equals("00")) {
					num=userIntegralCount2.getNumber()+Integer.valueOf(integral.getIntegral());
				}else if (type.equals("01")) {
					num=userIntegralCount2.getNumber()-Integer.valueOf(integral.getIntegral());
				}			
				userIntegralCount.setNumber(num);
				userIntegralCount.setModifyTime(new Date());
				userIntegralCountService.integralUpdate(userIntegralCount);			
			}						
		}
		return "true";
	}
}
