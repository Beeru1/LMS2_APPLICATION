package com.ibm.lms.actions;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.ibm.lms.dto.SmsObject;
import com.ibm.lms.dto.SmsState;
import com.ibm.lms.engine.util.ServerPropertyReader;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.services.MasterService;
import com.ibm.lms.services.impl.MasterServiceImpl;

public class RefreshSMSMaps extends TimerTask {
	
	private static Logger logger = Logger.getLogger(RefreshSMSMaps.class.getName());
    

	@Override
	public void run() {
		logger.info("Refresh Map started--");
		SmsObject smsObj=new SmsObject();
		SmsState smsState=new SmsState();
		// TODO Auto-generated method stub
		Hashtable<SmsObject,SmsState> smsMap=ReceiveSMS.smsMap;
		MasterService masterSrvc=new MasterServiceImpl();
		logger.info(smsMap.isEmpty());
		Set<SmsObject>entrySet=smsMap.keySet();
		Set<SmsObject> removeObject=new HashSet<SmsObject>();
		Iterator<SmsObject> itr=entrySet.iterator();
		Integer sessionTime=30;
		try {
			sessionTime = Integer.parseInt(masterSrvc.getParameterName("SESSION_TIME_OUT"));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("CONF_TIME_OUT:"+sessionTime);
		while(itr.hasNext()){
			smsObj=itr.next();
			smsState=smsMap.get(smsObj);
			Long currentTime=System.currentTimeMillis();
			Long diff=currentTime-smsState.getLasMsgtime();
			if(diff>0){
				Long timeDiffinSec=diff/1000;
				if(timeDiffinSec>60){
					Long timeout=timeDiffinSec/60;
					//configuration;
					logger.info("CALC_TIME_OUT:"+timeout);
					if(timeout > sessionTime){
						removeObject.add(smsObj);
						
					}
				}
			}
		}
		itr=removeObject.iterator();
		while(itr.hasNext()){
			smsObj=itr.next();
			smsMap.remove(smsObj);
		}
		logger.info("Refresh Map ended");

	}

}
