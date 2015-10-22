package cse.buffalo.edu.utility;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.stat.inference.TTest;

public class Util {
	
	
	public static List<Integer> getInfoGene(List<Map<String, Object>>... dataSet){
		Map<Integer, LinkedList<Integer>> map1  = getMap(dataSet[0]);
		Map<Integer, LinkedList<Integer>> map2  = getMap(dataSet[1]);
		TTest tTest = new TTest();
		
		List<Integer> result = new LinkedList<>();
		for( Map.Entry<Integer, LinkedList<Integer>> entry : map1.entrySet() ){
			if( map2.containsKey(entry.getKey()) ){
				
				double[] arr1 = new double[entry.getValue().size()];
				for( int counter = 0; counter < entry.getValue().size(); counter++ ){
					arr1[counter] = entry.getValue().get(counter);
				}
				
				
				double[] arr2 = new double[map2.get(entry.getKey()).size()];
				for( int counter = 0; counter < map2.get(entry.getKey()).size(); counter++ ){
					arr2[counter] = map2.get(entry.getKey()).get(counter);
				}
				
				double pValue = tTest.tTest(arr1 , arr2 );
				
				if( pValue <= 0.01 ){
					result.add(entry.getKey());
				}
			}
		}
		
		return result;
	}
	
	public static LinkedList<Integer> convertToList(List<Map<String, Object>> data){
		LinkedList<Integer> list = new LinkedList<>();
		
		for( Map<String, Object> map : data ){
			for( Map.Entry<String, Object> entrySet : map.entrySet() ){
				list.add(Integer.parseInt(entrySet.getValue().toString()));
			}
		}
		
		return list;
	}
	
	
	
	public static double[] convertToArray(List<Map<String, Object>> data){
		double[] list = new double[data.size()];
		int i = 0;
		for( Map<String, Object> map : data ){
			for( Map.Entry<String, Object> entrySet : map.entrySet() ){
				//list.add(Double.parseDouble(entrySet.getValue().toString()));
				list[i] = Double.parseDouble(entrySet.getValue().toString());
			}
			i++;
		}
		
		return list;
	}
	
	public static double calculateMean(List<Integer> values){
		double sum = 0.0;
		for( Integer val : values ){
			sum += val;
		}
		
		return sum/values.size();
	}
	
	
	public static double calculateVariance(double mean , LinkedList<Integer> list){
		double var = 0.0;
		for( Integer val : list ){
			var += Math.pow(val - mean, 2);
		}
		
		return var/(list.size()-1);
	}
	
	public static double calculateTTest(List<Map<String, Object>> dataSet1 , List<Map<String, Object>> dataSet2){
		LinkedList<Integer> list1 = convertToList(dataSet1);
		System.out.println("List1:"+list1.size());
		LinkedList<Integer> list2 = convertToList(dataSet2);
		System.out.println("List2:"+list2.size());
		
		/*TTest test = new TTest();
		System.out.println("Ttest value:"+test.t(convertToArray(dataSet1), convertToArray(dataSet2)));*/
		
		
		double mean1 = calculateMean(list1);
		double mean2 = calculateMean(list2);
		
		double var1 = calculateVariance(mean1, list1);
		double var2 = calculateVariance(mean2, list2);
		
		return (mean1 - mean2)/(Math.sqrt((var1/list1.size()) + (var2/list2.size())));
	}
	
	
	
	public static double calculateFStatistics(List<Map<String, Object>>... dataSet){
		LinkedList<Integer> list1 = convertToList(dataSet[0]);
		LinkedList<Integer> list2 = convertToList(dataSet[1]);
		LinkedList<Integer> list3 = convertToList(dataSet[2]);
		LinkedList<Integer> list4 = convertToList(dataSet[3]);
		
		double mean1 = calculateMean(list1);
		double mean2 = calculateMean(list2);
		double mean3 = calculateMean(list3);
		double mean4 = calculateMean(list4);
		
		int totalSize =  list1.size() + list2.size() + list3.size() + list4.size();
		double totalMean = (mean1 * list1.size() + mean2 * list2.size() + mean3 * list3.size() + mean4 * list4.size() )/totalSize;
		
		// between-group variability
		double betwGrpVar = (list1.size()* Math.pow(mean1-totalMean, 2) + list2.size()* Math.pow(mean2-totalMean, 2) 
								+ list3.size()* Math.pow(mean3-totalMean, 2) + list4.size()* Math.pow(mean4-totalMean, 2))/(4-1);
		
		// within-group variability
		double withinGrpVar = (calculateVariance(mean1, list1) * (list1.size()-1) +
									calculateVariance(mean2, list2) * (list2.size()-1) +
									calculateVariance(mean3, list3) * (list3.size()-1) +
									calculateVariance(mean4, list4) * (list4.size()-1))/(totalSize-4);
		
		return betwGrpVar/withinGrpVar;
	}
	
	
	public static Map<Integer, LinkedList<Integer>> getMap(List<Map<String, Object>> group){
		Map<Integer, LinkedList<Integer>> result = new LinkedHashMap<Integer, LinkedList<Integer>>();
		
		for( Map<String, Object> map : group ){
			LinkedList<Integer> list = null;
			int i = 0;
			for( Map.Entry<String, Object> entrySet : map.entrySet() ){
				if( i == 0 && result.containsKey(Integer.parseInt(entrySet.getValue().toString())) ){
					list = result.get(Integer.parseInt(entrySet.getValue().toString()));
					i++;
				}
				else if( i == 0 ){
					list = new LinkedList<Integer>();
					result.put(Integer.parseInt(entrySet.getValue().toString()), list);
					i++;
				}
				else if( i != 0 ){
					list.add(Integer.parseInt(entrySet.getValue().toString()));
				}
			}
		}
		
		return result;
	}
	
