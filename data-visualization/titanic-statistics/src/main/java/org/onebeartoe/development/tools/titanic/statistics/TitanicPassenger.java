
package org.onebeartoe.development.tools.titanic.statistics;

import com.opencsv.bean.CsvBindByName;

/**
 *
 */
public class TitanicPassenger 
{
    @CsvBindByName(column = "pclass")
    public String pClass;
    
    @CsvBindByName(column = "survived")
    public String survived;
    
    @CsvBindByName
    public String name;


    @CsvBindByName
    public String sex;
    
    
    @CsvBindByName
    public String age;
    
    
    @CsvBindByName
    public String sibsp;

    
    @CsvBindByName
    public String parch;

    
    @CsvBindByName
    public String ticket;

    
    @CsvBindByName
    public String fare;

    
    @CsvBindByName
    public String cabin;

    
    @CsvBindByName
    public String embarked;

    
    @CsvBindByName
    public String boat;

    
    @CsvBindByName
    public String body;

    
    
    @CsvBindByName(column = "home.dest")
    public String homeDest;  
    
    public String getHomeDest()
    {
        return homeDest;
    }
}
