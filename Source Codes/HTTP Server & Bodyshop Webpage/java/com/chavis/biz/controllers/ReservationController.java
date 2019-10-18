package com.chavis.biz.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chavis.biz.service.BodyshopService;
import com.chavis.biz.service.ReservationService;
import com.chavis.biz.vo.ReservationVO;

@CrossOrigin("*")
@RestController
public class ReservationController {

	public static Logger log = LoggerFactory.getLogger(ReservationController.class);
	@Autowired
	ReservationService service;
	@Autowired
	BodyshopService bservice;

	@RequestMapping(value = "/Reservation/add.do", method = RequestMethod.POST)
	public String addReservation2(@RequestBody Map<String, String> map) {
		log.info("/Reservation/add2.do 실행");
		map.put("repaired_time", "NO");
		int result;
		try {
			result = service.addReservation(map);
			if (result == 1) {
				return "SUCCESS";

			} else {
				return "FAIL";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "FAIL";
		}
	}

	@ExceptionHandler(SQLException.class)
	public String Ex(SQLException ex) {
		log.info("ReservationController SQLException");
		return "FAIL";
	}

	@ExceptionHandler(Exception.class)
	public String Ex(Exception exception, Model model) {
		log.info("ReservationController Exception");
		// ReservationController 예외발생시 호출됨
		model.addAttribute("exception", exception);
		return "error";
	}
}
