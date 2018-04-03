package com.center.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.center.domain.Center;
import com.center.service.CenterService;

@RestController
@RequestMapping(value = "/center")
public class CenterController {

	@Autowired
	CenterService centerService;
	
	@CrossOrigin
	@RequestMapping(value = "/create", 
	method = RequestMethod.POST,
	consumes = MediaType.APPLICATION_JSON_VALUE,
	produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Center> createCenter(@RequestBody Center center)
    {
		Center exists = centerService.findOne(center.getId());
		
		if(exists != null){
			return new ResponseEntity<Center>(HttpStatus.CONFLICT);
		}
        
		Center saved = null;
		try {
			saved = centerService.create(center);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return new ResponseEntity<Center>(saved, HttpStatus.CREATED);
    }
	
	@CrossOrigin
	@RequestMapping(value = "/update", 
	method = RequestMethod.PUT,
	consumes = MediaType.APPLICATION_JSON_VALUE,
	produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Center> updateCenter(@RequestBody Center center)
    {
		Center exists = centerService.findOne(center.getId());
		
		if(exists == null){
			return new ResponseEntity<Center>(HttpStatus.NOT_FOUND);
		}
        
		Center saved = null;
		try {
			saved = centerService.update(center);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return new ResponseEntity<Center>(saved, HttpStatus.OK);
    }
	
	@CrossOrigin
	@RequestMapping(
			value = "/{id}/get",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Center> getCenter(@PathVariable String id) {
		Center center = centerService.findOne(Long.parseLong(id));
		
		if(center == null){
			return new ResponseEntity<Center>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Center>(center,
				HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
			value = "/getAll",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity< ArrayList<Center> > getCenters() {
		ArrayList<Center> centers = (ArrayList<Center>) centerService.findAll();
		return new ResponseEntity< ArrayList<Center> >(centers,
				HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
			value = "/{id}/delete",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Center> deleteCenterById(@PathVariable String id) {
		Center center = centerService.findOne(Long.parseLong(id));
		
		if(center == null){
			return new ResponseEntity<Center>(HttpStatus.NOT_FOUND);
		}

		try {
			centerService.delete(center);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Center>(HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/delete", 
	method = RequestMethod.DELETE,
	consumes = MediaType.APPLICATION_JSON_VALUE,
	produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Center> deleteCenter(@RequestBody Center center)
    {
		Center exists = centerService.findOne(center.getId());
		
		if(exists == null){
			return new ResponseEntity<Center>(HttpStatus.NOT_FOUND);
		}
        
		try {
			centerService.delete(center);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new ResponseEntity<Center>(HttpStatus.OK);
    }
	
}
