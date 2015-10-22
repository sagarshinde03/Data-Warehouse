package cse.buffalo.edu.controllers;

import java.net.URLDecoder;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.stat.inference.TTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cse.buffalo.edu.services.DataService;
import cse.buffalo.edu.utility.Util;

@Controller
public class WarehouseController {
	
	@Autowired(required=false)
	public DataService dataService = new DataService();
	
	@RequestMapping("/welcome")
	public ModelAndView welcome(){
		ModelAndView model = new ModelAndView("Home");
		//dataService.query();
		return model;
	}
	
	@RequestMapping("/part1")
	public ModelAndView part1(){
		ModelAndView model = new ModelAndView("Part1");
		return model;
	}
	
	@RequestMapping("/part2")
	public ModelAndView part2(){
		ModelAndView model = new ModelAndView("Part2");
		return model;
	}
	
	@RequestMapping(value="/fetchResult", produces="text/plain" , method=RequestMethod.POST)
	@ResponseBody
	public String fetchResult(@RequestBody String input){
		input = URLDecoder.decode(input).split("=")[0];
		System.out.println("input to populate: "+input);
		String[] data = input.split(":");
		String result = "";
		if( !data[0].contains("Q1")){
			result = dataService.queryForResult(data[0] , data[1]);
		}
		else{
			result = dataService.queryForResultCount(data[0] , data[1]);
		}
		
		return result;
	}
	
	
	@RequestMapping(value="/fetchData", produces="text/plain", method=RequestMethod.POST)
	@ResponseBody
	public String fetchData(@RequestBody String input){
		input = URLDecoder.decode(input).split("=")[0];
		System.out.println("input to populate: "+input);
		String[] data = input.split(":");
		String result = dataService.queryForOptions(data[0] , data[1]);
		return result;
	}
	
	
	@RequestMapping(value="/fetchResultQ4", produces="text/plain" , method=RequestMethod.POST)
	@ResponseBody
	public String fetchResultQ4(@RequestBody String input){
		input = URLDecoder.decode(input).split("=")[0];
		System.out.println("input to populate: "+input);
		String[] data = input.split(":");
		String[] opt = data[1].split(",");
		List<Map<String, Object>> resultSet1 = dataService.queryForResultQ4(opt[1] , opt[0]);	
		List<Map<String, Object>> resultSet2 = dataService.queryForResultQ4(opt[1] , "NOT-"+opt[0]);
		
		return String.valueOf(Util.calculateTTest(resultSet1, resultSet2));
	}
	
	@RequestMapping(value="/fetchResultQ5", produces="text/plain" , method=RequestMethod.POST)
	@ResponseBody
	public String fetchResultQ5(@RequestBody String input){
		input = URLDecoder.decode(input).split("=")[0];
		System.out.println("input to query: "+input);
		String[] data = input.split(":");
		//String[] data = input.split(":");
		List<Map<String, Object>> resultSet1 = dataService.queryForResultQ4(data[1] , "ALL");	
		List<Map<String, Object>> resultSet2 = dataService.queryForResultQ4(data[1] , "AML");
		List<Map<String, Object>> resultSet3 = dataService.queryForResultQ4(data[1] , "Colon tumor");
		List<Map<String, Object>> resultSet4 = dataService.queryForResultQ4(data[1] , "Breast tumor");
		
		return String.valueOf(Util.calculateFStatistics(resultSet1, resultSet2 , resultSet3 , resultSet4));
	}
	
