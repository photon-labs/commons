package com.photon.phresco.commons.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SelectedFeature {

	private String dispName;
	private String dispValue;
	private String versionID;
	private String type;
	
	public String getDispName() {
		return dispName;
	}
	public void setDispName(String dispName) {
		this.dispName = dispName;
	}
	public String getDispValue() {
		return dispValue;
	}
	public void setDispValue(String dispValue) {
		this.dispValue = dispValue;
	}
	public String getVersionID() {
		return versionID;
	}
	public void setVersionID(String versionID) {
		this.versionID = versionID;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
    public String toString() {
        return new ToStringBuilder(this,
                ToStringStyle.DEFAULT_STYLE)
                .append(super.toString())
                .append("dispName", getDispName())
                .append("dispValue", getDispValue())
                .append("versionID", getVersionID())
                .append("type", getType())
                .toString();
    }
}