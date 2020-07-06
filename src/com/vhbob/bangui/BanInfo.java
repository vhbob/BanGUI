package com.vhbob.bangui;

import java.util.Date;

public class BanInfo {

	private String target, reason, duration;
	private Date end;
	private boolean silent;

	// Constructors
	public BanInfo() {

	}

	public BanInfo(String target, boolean silent) {
		this.setTarget(target);
		this.setSilent(silent);
	}

	// Setter and getters
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public boolean isSilent() {
		return silent;
	}

	public void setSilent(boolean silent) {
		this.silent = silent;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

}
