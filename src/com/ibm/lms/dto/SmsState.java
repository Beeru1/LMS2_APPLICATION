package com.ibm.lms.dto;

public class SmsState {

	private Long lasMsgtime;
	private String state;
	 
	public Long getLasMsgtime() {
		return lasMsgtime;
	}
	public void setLasMsgtime(Long lasMsgtime) {
		this.lasMsgtime = lasMsgtime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	@Override
	public boolean equals(Object stateObj){
		
		if(this==stateObj)
			return true;
		if(stateObj==null)
			return false;
		else if(this!=null && !(stateObj instanceof SmsState))
			return false;
		else if(stateObj!=null && (stateObj instanceof SmsState)){
			SmsState smsState= (SmsState) stateObj;
			return (this.getLasMsgtime().equals(smsState.getLasMsgtime()) && this.getState().equals(smsState.getState()));
		}
		
		return false;
		
	}
	
	@Override
	
	public int hashCode(){
		int factor=47;
		int result=0;
		result=result+factor*this.getLasMsgtime().hashCode();
		result=result+factor*this.getState().hashCode();
		
		return result;
		
	}
	 
}
