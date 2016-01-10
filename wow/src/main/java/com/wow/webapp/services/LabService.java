package com.wow.webapp.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.wow.webapp.controllers.api.LabController;
import com.wow.webapp.dao.LabDAO;
import com.wow.webapp.dao.UserDAO;
import com.wow.webapp.domain.model.ApiBookingReturnModel;
import com.wow.webapp.domain.model.ApiReturnLab;
import com.wow.webapp.domain.model.ApiReturnModel;
import com.wow.webapp.domain.model.CreateLabBookingModel;
import com.wow.webapp.domain.model.CreateLabSubTypeModel;
import com.wow.webapp.domain.model.UserModel;
import com.wow.webapp.domain.pojo.LabBookingModel;
import com.wow.webapp.domain.pojo.LabSlotModel;
import com.wow.webapp.domain.pojo.LabSlotResponseModel;
import com.wow.webapp.domain.pojo.LabSubTypeModel;
import com.wow.webapp.domain.pojo.LabTypeModel;
import com.wow.webapp.entitymodel.Authority;
import com.wow.webapp.entitymodel.LabBooking;
import com.wow.webapp.entitymodel.LabSlots;
import com.wow.webapp.entitymodel.LabSubType;
import com.wow.webapp.entitymodel.LabType;
import com.wow.webapp.entitymodel.Profile;
import com.wow.webapp.entitymodel.User;
import com.wow.webapp.util.Constants;
import com.wow.webapp.util.Responses;
import com.wow.webapp.util.SMS;
import com.wow.webapp.util.Utils;

@Service
@Component
public class LabService {

	@Autowired
	private LabDAO labDao;
	@Autowired
	private UserDAO userDao;
	
	private static final Logger logger = LoggerFactory.getLogger(LabController.class);
	
	public List<String> addLabType(String name){
		List<String> errors = new ArrayList<String>();
		LabType lab = new LabType();
		try{
			logger.info("Name id :"+name );
			lab.setName(name);
			lab.setInserted_on(new Date());
			labDao.save(lab);
		}
		catch(Exception ex){
			logger.error("Exception raised " + ex.toString());
			errors.add(ex.toString());
		}
		return errors;
	}
	
	public List<String> addLabSubType(CreateLabSubTypeModel model){
		List<String> errors = new ArrayList<String>();
		
		try{
			logger.info("in add sub types::");
			LabType labType=labDao.findLabType(Integer.parseInt(model.getTypeId()));
			if(labType != null)
			{
				LabSubType labSubType=new LabSubType();
				labSubType.setLabType(labType);
				labSubType.setName(model.getName());
				labSubType.setDescription(model.getDescription());
				labSubType.setHomePickup(Boolean.parseBoolean(model.getHome_pickup()));
				labSubType.setMaxHomePickupBookings(Integer.parseInt(model.getMax_online_bookings()));
				labSubType.setMaxOnlineBookings(Integer.parseInt(model.getMax_online_bookings()));
				labSubType.setDuration(Integer.parseInt(model.getDuration()));
				
				labDao.save(labSubType);
				
				logger.info("lab sub type insertion success");
				
				if(model.getStartTime()!=null && !model.getStartTime().isEmpty() && model.getEndTime()!=null && !model.getEndTime().isEmpty())
				{
					logger.info("enter into insert slots for lab");
					List<String> error=addLabSlots(String.valueOf(labSubType.getId()),model.getStartTime(),model.getEndTime());
					logger.info("lab sots insertion success:::"+error.toString());
					
				}
				
			}
			else
				errors.add("requested laType Id is not found");
			
			
		}
		catch(Exception ex){
			logger.error("Exception raised " + ex.toString());
			errors.add(ex.toString());
		}
		return errors;
	}

	public List<Object> getLabTypes(){
		
		List<Object> list = new ArrayList<Object>();
		try{
			
			logger.info("in getLab types");
			List<LabType> labType=labDao.getLabType();
			if(labType.size() > 0 && labType != null)
			{
				for(LabType type:labType)
				{
					LabTypeModel labTypeModel=new LabTypeModel();
					labTypeModel.setId(type.getId());
					labTypeModel.setName(type.getName());
					
					list.add(labTypeModel);
				}
			}
		}
		catch(Exception ex){
			logger.error("Exception raised " + ex.toString());
			
		}
		return list;
	}
	
