package com.techmaster.hunter.obj.beans;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.techmaster.hunter.dao.impl.ServiceProviderDaoImpl;
import com.techmaster.hunter.util.HunterLogFactory;


@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ServiceProvider {
	
	private Long providerId;
	private String providerName;
	private float cstPrAudMsg;
	private float cstPrTxtMsg;
	
	public static ServiceProvider getHunterDefaultServiceProvider() {
		ServiceProvider dfltSP = new ServiceProviderDaoImpl().getServiceProviderById(1L); 
		HunterLogFactory.getLog(ServiceProvider.class).debug("Returning default provider >> " + dfltSP);
		return dfltSP;
	}

	public ServiceProvider() {
		super();
	}

	public Long getProviderId() {
		return providerId;
	}

	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}


	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public float getCstPrAudMsg() {
		return cstPrAudMsg;
	}

	public void setCstPrAudMsg(float cstPrAudMsg) {
		this.cstPrAudMsg = cstPrAudMsg;
	}

	public float getCstPrTxtMsg() {
		return cstPrTxtMsg;
	}

	public void setCstPrTxtMsg(float cstPrTxtMsg) {
		this.cstPrTxtMsg = cstPrTxtMsg;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(cstPrAudMsg);
		result = prime * result + Float.floatToIntBits(cstPrTxtMsg);
		result = prime * result
				+ ((providerId == null) ? 0 : providerId.hashCode());
		result = prime * result
				+ ((providerName == null) ? 0 : providerName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServiceProvider other = (ServiceProvider) obj;
		if (Float.floatToIntBits(cstPrAudMsg) != Float
				.floatToIntBits(other.cstPrAudMsg))
			return false;
		if (Float.floatToIntBits(cstPrTxtMsg) != Float
				.floatToIntBits(other.cstPrTxtMsg))
			return false;
		if (providerId == null) {
			if (other.providerId != null)
				return false;
		} else if (!providerId.equals(other.providerId))
			return false;
		if (providerName == null) {
			if (other.providerName != null)
				return false;
		} else if (!providerName.equals(other.providerName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ServiceProvider [providerId=" + providerId + ", providerName="
				+ providerName + ", cstPrAudMsg=" + cstPrAudMsg
				+ ", cstPrTxtMsg=" + cstPrTxtMsg + "]";
	}


	


	
	

}
