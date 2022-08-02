package com.bwdesigngroup.ignition.configmanager.gateway;

import com.bwdesigngroup.ignition.configmanager.gateway.scripting.GatewayScriptModule;
import com.inductiveautomation.ignition.common.licensing.LicenseState;
import com.inductiveautomation.ignition.common.script.ScriptManager;
import com.inductiveautomation.ignition.common.script.hints.PropertiesFileDocProvider;
import com.inductiveautomation.ignition.gateway.clientcomm.ClientReqSession;
import com.inductiveautomation.ignition.gateway.model.AbstractGatewayModuleHook;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;

/**
 * Class which is instantiated by the Ignition platform when the module is loaded in the gateway scope.
 */
public class GatewayHook extends AbstractGatewayModuleHook {

    private static GatewayContext gatewayContext;
    private final GatewayScriptModule scriptModule = new GatewayScriptModule();
    
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

        manager.addScriptModule(
                "system.config",
                scriptModule,
                new PropertiesFileDocProvider());
    }

    @Override
    public Object getRPCHandler(ClientReqSession session, String projectName) {
        return scriptModule;
    }

    @Override
    public void shutdown() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void startup(LicenseState arg0) {
        // TODO Auto-generated method stub
        
    }

    public static GatewayContext getGatewayContext() { 
        return gatewayContext; 
    }

}

