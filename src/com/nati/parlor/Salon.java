package com.nati.parlor;

import android.os.Parcel;
import android.os.Parcelable;

public class Salon implements Parcelable{

	private int salon_id;
	private String salonName;
	private String postcode;
	private String mobileNum;
	public Salon(int salon_id, String salonName, String postcode,
			String mobileNum) {
		super();
		this.salon_id = salon_id;
		this.salonName = salonName;
		this.postcode = postcode;
		this.mobileNum = mobileNum;
	}
	public Salon(Parcel in) {
		readFromParcel(in);
	}
	
	private void readFromParcel(Parcel in) {
		 
		// We just need to read back each
		// field in the order that it was
		// written to the parcel
		salon_id = in.readInt();
		salonName=in.readString();
		mobileNum=in.readString();
		postcode=in.readString();
		
		
	}
	public int getSalon_id() {
		return salon_id;
	}
	public void setSalon_id(int salon_id) {
		this.salon_id = salon_id;
	}
	public String getSalonName() {
		return salonName;
	}
	public void setSalonName(String salonName) {
		this.salonName = salonName;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getMobileNum() {
		return mobileNum;
	}
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(salon_id);
		dest.writeString(salonName);
		dest.writeString(mobileNum);
		dest.writeString(postcode);
	}
	
	 public static final Parcelable.Creator CREATOR =
		    	new Parcelable.Creator() {
		            public Salon createFromParcel(Parcel in) {
		                return new Salon(in);
		            }
		 
		            public Salon[] newArray(int size) {
		                return new Salon[size];
		            }
		        };
	
}