	@SafeVarargs
	public static double calAvgCorr(List<Map<String, Object>>... dataSet){
		Map<Integer, LinkedList<Integer>> map1 = new LinkedHashMap<Integer, LinkedList<Integer>>();
		Map<Integer, LinkedList<Integer>> map2 = null;
		map1 = getMap(dataSet[0]);
		double avgCorr = 0.0;
		
		if( dataSet.length > 1 ){
			map2 = new LinkedHashMap<Integer, LinkedList<Integer>>();
			map2 = getMap(dataSet[1]);
			System.out.println("Call Times (ALL - AML)"+(map1.size()*map2.size()));
			for( Map.Entry<Integer, LinkedList<Integer>> entry1 : map1.entrySet() ){
				for( Map.Entry<Integer, LinkedList<Integer>> entry2 : map2.entrySet() ){
					avgCorr += calcPCorr(entry1.getValue() , entry2.getValue());
				}
			}
			
			avgCorr =  avgCorr/(map1.size()*map2.size());
		}
		else{
			
			Integer[] keys = map1.keySet().toArray(new Integer[1]);
			for( int counter = 0; counter < keys.length; counter++ ){
				for( int innerCounter = counter+1; innerCounter < keys.length; innerCounter++ ){
					avgCorr += calcPCorr(map1.get(keys[counter]), map1.get(keys[innerCounter]));
				}
			}
			
			avgCorr =  (avgCorr * 2)/(map1.size() * (map1.size()-1));
		}
		
		return avgCorr;
	} 
	
	
	public static double calcPCorr(LinkedList<Integer> list1 , LinkedList<Integer> list2){
		double mean1 = calculateMean(list1);
		double mean2 = calculateMean(list2);
		
		double corr = 0.0;
		
		double cov = 0.0;
		for( int counter = 0; counter < list1.size(); counter++ ){
			cov += (list1.get(counter)-mean1) * (list2.get(counter)-mean2);
		}
		
		corr = cov / Math.sqrt(calculateVariance(mean1, list1)*(list1.size()-1)*calculateVariance(mean2, list2)*(list2.size()-1));
		
		return corr;
	}
	
	
	public static double calCorr(LinkedList<Integer> list1 , LinkedList<Integer> list2){
		System.out.println("list1:"+list1.size());
		System.out.println("list2:"+list2.size());
		double cov = calcCovariance(list1, list2) * (list1.size()-1);
		double var1 = calculateVariance( calculateMean(list1) , list1 )* (list1.size()-1);
		double var2 = calculateVariance( calculateMean(list2) , list2 )* (list2.size()-1);
		double corr = cov/Math.sqrt(var1 * var2);
		return corr;
	}
	
	
	public static double calcCovariance(List<Integer> list1 , List<Integer> list2){
		double mean1 = calculateMean(list1);
		double mean2 = calculateMean(list2);
		double cov = 0.0;
		for( int counter = 0; counter < list1.size(); counter++ ){
			cov += (list1.get(counter)-mean1) * (list2.get(counter)-mean2);
		}
		
		cov = cov / (list1.size()-1);
		return cov;
	}
	
	public static Boolean classifyPatient(List<Map<String, Object>> resultForSelD , 
								List<Map<String, Object>> resultForNotD , LinkedList<Integer> testValues){
		Map<Integer, LinkedList<Integer>> mapForSelD = getMap(resultForSelD);
		Map<Integer, LinkedList<Integer>> mapForNotD = getMap(resultForNotD);
		
		double[] corr1 = new double[mapForSelD.entrySet().size()];
		int counter = -1;
		for (Map.Entry<Integer, LinkedList<Integer>> entry1 : mapForSelD.entrySet()){
			counter++;
			corr1[counter] = calcPCorr(entry1.getValue(), testValues);
		}
		
		counter = -1;
		double[] corr2 = new double[mapForNotD.entrySet().size()];
		for (Map.Entry<Integer, LinkedList<Integer>> entry2 : mapForNotD.entrySet()){
			counter++;
			corr2[counter] = calcPCorr(entry2.getValue(), testValues);
		}
		
		TTest tTest = new TTest();
		double pValue = tTest.tTest(corr1 , corr2 );
		
		
		if( pValue < 0.01 )
			return true;
		else
			return false;
			
	}
	
	
}
