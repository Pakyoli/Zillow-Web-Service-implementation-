package project;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;

public class Result {
    
    NumberFormat formatter = NumberFormat.getCurrencyInstance();
    
    private double latitude=0;
    private double longitude=0; 
    private int valueChanged=0;
    private int lotSize=0;
    private int finishedSize=0;
    private int zip=0;
    private int yearBuilt=0;
    private String low;
    private String high;
    private String street;
    private String city;
    private String state;
    private String zpid;
    private String amount;
    private String useCode;
    private String regionChart;
    private String lastSoldDate;
    private String lastUpdate;   
    private String bathroom;
    private String totalRoom;
    private String lastSoldPrice;   
    private String bedroom;
    private String status;
    private String type;
    private String description;
    private String price;
    private String highK;
    private String lowK;

    
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUseCode() {
        return useCode;
    }

    public void setUseCode(String useCode) {
        this.useCode = useCode;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude.doubleValue();
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude.doubleValue();        
    }

    public int getLotSize() {
        return lotSize;
    }

    public void setLotSize(String lotSize) {
        this.lotSize = Integer.parseInt(lotSize);
    }

    public int getFinishedSize() {
        return finishedSize;
    }

    public void setFinishedSize(String finishedSize) {
        this.finishedSize = Integer.parseInt(finishedSize);
    }

    public String getBathroom() {
        return bathroom;
    }

    public void setBathroom(String bathroom) {
        this.bathroom = bathroom;
    }

    public String getTotalRoom() {
        return totalRoom;
    }

    public void setTotalRoom(String totalRoom) {
        this.totalRoom = totalRoom;
    }

    public String getBedroom() {
        return bedroom;
    }

    public void setBedroom(String bedroom) {
        this.bedroom = bedroom;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = formatter.format(amount.intValue());
    }

    public String getLastSoldPrice() {
        return lastSoldPrice;
    }

    public void setLastSoldPrice(BigInteger lastSoldPrice) {
        this.lastSoldPrice = formatter.format(lastSoldPrice.intValue());
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = Integer.parseInt(zip);
    }

    public int getYearBuilt() {
        return yearBuilt;
    }

    public void setYearBuilt(String yearBuilt) {
        this.yearBuilt = Integer.parseInt(yearBuilt);     
    }

    public String getZpid() {
        return zpid;
    }

    public void setZpid(String zpid) {
        this.zpid = zpid;
    }

    public String getRegionChart() {
        return regionChart;
    }

    public void setRegionChart(String regionChart) {
        this.regionChart = regionChart;
    }

    public String getLastSoldDate() {
        return lastSoldDate;
    }

    public void setLastSoldDate(String lastSoldDate){
        this.lastSoldDate = lastSoldDate;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = formatter.format(Integer.parseInt(price));
    }

    public int getValueChanged() {
        return valueChanged;
    }

    public void setValueChanged(String valueChanged) {
        this.valueChanged = Integer.parseInt(valueChanged);
    }

    public String getHighK() {
        return formatValue(high);
    }

    public void setHighK(String highK) {
        this.highK = highK;
    }

    public String getLowK() {
        return formatValue(low);
    }

    public void setLowK(String lowK) {
        this.lowK = lowK;
    }

    private String formatValue(String v) {
        if(v.length()==5){
            v = v.substring(0, 2);
        }
        if(v.length()==6){
            v = v.substring(0, 3);
        }
        if(v.length()==7){
            v = v.charAt(0)+","+v.substring(1, 4);
        }
        if(v.length()==8){
            v = v.substring(0, 2)+","+v.substring(2, 5);
        }
        if(v.length()>8){
            return v;
        }
        return v+"K";
    }

    
    
    
}
