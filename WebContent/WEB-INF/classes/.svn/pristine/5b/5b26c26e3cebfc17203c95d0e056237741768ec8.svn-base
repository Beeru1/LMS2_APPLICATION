package com.ibm.lms.common;

import java.util.HashSet;
import java.util.Set;

public class LMSStatusCodes {
	
	public static final int LEAD_CAPTURED = 100;
	public static final int LEAD_OPEN = 200;
	public static final int DIALER_CON_FAIL = 210;
	public static final int QUALIFICATION = 300;
	public static final int QUALIFIED = 305;
	public static final int FEASIBILITY = 310;
	public static final int WIRED = 311;
	public static final int UNWIRED = 315;
	public static final int UNWIRED_NO_TAG = 317;
	public static final int INFO_INADEQUATE = 320;
	public static final int LEAD_CALLBACK_DONE = 350;
	public static final int ASSIGNED = 400;
	public static final int REASSIGNED = 401;
	public static final int WIRED_PARTIAL = 312;
	public static final int UNWIRED_NO_COVERAGE = 316;
	public static final int FEASIBILITY_INFO_DONE = 321;
	public static final int WON = 500;
	public static final int WON_CHANNEL_PARTNER = 1006;
	public static final int WON_CLOSE_LOOPING = 1008;
	public static final int WON_SMS = 1010;
	public static final int LOST = 600;
	public static final int LOST_CHANNEL_PARTNER = 1007;
	public static final int LOST_CLOSE_LOOPING = 1009;
	public static final int LOST_SMS = 1011;
	public static final int MET = 402;
	public static final int NOTMET = 403;
	public static final int YET_TO_GET_FEEDBACK = 404;
	public static final int UNQUALIFIED = 700;
	public static final int UNQUALIFIED_INVALID = 710;
	public static final int LEAD_CONTACTBACK = 800;
	public static final int DIALER_SECOND_CALL = 370;
	public static final int FOLLOW_UP_DIALER_CALL = 380;
	public static final int ESCALATION = 900; //aman
	public static final int ESCALATION1 = 901;
	public static final int ESCALATION2= 902;
	public static final int ESCALATION3 = 903;
	public static final int ESCALATION4 = 904;
	public static final int FEASIBILITY5 = 800; 
	public static final int FEASIBILITY1 = 801;
	public static final int FEASIBILITY2 = 802;
	public static final int FEASIBILITY3 = 803;
	public static final int FEASIBILITY4 = 804;//aman
	
	public static Set<Integer> getAllowedFeasibilitySubStatusCodes()
	{
		Set <Integer>allowedFeasibilitySubStatusSet = new HashSet<Integer>();
		allowedFeasibilitySubStatusSet.add(WIRED); 
		allowedFeasibilitySubStatusSet.add(UNWIRED); 
		allowedFeasibilitySubStatusSet.add(WIRED_PARTIAL); 
		allowedFeasibilitySubStatusSet.add(UNWIRED_NO_COVERAGE); 
		allowedFeasibilitySubStatusSet.add(UNWIRED_NO_TAG); 
		allowedFeasibilitySubStatusSet.add(INFO_INADEQUATE);
		allowedFeasibilitySubStatusSet.add(FEASIBILITY_INFO_DONE); 
		return allowedFeasibilitySubStatusSet;
	}
	public static Set<Integer> getAllowedFeasibilityStatusCodes()
	{
		Set <Integer>allowedFeasibilityStatusSet = new HashSet<Integer>();
		allowedFeasibilityStatusSet.add(WIRED);
		allowedFeasibilityStatusSet.add(UNWIRED); 
		allowedFeasibilityStatusSet.add(INFO_INADEQUATE);
		allowedFeasibilityStatusSet.add(FEASIBILITY_INFO_DONE); 
		return allowedFeasibilityStatusSet;
	}
}
