package com.mtoliv.controller;

import java.rmi.RemoteException;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.com.weaver.services.webservices.WorkflowServicePortTypeProxy;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import weaver.workflow.webservices.WorkflowRequestInfo;

@EnableSwagger2
@EnableAutoConfiguration
@RestController
@RequestMapping(value = "/workflow", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class WorkflowController {
	
	@GetMapping(value = "/{workflowId}")
	public ResponseEntity<Object> getWorkflowRequestInfo(@PathVariable(name = "workflowId") int workflowId) {
		
		WorkflowServicePortTypeProxy proxy = new WorkflowServicePortTypeProxy();
		
		try {
			
			WorkflowRequestInfo workflowRequestInfo = proxy.getCreateWorkflowRequestInfo(workflowId, 1);
			if (null == workflowRequestInfo) return ResponseEntity.ok().build();
			
			return ResponseEntity.ok(workflowRequestInfo);
			
		} catch (RemoteException e) {
			
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
	}

}