package com.mtoliv.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@EnableAutoConfiguration
@RestController
@RequestMapping(value = "/test")
public class TestController {

	@RequestMapping(value = "/aa", method = RequestMethod.GET)
	String get() {
		
		return "asdasd";
	}
}