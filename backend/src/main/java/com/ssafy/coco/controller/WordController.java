package com.ssafy.coco.controller;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ssafy.coco.service.TagService;
import com.ssafy.coco.service.WordDictionaryService;
import com.ssafy.coco.vo.Tag;
import com.ssafy.coco.vo.WordDictionary;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/wdc")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Api(tags = { "WordController Controller" }, description = "사전에 키워드를 통해 검색")
public class WordController {
	@Autowired
	WordDictionaryService wordDictionaryService;

	@Autowired
	TagService tagService;

	@ApiOperation(value = "선택 워드 사전 반환", response = List.class)
	@RequestMapping(value = "/findWordDictionary", method = RequestMethod.POST)
	public ResponseEntity<List<WordDictionary>> findWordDictionary(@RequestBody String keyword) throws Exception {
		WordDictionary wd = new WordDictionary();
		wd.setWord(keyword);
		List<WordDictionary> answers = wordDictionaryService.findWordDictionary(wd);
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<WordDictionary>>(answers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "존재 하는 해시태그의 사전 전처리 구성", response = List.class)
	@RequestMapping(value = "/makeWordDictionary", method = RequestMethod.POST)
	void makeWordDictionary() throws Exception {
		String inputString = "python";
		List<Tag> list = tagService.findAllTag();
		WordDictionary inputTestWD = new WordDictionary();
		for (Tag tag : list) {
			inputString = tag.getTagName();
			inputTestWD.setWord(inputString);
			int size = wordDictionaryService.findWordDictionary(inputTestWD).size();
			if (size != 0)
				continue;
			HttpHeaders headers = new HttpHeaders();
			RestTemplate restTemplate = new RestTemplate();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			JSONParser p = new JSONParser();

			String uri = "https://www.googleapis.com/customsearch/v1/siterestrict?key=AIzaSyDwijj_hIBLqxw5__S3dkghvPZbt-_djvk&cx=011639170629408361658:ycrrovtrshs&q="
					+ inputString + "&start=1";
			ResponseEntity<String> rest_reponse;
			rest_reponse = restTemplate.getForEntity(uri, String.class);

			String bodys = rest_reponse.getBody();
//		System.out.println(bodys);
			JSONObject data = (JSONObject) p.parse(bodys);

			JSONArray items = (JSONArray) data.get("items");
			if (items == null)
				continue;
			JSONObject item;

			for (int i = 0; i < items.size(); i++) {
				item = (JSONObject) items.get(i);

				String title = item.get("title").toString();
				String link = item.get("link").toString();
				String snippet = item.get("snippet").toString();
				JSONObject pagemap = (JSONObject) item.get("pagemap");
				if (pagemap == null)
					continue;
				JSONArray cse_thumbnail = (JSONArray) pagemap.get("cse_thumbnail");
				String src = null;
				if (cse_thumbnail != null) {
					src = ((JSONObject) (cse_thumbnail.get(0))).get("src").toString();
				}
				WordDictionary wd = new WordDictionary();
				wd.setWord(inputString);
				wd.setLink(link);
				wd.setDescription(title + "\n" + snippet);
				wd.setThumbnailSrc(src);
				wordDictionaryService.addWordDictionary(wd);
			}
		}
	}

	@ApiOperation(value = "존재 하는 해시태그의 사전 전처리 구성", response = List.class)
	@RequestMapping(value = "/makeWordByKeyword", method = RequestMethod.POST)
	void makeWordByKeyword(@RequestBody String keyword) throws Exception {
		String inputString = keyword;
		WordDictionary inputTestWD = new WordDictionary();
		inputTestWD.setWord(inputString);
		int size = wordDictionaryService.findWordDictionary(inputTestWD).size();
		if (size != 0)
			return;
		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		JSONParser p = new JSONParser();

		String uri = "https://www.googleapis.com/customsearch/v1/siterestrict?key=AIzaSyDwijj_hIBLqxw5__S3dkghvPZbt-_djvk&cx=011639170629408361658:ycrrovtrshs&q="
				+ inputString + "&start=1";
		ResponseEntity<String> rest_reponse;
		rest_reponse = restTemplate.getForEntity(uri, String.class);

		String bodys = rest_reponse.getBody();
//		System.out.println(bodys);
		JSONObject data = (JSONObject) p.parse(bodys);

		JSONArray items = (JSONArray) data.get("items");
		if (items == null)
			return;
		JSONObject item;

		for (int i = 0; i < items.size(); i++) {
			item = (JSONObject) items.get(i);

			String title = item.get("title").toString();
			String link = item.get("link").toString();
			String snippet = item.get("snippet").toString();
			JSONObject pagemap = (JSONObject) item.get("pagemap");
			if (pagemap == null)
				continue;
			JSONArray cse_thumbnail = (JSONArray) pagemap.get("cse_thumbnail");
			String src = null;
			if (cse_thumbnail != null) {
				src = ((JSONObject) (cse_thumbnail.get(0))).get("src").toString();
			}
			WordDictionary wd = new WordDictionary();
			wd.setWord(inputString);
			wd.setLink(link);
			wd.setDescription(title + "\n" + snippet);
			wd.setThumbnailSrc(src);
			wordDictionaryService.addWordDictionary(wd);
		}
	}

}
