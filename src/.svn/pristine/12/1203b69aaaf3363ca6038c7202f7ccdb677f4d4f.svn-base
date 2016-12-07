/*
 * Created on Oct 11, 2007
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 */
package com.ibm.lms.common;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class is a form of Exception that indicates conditions that a
 * application might want to catch.
 *
 * @author buchi ram reddy
 */

public class PropertyReader {

	private static ResourceBundle bundle = null;
	private static ResourceBundle appBundle = null;
	private static ResourceBundle gsdBundle = null;
	/**
	 * Property Reader
	 *
	 */
	public PropertyReader() {
	}

	static {
		loadConfiguration();
	}
	/**
	 * Get
	 *
	 * @param param_s_key
	 * @return
	 */
	public static String getValue(String param_s_key) {
		if (param_s_key == null || param_s_key.trim().length() <= 0) {
			return null;
		};
		return bundle.getString(param_s_key.trim());
	}
	public static String getAppValue(String param_s_key) {
		if (param_s_key == null || param_s_key.trim().length() <= 0) {
			return null;
		};
		return appBundle.getString(param_s_key.trim());
	}
	public static String getGsdValue(String param_s_key) {
		if (param_s_key == null || param_s_key.trim().length() <= 0) {
			return null;
		};
		return gsdBundle.getString(param_s_key.trim());
	}
	private static void loadConfiguration() {
		bundle =
			ResourceBundle.getBundle("ServerProperties", Locale.ENGLISH);
		appBundle =
			ResourceBundle.getBundle("ApplicationResources", Locale.ENGLISH);
		gsdBundle=ResourceBundle.getBundle("GSD", Locale.ENGLISH);
	}

}
