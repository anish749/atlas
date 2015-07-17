
# Technical Details

Apache Hadoop consists of below projects for Data Inestion 

* Apache Sqoop - 

Apache Sqoop is hadoop project for ingesting data from Relation Databases into Hadoop. It has been in the Hadoop Ecosystem for a while and well proven in the industry.

* Apache Flume - 
Apache Flumen is a  good utility for ingesting data from various sources into hadoop.

Feed and Data Lineage

* Apache Falcon -
Falcon is a feed processing and feed management system aimed at making it easier for end consumers to onboard their feed processing and feed management on hadoop clusters. 

Falcon needs a workflow engine. It uses oozie by default.

* Apache Atlas*

Apache Atlas is a scalable and extensible set of core foundational governance services that enables enterprises to effectively and efficiently meet their compliance requirements within Hadoop and allows integration with the complete enterprise data ecosystem.

## The Demo project

To demonstrate a simple scenario we are going to setup system with the following.

* Source System.

This is a mysql database. 

* Destination System
 
We have HDP Cluster. For our tutorial we are going to use the HDP 2.3 Sandbox

Design

In the Source System we are going to have an online system that is storing Truck Drivers Information System. The Truck Driver Information System, tracks the Truck Companies, employees, status, license, route and hours logged.

Following are the tables in the Source System:-

#### Organization

<table>
 <tr>
  <td>Cloumn Name</td>
  <td>Cloumn Type</td>
  <td>Not Null </td>
  <td> Primary Key</td>
 </tr>
 <tr>
  <td>OraganizationId</td>
  <td>Varchar(100)</td>
  <td>Y</td>
  <td>Y</td>
 </tr>
  <tr>
  <td>OraganizationName</td>
  <td>Varchar(1000)</td>
  <td>Y</td>
  <td>N</td>
 </tr>
  <tr>
  <td>Industry</td>
  <td>Varchar(100)</td>
  <td>N</td>
  <td>N</td>
 </tr>
 <tr>
  <td>Publicy_Traded</td>
  <td>Boolean</td>
  <td>Y</td>
  <td>Y</td>
 </tr>
</table>

#### Employee


<table>
 <tr>
  <td>Cloumn Name</td>
  <td>Cloumn Type</td>
  <td>Not Null </td>
  <td> Primary Key</td>
 </tr>
 <tr>
  <td>Employee_ID</td>
  <td>Varchar(100)</td>
  <td>Y</td>
  <td>Y</td>
 </tr>
  <tr>
  <td>Emplpyee_Name</td>
  <td>Varchar(1000)</td>
  <td>Y</td>
  <td>N</td>
 </tr>
  <tr>
  <td>Email</td>
  <td>Varchar(100)</td>
  <td>Y</td>
  <td>N</td>
 </tr>
 <tr>
  <td>Designation</td>
  <td>Varchar(200)</td>
  <td>Y</td>
  <td>N</td>
 </tr>
  <tr>
  <td>OragnizationID</td>
  <td>Varchar(100)</td>
  <td>Y</td>
  <td>N</td>
 </tr>
  <tr>
  <td>JobType</td>
  <td>Varchar(100)</td>
  <td>Y</td>
  <td>N</td>
 </tr>
</table>


#### DRIVERS

<table>
 <tr>
  <td>Cloumn Name</td>
  <td>Cloumn Type</td>
  <td>Not Null </td>
  <td> Primary Key</td>
 </tr>
 <tr>
  <td>DRIVER_ID</td>
  <td>Varchar(200)</td>
  <td>Y</td>
  <td>Y</td>
 </tr>
  <tr>
  <td>DRIVER_NAME</td>
  <td>Varchar(1000)</td>
  <td>Y</td>
  <td>N</td>
 </tr>
  <tr>
  <td>CERTIFIED</td>
  <td>Varchar(100)</td>
  <td>Y</td>
  <td>N</td>
 </tr>
 <tr>
  <td>WAGE_PLAN</td>
  <td>Varchar(200)</td>
  <td>Y</td>
  <td>N</td>
 </tr>
</table>

## Create Source System

#### Install Mysql

* Create a CentOS 6.5 Virtula Machine
* Install mysql server
* wget http://dev.mysql.com/get/mysql-community-release-el6-5.noarch.rpm/from/http://repo.mysql.com/
* sudo yum localinstall mysql-community-release-el6-*.noarch.rpm
* sudo yum install mysql-community-server
* sudo service mysqld start
* sudo chkconfig mysqld on
* chkconfig --list mysqld

[Refer to the MySQL Install docs](https://dev.mysql.com/doc/refman/5.6/en/linux-installation-yum-repo.html)

Next login as 

    mysql -u root -h <hostname>

#### Create Table and Load the Data
Execute the script in the github repo 
    MySQLSourceSystem.ddl

This will create all the needed tables.

Load the Drivers Data. Copy the drivers.csv file over from the repo

    LOAD DATA LOCAL INFILE '<dir>/drivers.csv' into table DRIVERS FIELDS TERMINATED BY "," LINES TERMINATEd BY '\n' (DRIVER_ID, DRIVER_NAME,CERTIFIED, WAGE_PLAN);
    

###### Now you should have the Source System Ready!

















