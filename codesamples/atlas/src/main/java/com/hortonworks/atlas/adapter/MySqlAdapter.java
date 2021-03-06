/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hortonworks.atlas.adapter;

import java.util.HashMap;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class MySqlAdapter {


	public String host = "";
	public String DBname = "";
	public String user = "";
	public String password = "";
	
    public HashMap<String, Table> hsh  = new HashMap<String,Table>();
    
    public HashMap<String, Table> getTableMap(){
    	
    	return this.hsh;
    }
	
	/**
	 * Constructor - Mysql
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 * 
	 */
	public MySqlAdapter(String hst, String DBnm, String user, String password) throws ClassNotFoundException, SQLException{
		
		Class.forName("com.mysql.jdbc.Driver");
		
		this.host = hst;
		this.DBname =  DBnm;
		this.user =  user;
		this.password = password;
		 
		scrubDB();
					 
	}
	
	
	/**
	 * This method will scrub the database and get all the tables and columns and 
	 * create a hashmap by tablename and the table objects
	 * @throws SQLException
	 */
	private void scrubDB() throws SQLException{
		
		Connection connection = null;
		Column col = null;
		Table t =  null;	
		DB db = null;
		
		System.out.println("jdbc:mysql://" +host +":3306/" + this.DBname+this.user + this.password);
		
		connection = DriverManager
				.getConnection("jdbc:mysql://" +host +":3306/" + this.DBname,this.user, this.password);
				
				java.sql.DatabaseMetaData md = connection.getMetaData();
				
			
				java.sql.ResultSet res = md.getTables(null, null, null, new String[]{"TABLE"});
				
		 
				while(res.next()){
					
					
					java.sql.ResultSetMetaData rsmd = res.getMetaData();
					
					
					 System.out.println(
					            "   "+res.getString("TABLE_CAT") 
					          
					           + ", "+res.getString("TABLE_NAME")
					           + ", "+res.getString("TABLE_TYPE")
					           + ", "+res.getString("REMARKS")); 
					
					 
					 t = new Table();
					 t.setTable_name(res.getString("TABLE_NAME"));
					 t.setTable_type(res.getString("TABLE_TYPE"));
					 t.setRemarks(res.getString("REMARKS"));
					 DB dbins = new DB();
					 dbins.setName(this.DBname);
					 t.setDb(dbins);
					 
					 java.sql.ResultSet res1 = md.getColumns(null, null, res.getString("TABLE_NAME"), null);
					 
					 while(res1.next()){
						 System.out.println(
						            "   "+res1.getString("TABLE_NAME") 
						           + ", "+res1.getString("COLUMN_NAME")
						           + ", "+res1.getString("TYPE_NAME")
						           +  ", "+res1.getString("COLUMN_SIZE")
						           +  ", "+res1.getString("NULLABLE")
						           +  ", "+res1.getString("REMARKS"));
						         
						 
					col = new Column();
					col.setColumn_name(res1.getString("COLUMN_NAME"));
					col.setColumn_type(res1.getString("TYPE_NAME"));
					col.setColumn_remarks(res1.getString("REMARKS"));
					col.setColumn_size(res1.getString("COLUMN_SIZE"));
					
					t.addColumn(col);
					
					 }
					 
					 hsh.put(res.getString("TABLE_NAME"), t);
					 
				}
	
	
	}
	
	
	/**
	 * Create a new
	 */
	
	

}
