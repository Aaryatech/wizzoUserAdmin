package com.ats.wizzo;

import java.io.IOException;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ats.wizzo.model.User;
import com.ats.wizzo.model.UserPwd;
import com.fasterxml.jackson.databind.ObjectMapper;

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

import com.ats.wizzo.model.GenerateOtp;
import com.ats.wizzo.model.LoginResponse;
import com.ats.wizzo.model.LoginResponseUser;
import com.ats.wizzo.model.Room;
import com.ats.wizzo.model.TotalRoom;
import com.ats.wizzo.common.Constants;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

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

					List<TotalRoom> roomList = new ArrayList<TotalRoom>(Arrays.asList(room));
					System.out.println("roomList" + roomList);
					mav.addObject("roomList", roomList);

				} else {

					mav = new ModelAndView("login");
					System.out.println("Invalid login credentials");

				}

			}
		} catch (Exception e) {
			System.out.println("HomeController Login API Excep:  " + e.getMessage());
			e.printStackTrace();

		}

		return mav;

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
		ModelAndView mav = new ModelAndView("home");

		res.setContentType("text/html");
		try {

			RestTemplate rest = new RestTemplate();
			UserPwd userPwd = new UserPwd();
			UserPwd userPwdRes = new UserPwd();

			userPwd = rest.postForObject(Constants.url + "/getDataByUserId", userId, UserPwd.class);
			if (userPwd == null) {

				userPwdRes.setUserPassword(userPassword);
				userPwdRes.setUserId(Integer.parseInt(userId));

				System.out.println("userPwdRes" + userPwdRes);
				userPwd = rest.postForObject(Constants.url + "/saveUserPwd", userPwdRes, UserPwd.class);
			} else {
				userPwdRes.setUserPwdId(userPwd.getUserPwdId());
			}

			System.out.println("userPwd" + userPwd.toString());

		} catch (Exception e) {
			System.out.println("HomeController Login API Excep:  " + e.getMessage());

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