	@RequestMapping(value="/fetchResultQ6", produces="text/plain" , method=RequestMethod.POST)
	@ResponseBody
	public String fetchResultQ6(@RequestBody String input){
		input = URLDecoder.decode(input).split("=")[0];
		System.out.println("input to query: "+input);
		String[] data = input.split(":");
		//String[] data = input.split(":");
		List<Map<String, Object>> resultSet1 = dataService.queryForResultQ6(data[1] , "ALL");	
		System.out.println("Size for ALL:"+resultSet1.size());
		List<Map<String, Object>> resultSet2 = dataService.queryForResultQ6(data[1] , "AML");
		System.out.println("Size for AML:"+resultSet2.size());
		double avgCorr1 = Util.calAvgCorr(resultSet1);
		double avgCorr2 = Util.calAvgCorr(resultSet1 , resultSet2);
		
		String rs = "<ul><li>Average correlation for ALL - ALL : "+avgCorr1 + "</li>"
				+ "<li>Average correlation for ALL - AML : "+avgCorr2 + "</li>"
				+ "</ul>";
		
		return rs;
	}
	
	
	@RequestMapping(value="/fetchResultQ7", produces="text/plain" , method=RequestMethod.POST)
	@ResponseBody
	public String fetchResultQ7(@RequestBody String input){
		input = URLDecoder.decode(input).split("=")[0];
		System.out.println("input to query: "+input);
		String[] data = input.split(":");
		//String[] data = input.split(":");
		List<Map<String, Object>> resultSet1 = dataService.queryForResultQ7(data[1] , data[1]);	
		System.out.println("Size for "+data[1]+":"+resultSet1.size());
		List<Map<String, Object>> resultSet2 = dataService.queryForResultQ7(data[1] , "NOT-"+data[1]);
		System.out.println("Size for NOT-"+data[1]+":"+resultSet2.size());
		
		List<Integer> result = Util.getInfoGene(resultSet1 , resultSet2);
		
		StringBuilder builder = new StringBuilder();
		builder.append("<ul>");				
		for( Integer gene : result ){
			builder.append("<li><ul>");
			builder.append("<li>"+gene+"</li>");
			builder.append("</ul></li>");
		}
		builder.append("</ul>");
		
		return builder.toString();
		
	}
	
	
	@RequestMapping(value="/fetchResultQ8", produces="text/plain" , method=RequestMethod.POST)
	@ResponseBody
	public String fetchResultQ8(@RequestBody String input){
		input = URLDecoder.decode(input).split("=")[0];
		System.out.println("input to query: "+input);
		String[] data = input.split(":");
		//String[] data = input.split(":");
		List<Map<String, Object>> resultSet1 = dataService.queryForResultQ7(data[1] , data[1]);	
		System.out.println("Size for "+data[1]+":"+resultSet1.size());
		List<Map<String, Object>> resultSet2 = dataService.queryForResultQ7(data[1] , "NOT-"+data[1]);
		System.out.println("Size for NOT-"+data[1]+":"+resultSet2.size());
		
		List<Integer> result = Util.getInfoGene(resultSet1 , resultSet2);
		
		StringBuilder builder = new StringBuilder();
		int counter = 1;
		int resultSize = result.size();
		for( Integer gene : result ){
			if( counter < resultSize ){
				builder.append(gene + " , ");
			}
			else{
				builder.append(gene);
			}
			counter++;
		}
		
		String infoGenes = builder.toString();
		
		List<Map<String, Object>> resultForSelD = dataService.queryForResultQ8(data[1] , infoGenes);
		List<Map<String, Object>> resultForNotD = dataService.queryForResultQ8("NOT-"+data[1] , infoGenes);
		
		List<Map<String, Object>> sampleData = dataService.queryForResultQ8("SAMPLE" , infoGenes);
		LinkedList<Integer> test1 = new LinkedList<Integer>();
		LinkedList<Integer> test2 = new LinkedList<Integer>();
		LinkedList<Integer> test3 = new LinkedList<Integer>();
		LinkedList<Integer> test4 = new LinkedList<Integer>();
		LinkedList<Integer> test5 = new LinkedList<Integer>();
		int count = 1;
		
		for( Map<String, Object> map : sampleData ){
			for( Map.Entry<String, Object> entrySet : map.entrySet() ){
				switch (entrySet.getKey()) {
					case "TEST1":
						test1.add(Integer.parseInt(entrySet.getValue().toString()));
						break;
					case "TEST5":
						test5.add(Integer.parseInt(entrySet.getValue().toString()));
						break;
					case "TEST2":
						test2.add(Integer.parseInt(entrySet.getValue().toString()));
						break;
					case "TEST3":
						test3.add(Integer.parseInt(entrySet.getValue().toString()));
						break;
					case "TEST4":
						test4.add(Integer.parseInt(entrySet.getValue().toString()));
						break;
				}
			}
		}
		
		Boolean results[] = new Boolean[5];
		results[0] = Util.classifyPatient(resultForSelD ,resultForNotD ,test1 );
		results[1] = Util.classifyPatient(resultForSelD ,resultForNotD ,test2 );
		results[2] = Util.classifyPatient(resultForSelD ,resultForNotD ,test3 );
		results[3] = Util.classifyPatient(resultForSelD ,resultForNotD ,test4 );
		results[4] = Util.classifyPatient(resultForSelD ,resultForNotD ,test5 );
		
		
		StringBuilder rsBuilder = new StringBuilder();
		rsBuilder.append("<ul>");	
		count = 0;
		for( Boolean rs : results ){
			count++;
			rsBuilder.append("<li><ul>");
			if( rs ){
				rsBuilder.append("<li>Patient "+count+" is Classified as: "+data[1]+"</li>");
			}
			else{
				rsBuilder.append("<li>Patient "+count+" is Classified as: not "+data[1]+"</li>");
			}
			rsBuilder.append("</ul></li>");
		}
		
		rsBuilder.append("</ul>");
		
		return rsBuilder.toString();
		
	}
	
	

}
