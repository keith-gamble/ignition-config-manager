package com.bwdesigngroup.ignition.configmanager.client;

import com.bwdesigngroup.ignition.configmanager.client.scripting.ClientScriptModule;
import com.inductiveautomation.ignition.common.script.ScriptManager;
import com.inductiveautomation.ignition.common.script.hints.PropertiesFileDocProvider;
import com.inductiveautomation.vision.api.client.AbstractClientModuleHook;

/**
 *
 * @author Keith Gamble
 */
public class ClientHook extends AbstractClientModuleHook {
    
    @Override
    public void initializeScriptManager(ScriptManager manager) {
        super.initializeScriptManager(manager);

        manager.addScriptModule(
            "system.config",
            new ClientScriptModule(),
            new PropertiesFileDocProvider()
        );
    }
}
