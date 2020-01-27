package com.example.Toll_Plaza;

public class tollinfo {

    String Name;
    String vehical_type;
    String vehical_number;
    String timestamp;
    float Amount;



    public String toString(){
        return "Name :" + Name + ",\nVehical Type : "+ vehical_type + ",\nVehical Number : "+ vehical_number + ",\nTime : "+ timestamp + ",\nAmount : " + Amount;
    }

    public tollinfo() {
       /* this.Name= "sunil";
        this.vehical_type = "car";
        this.vehical_number = "MH-2045";
        this.timestamp = "2020-01-18 14:22:50";
        this.Amount = 20;*/

    }
  /* public tollinfo(String Name,String vehical_type,String vehical_number,String timestamp,float Amount) {
        this.Name= Name;
        this.vehical_type = vehical_type;
        this.vehical_number = vehical_number;
        this.timestamp = timestamp;
        this.Amount = Amount;

    }*/

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getVehical_type() {
        return vehical_type;
    }

    public void setVehical_type(String vehical_type) {
        this.vehical_type = vehical_type;
    }

    public String getVehical_number() {
        return vehical_number;
    }

    public void setVehical_number(String vehical_number) {
        this.vehical_number = vehical_number;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public float getAmount() {
        return Amount;
    }

    public void setAmount(float amount) {
        Amount = amount;
    }
}
