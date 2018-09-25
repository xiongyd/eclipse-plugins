package com.plugindev.addressbook;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.plugindev.addressbook.models.AddressManager;

public class Activator implements BundleActivator {

	//²Î¿¼ http://wiki.eclipse.org/Eclipse_Plug-in_Development_FAQ
	private static Activator instance;
	
	public static final String PLUGIN_ID = "com.plugindev.addressbook";
	
	private static BundleContext context;

	public static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		instance = this;
		Activator.context = bundleContext;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		instance = null;
		AddressManager.getManager().saveAddresses();
		Activator.context = null;
	}
	
	public Bundle getBundle() {
		return context.getBundle();
	}
	
	public static Activator getDefault() {
		return instance;
	}

	public static ImageDescriptor getImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