	public List<Object> getLabSubTypes(Integer id){
		
		List<Object> list = new ArrayList<Object>();
		try{
			
			logger.info("in getLab types");

			LabType labType=new LabType(id);
			List<LabSubType> labSubType = null;
			if(id == null) labSubType = labDao.getLabSubType();
			else labSubType=labDao.getLabSubType(labType);
			
			if(labSubType.size() > 0 && labSubType != null)
			{
				for(LabSubType type:labSubType)
				{
					LabSubTypeModel model=new LabSubTypeModel();
					model.setId(type.getId());
					model.setName(type.getName());
					model.setDescription(type.getDescription());
					model.setDuration(type.getDuration());
					model.setHome_pickup(type.isHomePickup());
					model.setOnline_booking(type.isOnlineBooking());
					model.setMax_homepickup_bookings(type.getMaxHomePickupBookings());
					model.setMax_online_bookings(type.getMaxOnlineBookings());
					
					list.add(model);
				}
			}
		}
		catch(Exception ex){
			logger.error("Exception raised " + ex.toString());
			
		}
		return list;
	}
	
	public List<String> addLabSlots(String subTypeId,String starttime,String endtime)
	{
		List<String> errors = new ArrayList<String>();
		try
		{
			Utils utils=new Utils();
			Date startTime=utils.convertStringToDate(starttime);
			Date endTime=utils.convertStringToDate(endtime);
			int comp = startTime.compareTo(endTime);
			logger.info("dates comparsion checking is :::start::"+startTime+":::end:::"+endTime+"::results are:::"+comp);
			if(comp == 0 || comp == -1)
			{
				LabSubType labsubType=labDao.gerLabsubType(Integer.parseInt(subTypeId));
				if(labsubType != null)
				{
					int duration=labsubType.getDuration();
					List<String> existedList=labDao.findSlotsByStartAndEndTimes(startTime, endTime,labsubType);
					logger.info("existedList are ::: "+existedList.toString());
					List<String> list=utils.getRangeTimes(startTime,endTime,duration);
					List<String> insertedList=null;
					if(existedList!=null && existedList.size()>0)
					{
						insertedList = new ArrayList<String>(list);
						insertedList.removeAll(existedList);
					}
					else
						insertedList=list;
					
					logger.info("inserted list are:::"+insertedList.toString());
					
					if(insertedList!=null && insertedList.size()>0)
						for(String str:insertedList)
						{
							LabSlots s = new LabSlots();
							Date time=utils.convertStringToDate(str);
							s.setTime(time);
							s.setSubType(labsubType);
							s.setInserted_on(new Date());
							s.setEnabled(true);
							labDao.save(s);
							
						}
					
					
				}
				else
					errors.add("labsub type not found on based on id ::"+subTypeId);
			}
			else
				errors.add("end date must be greater then start date");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("exception occurs while adding lab slots:::"+e.toString());
			errors.add(e.getMessage());
		}
		return errors;
	}

	

