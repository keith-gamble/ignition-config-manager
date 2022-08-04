package com.bwdesigngroup.ignition.configmanager.gateway;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyStringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bwdesigngroup.ignition.configmanager.gateway.scripting.GatewayScriptModule;
import com.inductiveautomation.ignition.common.licensing.LicenseState;
import com.inductiveautomation.ignition.common.script.JythonExecException;
import com.inductiveautomation.ignition.common.script.ScriptManager;
import com.inductiveautomation.ignition.common.script.hints.PropertiesFileDocProvider;
import com.inductiveautomation.ignition.gateway.clientcomm.ClientReqSession;
import com.inductiveautomation.ignition.gateway.model.AbstractGatewayModuleHook;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;


/**
 * Class which is instantiated by the Ignition platform when the module is loaded in the gateway scope.
 */
public class GatewayHook extends AbstractGatewayModuleHook {

    public static HashMap<String,WeakReference<GatewayScriptModule>> scriptModules = new HashMap<String,WeakReference<GatewayScriptModule>>();

    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static GatewayContext gatewayContext;
    
    /**
     * Called to before startup. This is the chance for the module to add its extension points and update persistent
     * records and schemas. None of the managers will be started up at this point, but the extension point managers will
     * accept extension point types.
     */
    @Override
    public void setup(GatewayContext context) {
        gatewayContext = context;
    }

    /**
     * @return {@code true} if this is a "free" module, i.e. it does not participate in the licensing system. This is
     * equivalent to the now defunct FreeModule attribute that could be specified in module.xml.
     */
    @Override
    public boolean isFreeModule() {
        return true;
    }

    @Override
    public void initializeScriptManager(ScriptManager manager) {
        super.initializeScriptManager(manager);

        PyStringMap localsMap = manager.createLocalsMap();
        PyObject getProjectName = localsMap.get((new PyString("system"))).__getattr__("util").__getattr__("getProjectName");
        
        String projectName = null;
        try {
            projectName = manager.runFunction(getProjectName).toString();
        } catch (JythonExecException e) {
            logger.error("Error getting project name", e);
        }

        if (projectName != null) {            
            WeakReference<GatewayScriptModule> scriptModule = new WeakReference<GatewayScriptModule>(new GatewayScriptModule(projectName));
            scriptModules.put(projectName.toString(), scriptModule);

            manager.addScriptModule(
                "system.config",
                scriptModule.get(),
                new PropertiesFileDocProvider());
        }
    }

    @Override
    public Object getRPCHandler(ClientReqSession session, String projectName) {

        // If there is not a script module for the project, create one and add it to the scriptModules map
        if (scriptModules.get(projectName) == null) {
            WeakReference<GatewayScriptModule> scriptModule = new WeakReference<GatewayScriptModule>(new GatewayScriptModule(projectName));
            scriptModules.put(projectName.toString(), scriptModule);
        }

        return scriptModules.get(projectName).get();
    }

    @Override
    public void shutdown() {
        logger.info("Shutting down configmanager module");
        
    }

    @Override
    public void startup(LicenseState arg0) {
        logger.info("Starting up configmanager module");
        
    }

    public static GatewayContext getGatewayContext() { 
        return gatewayContext; 
    }

}

