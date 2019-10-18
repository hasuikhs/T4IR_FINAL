package com.chavis.biz.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chavis.biz.service.CarService;
import com.chavis.biz.vo.CarVO;

@CrossOrigin("*")
@RestController
public class CarController {

	public static Logger log = LoggerFactory.getLogger(CarController.class);

	@Autowired
	CarService service;

	@RequestMapping(value = "/Car/personalview.do", method = RequestMethod.POST)
	public CarVO getPersonalCar(@RequestBody Map<String, String> map) {
		log.info("/Car/personalview.do 실행");
		return service.getPersonalCar(map.get("member_id"));
	}

	@ExceptionHandler(Exception.class)
	public String Ex(Exception exception, Model model) {
		log.info("CarController Exception");
		// ReserveController 예외발생시 호출됨
		model.addAttribute("exception", exception);
		return "error";
	}
}
