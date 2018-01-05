package org.ekstep.dialcode.controller;

import java.util.Map;

import org.ekstep.common.controller.BaseController;
import org.ekstep.common.dto.Request;
import org.ekstep.common.dto.Response;
import org.ekstep.dialcode.enums.DialCodeEnum;
import org.ekstep.dialcode.mgr.IDialCodeManager;
import org.ekstep.telemetry.logger.TelemetryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller Class for All CRUD Operation of QR Codes (DIAL Code). This class
 * is entry point for all operation related to DIAL Code.
 * 
 * @author gauraw
 *
 */
@Controller
@RequestMapping("/v3/dialcode")
public class DialCodeV3Controller extends BaseController {

	private static final String CHANNEL_ID = "X-Channel-Id";

	@Autowired
	private IDialCodeManager dialCodeManager;

	/**
	 * @param requestMap
	 * @param channelId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/generate", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> generateDialCode(@RequestBody Map<String, Object> requestMap,
			@RequestHeader(value = CHANNEL_ID, required = true) String channelId) {
		String apiId = "ekstep.dialcode.generate";
		Request request = getRequest(requestMap);
		try {
			Map<String, Object> map = (Map<String, Object>) request.get(DialCodeEnum.dialcodes.name());
			Response response = dialCodeManager.generateDialCode(map, channelId);
			return getResponseEntity(response, apiId, null);
		} catch (Exception e) {
			TelemetryManager.log("Exception Occured while generating Dial Code : ", e.getMessage(), e);
			return getExceptionResponseEntity(e, apiId, null);
		}
	}

	/**
	 * @param dialCodeId
	 * @return
	 */
	@RequestMapping(value = "/read/{id:.+}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> readDialCode(@PathVariable(value = "id") String dialCodeId) {
		String apiId = "ekstep.dialcode.info";
		try {
			Response response = dialCodeManager.readDialCode(dialCodeId);
			return getResponseEntity(response, apiId, null);
		} catch (Exception e) {
			TelemetryManager.log("Exception Occured while reading Dial Code details : ", e.getMessage(), e);
			return getExceptionResponseEntity(e, apiId, null);
		}
	}

	/**
	 * @param dialCodeId
	 * @param requestMap
	 * @param channelId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/update/{id:.+}", method = RequestMethod.PATCH)
	@ResponseBody
	public ResponseEntity<Response> updateDialCode(@PathVariable(value = "id") String dialCodeId,
			@RequestBody Map<String, Object> requestMap,
			@RequestHeader(value = CHANNEL_ID, required = true) String channelId) {
		String apiId = "ekstep.dialcode.update";
		Request request = getRequest(requestMap);
		try {
			Map<String, Object> map = (Map<String, Object>) request.get(DialCodeEnum.dialcode.name());
			Response response = dialCodeManager.updateDialCode(dialCodeId, channelId, map);
			return getResponseEntity(response, apiId, null);
		} catch (Exception e) {
			TelemetryManager.log("Exception Occured while updating Dial Code : ", e.getMessage(), e);
			return getExceptionResponseEntity(e, apiId, null);
		}
	}

	/**
	 * @param requestMap
	 * @param channelId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> listDialCode(@RequestBody Map<String, Object> requestMap,
			@RequestHeader(value = CHANNEL_ID, required = true) String channelId) {
		String apiId = "ekstep.dialcode.list";
		Request request = getRequest(requestMap);
		try {
			Map<String, Object> map = (Map<String, Object>) request.get(DialCodeEnum.search.name());
			Response response = dialCodeManager.listDialCode(channelId, map);
			return getResponseEntity(response, apiId, null);
		} catch (Exception e) {
			TelemetryManager.log("Exception Occured while Performing List Operation for Dial Codes : ", e.getMessage(),
					e);
			return getExceptionResponseEntity(e, apiId, null);
		}
	}

	/**
	 * @param dialCodeId
	 * @param channelId
	 * @return
	 */
	@RequestMapping(value = "/publish/{id:.+}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> publishDialCode(@PathVariable(value = "id") String dialCodeId,
			@RequestHeader(value = CHANNEL_ID, required = true) String channelId) {
		String apiId = "ekstep.dialcode.publish";
		try {
			Response response = dialCodeManager.publishDialCode(dialCodeId, channelId);
			return getResponseEntity(response, apiId, null);
		} catch (Exception e) {
			TelemetryManager.log("Exception Occured while Performing Publish Operation on Dial Code : ", e.getMessage(),
					e);
			return getExceptionResponseEntity(e, apiId, null);
		}
	}
}