	public ApiReturnModel labBooking(CreateLabBookingModel model) {
		
		logger.info("in lab booking");
		ApiReturnModel returnModel = null;
		List<String> errors = new ArrayList<String>();
		UserDetails ud=null;
		try
		{
			LabSlots slots = labDao.findSlot(Integer.parseInt(model.getSlotId()));
			if(slots != null)
			{
				logger.info("slot avilable");
				LabSubType subType = slots.getSubType();
				
				String receive_mode = "";
				Integer size_limit_num = 0;
				Integer max_bookings_num = 0;
				String source = model.getSource();
				
				
				if(model.getReceive_mode() != null && model.getReceive_mode().equalsIgnoreCase(Constants.RECEIVE_MODE))
				{
					logger.info("Home pick up booking");
					size_limit_num = subType.getMaxHomePickupBookings();
					receive_mode = Constants.LAB_HOMEPICKUP;
					if(model.getAddress().isEmpty() && model.getAddress() == null)
					{
						errors.add("homepickup user must be give address");
						logger.info("homepickup user must be give address");
						return new ApiReturnModel(Responses.FAILURE_CODE,Responses.ERROR_STATUS,"lab slot booking failed",errors);
					}
					
				}
				else
				{
					logger.info("walkin booking");
					size_limit_num = subType.getMaxOnlineBookings();
					receive_mode = Constants.LAB_WALKIN;
				}
				
				
				if(model.getSource() == null)
					source = Constants.ONLINE_SOURCE;
				
				
				logger.info("source :::"+source);
				logger.info("receive Mode ::"+receive_mode);
				logger.info("Max bookings allowed :::"+size_limit_num);
				
				List<LabBooking> booking = labDao.findBookingsOnslot(slots,receive_mode);
				
				if(!booking.isEmpty() && booking != null)
					max_bookings_num = booking.size();
				
				logger.info("already booking same slot numbers:::"+max_bookings_num);
				
				if(max_bookings_num < size_limit_num)
				{
					logger.info("condition is true for checking max bookings for one slot");
					ud=Utils.getUserSession();
					String role=null;
					String userName=null;
					
					if (ud == null) {
						logger.info("Anonymous user");
						if(model.getMobile() ==null || model.getName() ==null || model.getAddress() ==null){
							logger.info("Anonymous user requested parameters are not coming to user");
							errors.add("Anonymous user requested parameters are not coming to user");
							return new ApiReturnModel(Responses.FAILURE_CODE,Responses.ERROR_STATUS,"lab slot booking failed",errors);
						}
						User user=userDao.findByUserName(model.getMobile());
						if(user == null){
							logger.info("new user ,mobile no:::"+model.getMobile());
							addUser(model);
							userName = model.getMobile();
						}
						else
						{
							userName=user.getUsername();
							logger.info("existed user ::: username::"+userName);
						}
							
						
					}
					else
					{
						logger.info("registred user");
						for(GrantedAuthority auth :ud.getAuthorities())
							role=auth.getAuthority();
						if(role!=null && Constants.ROLE_LAB_ACCESS.contains(role))
						{
							if(model.getMobile() ==null || model.getName() ==null || model.getAddress() ==null){
								logger.info("Anonymous user requested parameters are not coming to user");
								errors.add("Anonymous user requested parameters are not coming to user");
								return new ApiReturnModel(Responses.FAILURE_CODE,Responses.ERROR_STATUS,"lab slot booking failed",errors);
							}
							logger.info("ROLE RECP is booking a slot for new user");
							User user=userDao.findByUserName(model.getMobile());
							if(user == null){
								addUser(model);
							}
							userName = model.getMobile();
							
						}else
						{
							logger.info("ROLE USER booking a slot for own");
							userName=ud.getUsername();
						}
							
					}
					
					User user=new User(userName);
					LabBooking labBooking = new LabBooking();
					labBooking.setLabSlot(slots);
					labBooking.setUser(user);
					labBooking.setReceive_mode(receive_mode);
					labBooking.setSource(source);
					labBooking.setInserted_on(new Date());
					labBooking.setAddress(model.getAddress());
					
					labDao.save(labBooking);
					
					LabBookingModel labBookingModel = new LabBookingModel();
					labBookingModel.setReceiveMode(receive_mode);
					labBookingModel.setSlotTime(new Utils().convertDateToUTCFormat(slots.getTime()));
					labBookingModel.setTestName(slots.getSubType().getName());
					
					List<Object> list = new ArrayList<Object>();
					list.add(labBookingModel);
					
					logger.info("slot booking success response is :::"+list.toString());
					
					returnModel = new ApiReturnLab(Responses.SUCCESS_CODE, Responses.SUCCESS_STATUS, "slot booking success", list);
					return returnModel;
					
				}
				else
					errors.add("booking exceed limit, try to book another slot");
					
				
			}
			else
				errors.add("slot is not found");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("exception occurs while book a slot for lab:::"+e.toString());
			errors.add(e.getMessage());
		}
		
		logger.info("error occurs while book a lab slot :::errors are::"+errors.toString());
		
		returnModel = new ApiReturnModel(Responses.FAILURE_CODE,Responses.ERROR_STATUS,"lab slot booking failed",errors);		
		return returnModel;
	}
	
