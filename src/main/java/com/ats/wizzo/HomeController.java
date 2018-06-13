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

import com.ats.wizzo.model.User;
import com.ats.wizzo.model.UserPwd;

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

import com.ats.wizzo.model.ErrorMessage;
import com.ats.wizzo.model.Room;
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
				/*MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
				map.add("userMob", name);
				map.add("password", password);
				LoginResponseExh loginResponse = rest.postForObject(Constants.url + "/loginExhibitor", map,
						LoginResponseExh.class);
				System.out.println("loginResponse" + loginResponse);*/

				if (name.equals("Tester") && password.equals("1234")) {
					mav = new ModelAndView("home");
					MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
					map.add("userId", 1); 
					Room[] room = rest.postForObject(Constants.url + "/getRoomListByUsertId", map,
							Room[].class);
					
					List<Room> roomList = new ArrayList<Room>(Arrays.asList(room));
					System.out.println("roomList" +  roomList);
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
	public String generateOtp(HttpServletRequest request, HttpServletResponse res) throws IOException {
		Random rnd = new Random();
		int n = 100000 + rnd.nextInt(900000);
		String userMobile = request.getParameter("userMobile");
		try {

		} catch (Exception e) {
			System.out.println("HomeController Login API Excep:  " + e.getMessage());
		}
		String otp = String.valueOf(n);
		return otp;

	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request, HttpServletResponse res) throws IOException {

		String userMobile = request.getParameter("userMobile");

		String otp = request.getParameter("otp");
		String userPassword = request.getParameter("userPassword");
		ModelAndView mav = new ModelAndView("home");

		res.setContentType("text/html");
		try {
			System.out.println("Login Process " + userMobile);
			System.out.println("otp " + otp);

			RestTemplate rest = new RestTemplate();
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("userMobile", userMobile);
			map.add("otp", otp);
			ErrorMessage errorMessage = rest.postForObject(Constants.url + "/login", map, ErrorMessage.class);
			System.out.println("loginResponse" + errorMessage);

			UserPwd userPwd = rest.postForObject(Constants.url + "/saveUserPwd", userPassword, UserPwd.class);
			System.out.println("userPwd" + userPwd.toString());

		} catch (Exception e) {
			System.out.println("HomeController Login API Excep:  " + e.getMessage());
			e.getStackTrace();
		}
		return mav;

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

}
