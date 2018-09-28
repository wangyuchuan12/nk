package com.ifrabbit.nk.context;

import com.ifrabbit.nk.usercenter.domain.Staff;

public abstract class UserContext {

	private static ThreadLocal<Staff> staffs = new ThreadLocal<>();

	public static void set(Staff staff) {
		staffs.set(staff);
	}

	public static Staff get() {
		return staffs.get();
	}

	public static void clear() {
		staffs.set(null);
		staffs.remove();
	}

}
