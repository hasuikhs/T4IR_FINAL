package com.chavis.biz.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.chavis.biz.service.BodyshopService;
import com.chavis.biz.service.ReservationService;
import com.chavis.biz.vo.BodyshopVO;
import com.chavis.biz.vo.ReservationVO;

@RestController
public class WebController {
	
	public static Logger log = LoggerFactory.getLogger(WebController.class);
	
	@Autowired
	BodyshopService bService;
	@Autowired
	ReservationService rService;

	@RequestMapping(value = "/home.do", method = RequestMethod.GET)
	public ModelAndView home(ModelAndView mv) {
		return mv;
	}

	@RequestMapping(value = "/dashboard.do", method = RequestMethod.POST)
	public ModelAndView loginProc(@RequestParam("bodyshop_id") String bodyshop_id,
			@RequestParam("bodyshop_pw") String bodyshop_pw, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("dashboard");
		BodyshopVO bodyshop = bService.bodyshopLogin(bodyshop_id, bodyshop_pw);
		if (bodyshop != null) {
			session.setAttribute("bodyshop", bodyshop);
			
		}
		
		Map<String, Integer> map = new HashMap<>();
		int midnightToSix = 0;
		int sixToNoon = 0;
		int noonToSix = 0;
		int sixToMidnight = 0;
		
		for (ReservationVO reserve : rService.getReservationByBodyshopNo(bodyshop.getBodyshop_no())) {
			String time = reserve.getReservation_time().split(" ")[1];
			int hour = Integer.parseInt(time.split(":")[0]);
			if(hour >= 0 && hour < 6)
				midnightToSix ++;
			if(hour >= 6 && hour < 12)
				sixToNoon ++;
			if(hour >= 12 && hour < 18)
				noonToSix ++;
			if(hour >= 18 && hour < 24)
				sixToMidnight ++;
		}
		map.put("0~6", midnightToSix);
		map.put("6~12", sixToNoon);
		map.put("12~18", noonToSix);
		map.put("18~24", sixToMidnight);
		
		session.setAttribute("chartData", map);
		session.setAttribute("tableData", rService.getReservationForWeb(bodyshop.getBodyshop_no()));
		return mv;
	}

}
