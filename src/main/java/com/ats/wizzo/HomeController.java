package com.ats.wizzo;

import java.io.IOException;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ats.wizzo.model.User;
import com.ats.wizzo.model.UserPassword;
import com.ats.wizzo.model.UserPwd;
import com.sun.org.apache.bcel.internal.generic.GOTO;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.wizzo.model.CurrentStatus;
import com.ats.wizzo.model.DevIdWithType;
import com.ats.wizzo.model.Device;
import com.ats.wizzo.model.ErrorMessage;
import com.ats.wizzo.model.GenerateOtp;
import com.ats.wizzo.model.LoginResponse;
import com.ats.wizzo.model.LoginResponseUser;
import com.ats.wizzo.model.ScanDevicesResponse;
import com.ats.wizzo.model.ScanList;
import com.ats.wizzo.model.Scheduler;
import com.ats.wizzo.model.TotalRoom;
import com.ats.wizzo.common.Constants;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	// MQTT
	public static String publishTopic = "//operations//dlZ3Ync9";

	public static String onOperation = "piMjVtYV";

	public static String offOperation = "JhTVo1V1";
	
	public static String fanOperation = "lfcGkYEw";
	
	public static String dimmer1Operation = "lfcGkYD1";
	
	public static String dimmer2Operation = "lfcGkYD2";
	
	private MqttClient client = null;

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	List<TotalRoom> roomList = new ArrayList<TotalRoom>();
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */

	RestTemplate rest = new RestTemplate();

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "login";
	}

	@RequestMapping("/loginProcess")
	public ModelAndView helloWorld(HttpServletRequest request, HttpServletResponse res) throws IOException {

		String name = request.getParameter("username");
		String password = request.getParameter("password");

		ModelAndView mav = new ModelAndView("login");

		res.setContentType("text/html");
		try {
			System.out.println("Login Process " + name);
			System.out.println("password " + password);

			if (name.equalsIgnoreCase("") || password.equalsIgnoreCase("") || name == null || password == null) {

				mav = new ModelAndView("login");
			} else {

				RestTemplate rest = new RestTemplate();

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
				map.add("userMobile", name);
				map.add("userPassword", password);
				LoginResponseUser loginResponse = rest.postForObject(Constants.url + "/userLogin", map,
						LoginResponseUser.class);
				System.out.println("loginResponse" + loginResponse);

				if (loginResponse.isError() == false) {
					mav = new ModelAndView("home");

					HttpSession session = request.getSession();
					session.setAttribute("user", loginResponse.getUserPassword());
					session.setAttribute("UserDetail", loginResponse);
					MultiValueMap<String, Object> map1 = new LinkedMultiValueMap<String, Object>();
					map1.add("userId", loginResponse.getUserPassword().getUserId());
					TotalRoom[] room = rest.postForObject(Constants.url + "/getRoomListByUsertId", map1,
							TotalRoom[].class);

					roomList = new ArrayList<TotalRoom>(Arrays.asList(room));
					System.out.println("roomList" + roomList);

					// getScanDevices

					map = new LinkedMultiValueMap<String, Object>();
					map.add("userId", loginResponse.getUserPassword().getUserId());
					Device[] scanDevice = rest.postForObject(Constants.url + "/macAddByUserId", map, Device[].class);
					List<Device> macAddList = new ArrayList<Device>(Arrays.asList(scanDevice));

					List<String> macList = new ArrayList<String>();

					for (Device device : macAddList) {

						macList.add(device.getDevMac());
					}

					System.out.println("macList" + macList);
					try {
						MqttStatus mqttStatus = new MqttStatus(
								loginResponse.getUserPassword().getAuthKey());
						try {
							mqttStatus.setupMQTT();
						}catch (Exception e) {
						e.printStackTrace();	
						}
						
						mqttStatus.subscribeToTopics(macList);
						
						 List<CurrentStatus>currentStatusList=mqttStatus.getCurrentStatus(macList);
						 mqttStatus.disconnect();
							System.out.println("after thread sleep "+currentStatusList.toString());
							mav.addObject("currentStatusList", currentStatusList);

						
					} catch (Exception e) {

						e.printStackTrace();
					}
					
					

					mav.addObject("roomList", roomList);

				} else {

					mav = new ModelAndView("login");
					System.out.println("Invalid login credentials");
					mav.addObject("loginResponseMessage", "Invalid login credentials");

				}

			}
		} catch (Exception e) {
			System.out.println("HomeController Login API Excep:  " + e.getMessage());
			e.printStackTrace();

		}

		return mav;

	}

	@RequestMapping(value = "/singleSwitchOnOff", method = RequestMethod.GET)
	@ResponseBody
	public List<Integer> singleSwitchOnOff(HttpServletRequest request, HttpServletResponse response) {

		List<Integer> roomIdList = new ArrayList<Integer>();
		try {

			HttpSession session = request.getSession();
			UserPassword user = (UserPassword) session.getAttribute("user");

			if (client == null) {

				client = new MqttClient("tcp://132.148.16.132:1883", "pahomqttpublish1");

			}

			int roomId = Integer.parseInt(request.getParameter("roomId"));
			String operation = request.getParameter("operation");
			int devId = Integer.parseInt(request.getParameter("devId"));
			int devIdValue = Integer.parseInt(request.getParameter("devIdValue"));

			for (int i = 0; i < roomList.size(); i++) {
				if (roomList.get(i).getRoomId() == roomId) {
					for (int j = 0; j < roomList.get(i).getDeviceList().size(); j++) {
						if (roomList.get(i).getDeviceList().get(j).getDevId() == devId) {

							Device device = roomList.get(i).getDeviceList().get(j);

							MqttMessage message = new MqttMessage();
							String msg = "";
							
							if(device.getDevType()==678) {
								
								msg = user.getAuthKey() + fanOperation + "#" + devIdValue;
								
							}else if(device.getDevType()==12) {
								
								msg = user.getAuthKey() + dimmer1Operation + "#" + devIdValue;
								
							}else if(device.getDevType()==13) {
								
								msg = user.getAuthKey() + dimmer2Operation + "#" + devIdValue;
								
							}else {
								
								if (operation.equalsIgnoreCase("on")) {
									msg = user.getAuthKey() + onOperation + "#" + device.getDevType();
								} else {
									msg = user.getAuthKey() + offOperation + "#" + device.getDevType();

								}
							}
							
							message.setPayload(msg.getBytes());

							if (!client.isConnected()) {

								client.connect();

							}
							client.publish(device.getDevMac() + publishTopic, message);

							break;
						}
					}
					break;
				}
			}

			System.out.println(roomIdList);

			client.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return roomIdList;
	}

	@RequestMapping(value = "/allSwitchOnOff", method = RequestMethod.GET)
	@ResponseBody
	public List<DevIdWithType> allSwitchOnOff(HttpServletRequest request, HttpServletResponse response) {

		List<DevIdWithType> roomIdList = new ArrayList<DevIdWithType>();
		try {

			HttpSession session = request.getSession();
			UserPassword user = (UserPassword) session.getAttribute("user");

			if (client == null) {

				client = new MqttClient("tcp://132.148.16.132:1883", "pahomqttpublish1");

			}

			int roomId = Integer.parseInt(request.getParameter("roomId"));
			String operation = request.getParameter("operation");

			for (int i = 0; i < roomList.size(); i++) {
				if (roomList.get(i).getRoomId() == roomId) {
					for (int j = 0; j < roomList.get(i).getDeviceList().size(); j++) {
						
						DevIdWithType devIdWithType = new DevIdWithType();
						devIdWithType.setDevId(roomList.get(i).getDeviceList().get(j).getDevId());
						devIdWithType.setType(roomList.get(i).getDeviceList().get(j).getDevType() );
						roomIdList.add(devIdWithType);

						Device device = roomList.get(i).getDeviceList().get(j);

						MqttMessage message = new MqttMessage();
						String msg = "";
						if (operation.equalsIgnoreCase("on")) {
							
							if(device.getDevType()==678) {
								
								msg = user.getAuthKey() + fanOperation + "#" + 1;
								
							}else if (device.getDevType()==12){
								
								msg = user.getAuthKey() + dimmer1Operation + "#" + 1;
								
							}else if (device.getDevType()==13){
								
								msg = user.getAuthKey() + dimmer2Operation + "#" + 1;
								
							}else {
								
								msg = user.getAuthKey() + onOperation + "#" + device.getDevType();
							}
							
						} else {
							
							if(device.getDevType()==678) {
								
								msg = user.getAuthKey() + fanOperation + "#" + 0;
								
							}else if (device.getDevType()==12){
								
								msg = user.getAuthKey() + dimmer1Operation + "#" + 0;
								
							}else if (device.getDevType()==13){
								
								msg = user.getAuthKey() + dimmer2Operation + "#" + 0;
								
							}else {
								
								msg = user.getAuthKey() + offOperation + "#" + device.getDevType();
							}
 
						}
						message.setPayload(msg.getBytes());

						if (!client.isConnected()) {

							client.connect();

						}
						client.publish(device.getDevMac() + publishTopic, message);

					}
					break;
				}
			}

			System.out.println(roomIdList);

			client.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return roomIdList;
	}

	@RequestMapping(value = "/selectAllSchedulerCheckBox", method = RequestMethod.GET)
	@ResponseBody
	public List<Integer> selectAllSchedulerCheckBox(HttpServletRequest request, HttpServletResponse response) {

		List<Integer> roomIdList = new ArrayList<Integer>();
		try {

			int roomId = Integer.parseInt(request.getParameter("roomId"));

			for (int i = 0; i < roomList.size(); i++) {
				if (roomList.get(i).getRoomId() == roomId) {
					for (int j = 0; j < roomList.get(i).getDeviceList().size(); j++) {
						roomIdList.add(roomList.get(i).getDeviceList().get(j).getDevId());

					}
				}
			}

			System.out.println(roomIdList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return roomIdList;
	}

	@RequestMapping(value = "/setScheduler", method = RequestMethod.GET)
	public @ResponseBody ErrorMessage setScheduler(HttpServletRequest request, HttpServletResponse response) {

		ErrorMessage errorMessage = new ErrorMessage();

		System.out.println("in set Scheduler ");
		try {

			String scheduleTime = request.getParameter("scheduleTime");
			int daily = Integer.parseInt(request.getParameter("daily"));
			int roomId = Integer.parseInt(request.getParameter("roomId"));
			int devId = Integer.parseInt(request.getParameter("devId"));
			int onOFF = Integer.parseInt(request.getParameter("onOFF"));
			int sliderValue = Integer.parseInt(request.getParameter("sliderValue"));
			
			System.out.println(scheduleTime);
			System.out.println(daily);
			System.out.println(roomId);
			System.out.println(devId);
			List<Scheduler> insert = new ArrayList<Scheduler>();
			Scheduler scheduler = new Scheduler();
			for (int i = 0; i < roomList.size(); i++) {
				if (roomList.get(i).getRoomId() == roomId) {
					for (int j = 0; j < roomList.get(i).getDeviceList().size(); j++) {
						if (roomList.get(i).getDeviceList().get(j).getDevId() == devId) {

							scheduler.setDay(daily);
							scheduler.setDevMac(roomList.get(i).getDeviceList().get(j).getDevMac());
							scheduler.setDevType(roomList.get(i).getDeviceList().get(j).getDevType());
							if(roomList.get(i).getDeviceList().get(j).getDevType()==678 || 
									roomList.get(i).getDeviceList().get(j).getDevType()==12 || roomList.get(i).getDeviceList().get(j).getDevType()==13) {
								scheduler.setOperation(sliderValue);
							}else {
								scheduler.setOperation(onOFF);
							}
							
							scheduler.setSchStatus(1);
							scheduler.setTime(scheduleTime + ":00");
							scheduler.setUserId(roomList.get(i).getDeviceList().get(j).getUserId());
							insert.add(scheduler);
						}
					}
				}
			}

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("schedulerList", insert);

			System.out.println(scheduler);
			errorMessage = rest.postForObject(Constants.url + "/setNewScheduler", insert, ErrorMessage.class);
			System.out.println(errorMessage);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return errorMessage;
	}

	@RequestMapping(value = "/setSchedulerForAll", method = RequestMethod.GET)
	public @ResponseBody ErrorMessage setSchedulerForAll(HttpServletRequest request, HttpServletResponse response) {

		ErrorMessage errorMessage = new ErrorMessage();

		System.out.println("in set Scheduler ");
		try {

			String scheduleTime = request.getParameter("scheduleTime");
			String selected = request.getParameter("selected");
			String selectedDivId = request.getParameter("selectedDivId");
			int daily = Integer.parseInt(request.getParameter("daily"));
			int onOFF = Integer.parseInt(request.getParameter("onOFF"));

			selectedDivId = selectedDivId.substring(1, selectedDivId.length());
			selected = selected.substring(1, selected.length());

			String[] divId = selectedDivId.split(",");
			String[] roomId = selected.split(",");

			List<Scheduler> insert = new ArrayList<Scheduler>();

			for (int i = 0; i < roomList.size(); i++) {

				for (int k = 0; k < roomId.length; k++) {
					if (roomList.get(i).getRoomId() == Integer.parseInt(roomId[k])) {
						for (int j = 0; j < roomList.get(i).getDeviceList().size(); j++) {

							for (int m = 0; m < divId.length; m++) {
								if (roomList.get(i).getDeviceList().get(j).getDevId() == Integer.parseInt(divId[m])) {
									Scheduler scheduler = new Scheduler();
									scheduler.setDay(daily);
									scheduler.setDevMac(roomList.get(i).getDeviceList().get(j).getDevMac());
									scheduler.setDevType(roomList.get(i).getDeviceList().get(j).getDevType());
									scheduler.setOperation(onOFF);
									scheduler.setSchStatus(1);
									scheduler.setTime(scheduleTime + ":00");
									scheduler.setUserId(roomList.get(i).getDeviceList().get(j).getUserId());
									insert.add(scheduler);
									break;

								}
							}

						}

						break;

					}
				}

			}

			System.out.println(insert);
			errorMessage = rest.postForObject(Constants.url + "/setNewScheduler", insert, ErrorMessage.class);
			System.out.println(errorMessage);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return errorMessage;
	}

	@RequestMapping(value = "/createNewPassword", method = RequestMethod.GET)
	public ModelAndView createNewPassword(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("otpGeneration");
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/generateOtp", method = RequestMethod.GET)
	@ResponseBody
	public GenerateOtp generateOtp(HttpServletRequest request, HttpServletResponse res) throws IOException {
		GenerateOtp generateOtp = new GenerateOtp();
		Random rnd = new Random();
		int n = 100000 + rnd.nextInt(900000);

		String userMobile = request.getParameter("userMobile");
		System.out.println("userMobile:" + userMobile);
		System.out.println("otp:" + n);

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("userMob", userMobile);
			map.add("userOtp", n);

			LoginResponse loginReponse = rest.postForObject(Constants.url + "/login", map, LoginResponse.class);
			System.out.println("loginResponse" + loginReponse);

			/* String userId =loginReponse.getUser().getUserId(); */
			System.out.println("user id" + loginReponse.getUser().getUserId());
			generateOtp.setOtp(n);
			generateOtp.setUserId(loginReponse.getUser().getUserId());

		} catch (Exception e) {
			System.out.println("HomeController Login API Excep:  " + e.getMessage());
		}

		return generateOtp;

	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse res) throws IOException {

		String userPassword = request.getParameter("userPassword");
		String userId = request.getParameter("userId");

		res.setContentType("text/html");
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("userId", userId);

			RestTemplate rest = new RestTemplate();
			UserPwd userPwd = new UserPwd();
			UserPwd userPwdRes = new UserPwd();

			userPwd = rest.postForObject(Constants.url + "/getDataByUserId", map, UserPwd.class);
			if (userPwd.getUserPwdId() == 0) {

				userPwdRes.setUserPassword(userPassword);
				userPwdRes.setUserId(Integer.parseInt(userId));

				System.out.println("userPwdRes" + userPwdRes);
				userPwd = rest.postForObject(Constants.url + "/saveUserPwd", userPwdRes, UserPwd.class);

			} else {
				userPwdRes.setUserPwdId(userPwd.getUserPwdId());
				userPwdRes.setUserPassword(userPassword);
				userPwdRes.setUserId(Integer.parseInt(userId));
				userPwd = rest.postForObject(Constants.url + "/saveUserPwd", userPwdRes, UserPwd.class);
			}

			System.out.println("userPwd" + userPwd.toString());

		} catch (Exception e) {
			System.out.println("HomeController Login API Excep:  " + e.getMessage());
			e.printStackTrace();

		}
		return "login";

	}

	// AJAX Call
	@RequestMapping(value = "/UserMobileNo", method = RequestMethod.GET)
	public @ResponseBody User UserMobileNo(HttpServletRequest request, HttpServletResponse response) {

		User user = new User();

		try {

			String userMobile = request.getParameter("userMobile");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("userMobile", userMobile);
			user = rest.postForObject(Constants.url + "/checkMobileNo", map, User.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		System.out.println("User Logout");

		session.invalidate();
		return "redirect:/";
	}

	@RequestMapping(value = "/sessionTimeOut", method = RequestMethod.GET)
	public ModelAndView displayLoginAgain(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("login");

		logger.info("/sessionTimeOut request mapping.");

		model.addObject("loginResponseMessage", "Session timeout ! Please login again . . .");

		return model;

	}

}
