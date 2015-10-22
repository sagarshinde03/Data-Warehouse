package cse.buffalo.edu.services;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DataService {
	
	@Autowired
	public DataSource dataSource;
	
	private JdbcTemplate template;
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
	
	public String queryForResultCount(String queryType, String dataType){
		this.template = new JdbcTemplate(this.dataSource);
		String queryString = "SELECT COUNT(p.p_id) from PATIENT p join CLINICAL_FACT cf on "
							+"p.p_id = cf.p_id join DISEASE ds on ds.ds_id = cf.ds_id WHERE ";
		switch (queryType) {
			case "Q1-DD":
				queryString += "ds.description = '"+dataType+"'";
				break;
			case "Q1-DT":
				queryString += "ds.type = '"+dataType+"'";
				break;
			case "Q1-DN":
				queryString += "ds.name = '"+dataType+"'";
				break;
			default:
				break;
		}
		
		List<Map<String, Object>> rs = template.queryForList(queryString);
		String result = "";
		for( Map<String, Object> map : rs ){
			for( Map.Entry<String, Object> entrySet : map.entrySet() ){
				result = entrySet.getValue().toString();
			}
		}
		
		return result;
	}
	
	
	public String queryForResult(String queryType, String dataOption){
		this.template = new JdbcTemplate(this.dataSource);
		String queryString = "";
		String[] opt = null;
		switch (queryType) {
			case "Q2":
				queryString = "SELECT DISTINCT dr.type FROM DRUG dr JOIN CLINICAL_FACT cf on dr.dr_id = cf.dr_id "
							+"JOIN DISEASE ds on ds.ds_id = cf.ds_id WHERE ds.description='"+dataOption+"'";
				break;
			case "Q3":
				opt = dataOption.split(",");
				queryString = "select microarray_fact.s_id, microarray_fact.exp from probe join gene_fact on probe.u_id=gene_fact.UIDD "
						+"join microarray_fact on microarray_fact.pb_id = probe.pb_id "
						+"where gene_fact.cl_id = "+opt[1]+" and microarray_fact.mu_id= 1 "
						+"and microarray_fact.s_id in (select s_id from clinical_fact where p_id in " 
						+"(select p_id from clinical_fact where ds_id in "
						    +"(select ds_id from disease where disease.name= '"+opt[0]+"' ) "
						  +") and s_id is not null)";
				break;
			case "Q4":
				opt = dataOption.split(",");
				queryString = "select microarray_fact.exp from probe join gene_fact on probe.u_id=gene_fact.UIDD "
						+"join microarray_fact on microarray_fact.pb_id=probe.pb_id where gene_fact.go_id="+opt[1]
						+" and microarray_fact.s_id in (select s_id from clinical_fact where p_id in " 
						+"(select p_id from clinical_fact where ds_id in "
						+"(select ds_id from disease where disease.name = '"+opt[0]+"')) "
						+"and s_id is not null)";
				break;
			default:
				break;
		}
		String result = processTabularData(template.queryForList(queryString));
		
		return result;
	}
	
	
	
	public List<Map<String, Object>> queryForResultQ4( String queryType, String dataOption  ){
		this.template = new JdbcTemplate(this.dataSource);
		//String[] opt = queryType.split(",");
		String queryString = "select microarray_fact.exp from probe join gene_fact on probe.u_id=gene_fact.UIDD "
				+"join microarray_fact on microarray_fact.pb_id = probe.pb_id where gene_fact.go_id="+queryType
				+" and microarray_fact.s_id in (select s_id from clinical_fact where p_id in " 
				+"(select p_id from clinical_fact where ds_id in "
				+"(select ds_id from disease where ";
		
		switch (dataOption) {
			case "ALL":
			case "AML":
			case "Colon tumor":
			case "Breast tumor":
			case "Flu":
			case "Giloblastome":
				queryString += "disease.name = '"+dataOption+"')) "
						+"and s_id is not null)";
				break;
			default:
				System.out.println("In Default, value is :"+dataOption);
				queryString += "disease.name <> '"+dataOption.split("-")[1]+"')) "
						+"and s_id is not null)";
				break;
		}
		
		return template.queryForList(queryString);
	}
	
	
	
	public List<Map<String, Object>> queryForResultQ6( String queryType, String dataOption  ){
		this.template = new JdbcTemplate(this.dataSource);
		//String[] opt = queryType.split(",");
		String queryString = "select clinical_fact.p_id, microarray_fact.exp "
				+"from probe "
				+"join gene_fact on probe.u_id=gene_fact.UIDD "
				+"join microarray_fact on microarray_fact.pb_id=probe.pb_id "
				+"LEFT join clinical_fact on clinical_fact.s_id = microarray_fact.s_id "
				+"where gene_fact.go_id="+queryType
				+" and microarray_fact.s_id in (select s_id from clinical_fact where p_id in  "
				+"(select p_id from clinical_fact "
				 + "where ds_id in "
				  +  "(select ds_id from disease where disease.name = '"+dataOption+"') "
				  +") "
				+"and s_id is not null) order by microarray_fact.s_id ";
		
		
		return template.queryForList(queryString);
	}
	
	
	public List<Map<String, Object>> queryForResultQ7( String queryType, String dataOption  ){
		this.template = new JdbcTemplate(this.dataSource);
		//String[] opt = queryType.split(",");
		String queryString = "select distinct probe.u_id, microarray_fact.exp "
				+"from probe "
				+"join microarray_fact on microarray_fact.pb_id=probe.pb_id "
				+"join clinical_fact on clinical_fact.s_id = microarray_fact.s_id "
				+"where microarray_fact.s_id in (select s_id from clinical_fact where p_id in " 
				+"(select p_id from clinical_fact "
				  +"where ds_id in ";
		System.out.println("DataOption is :" +dataOption);
		switch (dataOption) {
			case "ALL":
			case "AML":
			case "Colon tumor":
			case "Breast tumor":
			case "Flu":
			case "Giloblastome":
				queryString += "(select ds_id from disease where disease.name = '"+dataOption+"') "
						  	+") "
							+"and s_id is not null) "
							+"order by probe.u_id";
				break;
			default:
				queryString += "(select ds_id from disease where disease.name <> '"+dataOption.split("-")[1]+"') "
					  	+") "
						+"and s_id is not null) "
						+"order by probe.u_id";
				break;
		}
		
		return template.queryForList(queryString);
	}
	
	
	public List<Map<String, Object>> queryForResultQ8( String queryType, String dataOption  ){
		this.template = new JdbcTemplate(this.dataSource);
		//String[] opt = queryType.split(",");
		String queryString = "select clinical_fact.p_id, microarray_fact.exp "
				+"from probe "
				+"join microarray_fact on microarray_fact.pb_id=probe.pb_id "
				+"join clinical_fact on clinical_fact.s_id = microarray_fact.s_id "
				+"where microarray_fact.s_id in (select s_id from clinical_fact where p_id in "
				+"(select p_id from clinical_fact "
				  +"where ds_id in ";
		
		System.out.println("DataOption is :" +queryType);
		
		switch (queryType) {
			case "ALL":
			case "AML":
			case "Colon tumor":
			case "Breast tumor":
			case "Flu":
			case "Giloblastome":
				queryString += "(select ds_id from disease where disease.name = '"+queryType+"') ";
				break;
			case "SAMPLE":
				queryString = "SELECT test1, test2, test3, test4, test5 from TEST_SAMPLES WHERE U_ID in ("+dataOption+")";
				break;
			default:
				queryString += "(select ds_id from disease where disease.name <> '"+queryType+"') ";
				break;
		}
		
		if( queryType != "SAMPLE"  )
		{
			queryString += ") "
				+"and s_id is not null) and probe.u_id in ("+dataOption+") "
				+"order by clinical_fact.p_id, probe.U_ID";
		}
		
		System.out.println(queryString);
		return template.queryForList(queryString);
	}

	
	public String queryForOptions(String queryType, String dataType){
		this.template = new JdbcTemplate(this.dataSource);
		System.out.println("queryType: "+queryType+" , dataType:"+dataType);
		String queryString = "";
		
		switch (queryType) {
			case "Q1-F1":
				if( dataType.equals("DD") )
					queryString = "SELECT DISTINCT DESCRIPTION FROM DISEASE";
				else if( dataType.equals("DT") )
					queryString = "SELECT DISTINCT TYPE FROM DISEASE";
				else
					queryString = "SELECT DISTINCT NAME FROM DISEASE";
				break;
			case "Q2":
				queryString = "SELECT DISTINCT DESCRIPTION FROM DISEASE";
			case "Q3":
				if( dataType.equals("DN") )
					queryString = "SELECT DISTINCT NAME FROM DISEASE";
				else if( dataType.equals("ClusterID") )
					queryString = "select DISTINCT C.C_ID from CLUSTERR C order by C.C_ID";
				break;
			case "Q4":
				if( dataType.equals("DN") )
					queryString = "SELECT DISTINCT NAME FROM DISEASE";
				else if( dataType.equals("GoID") )
					queryString = "SELECT DISTINCT g.GO_ID from GENE_FACT g order by g.GO_ID";
				break;
			case "Q5":
				queryString = "SELECT DISTINCT g.GO_ID from GENE_FACT g order by g.GO_ID";
				break;
			case "Q6":
				queryString = "SELECT DISTINCT g.GO_ID from GENE_FACT g order by g.GO_ID";
				break;
			case "Q7":
				queryString = "SELECT DISTINCT NAME FROM DISEASE";
				break;
			case "Q8":
				if( dataType.equalsIgnoreCase("DN") ){
					queryString = "SELECT DISTINCT NAME FROM DISEASE";
				}
				else{
					queryString = "select P_ID from PATIENT";
				}
			default:
				break;
				
		}
		
		return processDataForSelect(template.queryForList(queryString));
	}
	
	
	private String processDataForSelect(List<Map<String, Object>> data){
		StringBuilder builder = new StringBuilder();
		for( Map<String, Object> map : data ){
			for( Map.Entry<String, Object> entrySet : map.entrySet() ){
				builder.append(entrySet.getValue()+":");
			}
		}
		return builder.toString();
	}
	
	
	private String processTabularData(List<Map<String, Object>> data){
		StringBuilder builder = new StringBuilder();
		builder.append("<ul>");				
		for( Map<String, Object> map : data ){
			builder.append("<li><ul>");
			for( Map.Entry<String, Object> entrySet : map.entrySet() ){
				builder.append("<li>"+entrySet.getValue()+"</li>");
			}
			builder.append("</ul></li>");
		}
		builder.append("</ul>");
		return builder.toString();
	}
	
}
