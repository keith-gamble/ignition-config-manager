package com.bwdesigngroup.ignition.configmanager.designer;

import javax.swing.Icon;

import com.bwdesigngroup.ignition.configmanager.client.scripting.ClientScriptModule;
import com.bwdesigngroup.ignition.configmanager.common.resources.GatewayConfigResource;
import com.bwdesigngroup.ignition.configmanager.common.resources.ProjectConfigResource;
import com.bwdesigngroup.ignition.configmanager.designer.workspaces.GatewayConfigWorkspace;
import com.bwdesigngroup.ignition.configmanager.designer.workspaces.ProjectConfigWorkspace;
import com.inductiveautomation.ignition.client.icons.VectorIcons;
import com.inductiveautomation.ignition.common.BundleUtil;
import com.inductiveautomation.ignition.common.licensing.LicenseState;
import com.inductiveautomation.ignition.common.project.resource.ProjectResourceId;
import com.inductiveautomation.ignition.common.script.ScriptManager;
import com.inductiveautomation.ignition.common.script.hints.PropertiesFileDocProvider;
import com.inductiveautomation.ignition.designer.model.AbstractDesignerModuleHook;
import com.inductiveautomation.ignition.designer.model.DesignerContext;


/**
 * This is the Designer-scope module hook.  The minimal implementation contains a startup method.
 */

public class DesignerHook extends AbstractDesignerModuleHook {

    private DesignerContext context;

    @Override
    public void startup(DesignerContext context, LicenseState activationState) throws Exception {
        this.context = context;

        BundleUtil.get().addBundle("ConfigManager", DesignerHook.class.getClassLoader(), "ConfigManager");

        // Intialize the root navigation node in the project browser
        ConfigManagerNavTreeNode configManagerNavTreeNode = new ConfigManagerNavTreeNode();
        this.context.getProjectBrowserRoot().addChild(configManagerNavTreeNode);

        // Initialize the project subnode and add it to the root node
        ProjectConfigWorkspace projectWorkspace =  new ProjectConfigWorkspace(this.context, configManagerNavTreeNode);
        GatewayConfigWorkspace gatewayWorkspace = new GatewayConfigWorkspace(this.context, configManagerNavTreeNode);
        this.context.registerResourceWorkspace(projectWorkspace);
        this.context.registerResourceWorkspace(gatewayWorkspace);

    }

    @Override
    public String getResourceCategoryKey(ProjectResourceId id) {
        if (id.getResourceType().equals(GatewayConfigResource.RESOURCE_TYPE)) {
            return "Gateway Config";
        } else if (id.getResourceType().equals(ProjectConfigResource.RESOURCE_TYPE)) {
            return "Project Config";
        } else {
            return super.getResourceCategoryKey(id);
        }
    }

    @Override
    public String getResourceDisplayName(ProjectResourceId id) {
        if (id.getResourceType().equals(GatewayConfigResource.RESOURCE_TYPE) || id.getResourceType().equals(ProjectConfigResource.RESOURCE_TYPE)) {
            return "Config";
        } else {
            return super.getResourceDisplayName(id);
        }
    }

    @Override
    public Icon getResourceIcon(ProjectResourceId id) {
        if (id.getResourceType().equals(GatewayConfigResource.RESOURCE_TYPE) || id.getResourceType().equals(ProjectConfigResource.RESOURCE_TYPE)) {
            return VectorIcons.get("script-configure");
        } else {
            return super.getResourceIcon(id);
        }
    }

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
