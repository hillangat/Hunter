package com.techmaster.hunter.dao.types;

import java.util.List;

import com.techmaster.hunter.obj.beans.ServiceProvider;

public interface ServiceProviderDao {
	
	public void insertServiceProvider(ServiceProvider serviceProvider);
	public void insertServiceProviders(List<ServiceProvider> serviceProviders);
	public ServiceProvider getServiceProviderById(Long id);
	public ServiceProvider getServiceProviderByName(String name);
	public List<ServiceProvider> getAllServiceProviders();
	public void updateServiceProvider(ServiceProvider update);

}