	private void addUser(CreateLabBookingModel model)
	{
		logger.info("enter into addUser while slotBooking");
		try
		{
		User user=new User();
		user.setUsername(model.getMobile());
		user.setPassword(new Utils().getEncryptedPassword(model.getMobile()));
		user.setEnabled(true);
		Set<Authority> authorities = new HashSet<Authority>();
		authorities.add(new Authority(user, Constants.ROLE_USER));
		user.setUserRole(authorities);
		
		Profile userProfile =  new Profile(user, model.getName());
		
		user.setUserProfile(userProfile);
		
		userDao.save(user);
		logger.info("add user success");
		SMS sms = new SMS();
		sms.sendSMS(Constants.SMS_BOOKING_MSG, model.getMobile());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public ApiReturnModel getSlots(String subTypeId, String date) {
		
		logger.info("in getslots subtypeId:::"+subTypeId);
		List<String> errors = new ArrayList<String>();
		try
		{
			LabSubType labSubType = new LabSubType(Integer.parseInt(subTypeId));
			List<LabSlots> slots = labDao.findLabSlotsOnDate(labSubType,date);
			if(slots!=null && slots.size()>0)
			{
				LabSlotResponseModel labslotModel = new LabSlotResponseModel();
				logger.info("lab slots availble for request date"+date);
				List<LabSlotModel> slotsList = new ArrayList<LabSlotModel>();
				//List<LabSlotModel> bookedSlotsList = new ArrayList<LabSlotModel>();
				for(LabSlots slot:slots)
				{	
					LabSlotModel slotObj = new LabSlotModel();
					String time = new Utils().convertDateToUTCFormat(slot.getTime());
						slotObj.setId(slot.getId());
						slotObj.setSlotTime(time);
						slotsList.add(slotObj);
					//List<LabBooking> booking = labDao.findBookingsOnslot(slot);
				}
				labslotModel.setAvailableSlots(slotsList);
				List<Object> res = new ArrayList<Object>();
				res.add(labslotModel);
				
				return new ApiReturnLab(Responses.SUCCESS_CODE, Responses.SUCCESS_STATUS, Responses.SUCCESS_MSG, res);
			}
			else
				errors.add("slots not availble");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("exception occurs while getting get slots :::"+e.toString());
			errors.add(e.getMessage());
		}
		
		return new ApiReturnModel(Responses.FAILURE_CODE,Responses.ERROR_STATUS,"lab slot booking failed",errors);
		
	}
	
	public ApiReturnModel getBookings(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		UserDetails ud=null;
		try {
			ud=Utils.getUserSession();
			if (ud == null) {
				return new ApiBookingReturnModel(
						Responses.USER_NOTLOGGEDIN, Responses.SUCCESS_STATUS,
						"user not logged in,please login");
			} else {
				List<LabBooking> bookings = new ArrayList<LabBooking>();
				logger.debug("Authorities : "+ ud.getAuthorities());
				String userName = ud.getUsername();
				String role=null;
				for(GrantedAuthority auth :ud.getAuthorities())
					role=auth.getAuthority();
				if(role!=null && Constants.ROLE_LAB_ACCESS.contains(role))
				{
					String requestedDate=request.getParameter("date");
					if(requestedDate != null && !requestedDate.isEmpty()){
						bookings = labDao.findLabBookings(requestedDate);
					}
					else{
						bookings = labDao.findLabBookings();
					}
				}
				else{
					logger.info("In user booking");
					User user=userDao.findByUserName(userName);
					logger.info("User is :" + user);
					bookings = labDao.findLabBookingsByUser(user);
				}
				List<LabBookingModel> results = new ArrayList<LabBookingModel>();
				for(LabBooking book: bookings){
					LabBookingModel result = new LabBookingModel();
					result.setId(book.getId());
					result.setReceiveMode(book.getReceive_mode());
					LabSlots bookedSlot = book.getLabSlot();
					result.setSlotTime(new Utils().convertDateToUTCFormat(bookedSlot.getTime()));
					result.setTestName(book.getLabSlot().getSubType().getName());
					UserModel userDetails = null;
					logger.info("Booking address is : " + book.getAddress());
					if(book.getAddress() != null)
						userDetails = new UserModel(book.getUser().getUserProfile().getName(),book.getUser().getUsername(),book.getAddress());
					else
						userDetails = new UserModel(book.getUser().getUserProfile().getName(),book.getUser().getUsername());
					result.setUser(userDetails);
					results.add(result);
				}
				List<Object> res = new ArrayList<Object>();
				res.add(results);
				return new ApiReturnLab(Responses.SUCCESS_CODE, Responses.SUCCESS_STATUS, Responses.SUCCESS_MSG, res);
			}
			}
			catch(Exception e){
				e.printStackTrace();
				logger.error("exception occurs while getting get slots :::"+e.toString());
				errors.add(e.getMessage());
			}
		
		return new ApiReturnModel(Responses.FAILURE_CODE,Responses.ERROR_STATUS,"lab slot booking failed",errors);
	}
	
}
