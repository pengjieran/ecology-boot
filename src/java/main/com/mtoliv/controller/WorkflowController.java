package com.mtoliv.controller;

import java.rmi.RemoteException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
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
	
	@Autowired
	private RedisConnectionFactory redisConnectionFactory;
	
	private final JdkSerializationRedisSerializer serializer = new JdkSerializationRedisSerializer();
	
	@GetMapping(value = "/{workflowId}")
	public ResponseEntity<Object> getWorkflowRequestInfo(@PathVariable(name = "workflowId") int workflowId) {
		
		WorkflowServicePortTypeProxy proxy = new WorkflowServicePortTypeProxy();
		
		try {
			
			WorkflowRequestInfo workflowRequestInfo = null;
			
			if (!exists(String.valueOf(workflowId))) {
				
				workflowRequestInfo = proxy.getCreateWorkflowRequestInfo(workflowId, 1);
				put(String.valueOf(workflowId), workflowRequestInfo);
			} else {
				
				workflowRequestInfo = (WorkflowRequestInfo) get(String.valueOf(workflowId));
			}
			
			if (null == workflowRequestInfo) return ResponseEntity.ok().build();
			
			
			return ResponseEntity.ok(workflowRequestInfo);
			
		} catch (RemoteException e) {
			
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
	}
	
	private boolean exists(String key) {
		
		RedisConnection connection = redisConnectionFactory.getConnection();
		Boolean exists = connection.exists(serializer.serialize(key));
		connection.close();
		return exists;
	}
	
	private void put(String key, Object object) {
		
		RedisConnection connection = redisConnectionFactory.getConnection();
		connection.set(serializer.serialize(key), serializer.serialize(object));
		connection.close();
	}
	
	private Object get(String key) {
		
		RedisConnection connection = redisConnectionFactory.getConnection();
		byte[] bs = connection.get(serializer.serialize(key));
		Object object = serializer.deserialize(bs);
		connection.close();
		
		return object;
	}
	
}