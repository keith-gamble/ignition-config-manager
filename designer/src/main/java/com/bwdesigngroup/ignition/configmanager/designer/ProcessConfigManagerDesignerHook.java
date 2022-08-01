package com.bwdesigngroup.ignition.configmanager.designer;

import javax.swing.Icon;

import com.bwdesigngroup.ignition.configmanager.common.ConfigResource;
import com.inductiveautomation.ignition.client.icons.VectorIcons;
import com.inductiveautomation.ignition.common.licensing.LicenseState;
import com.inductiveautomation.ignition.common.project.resource.ProjectResourceId;
import com.inductiveautomation.ignition.designer.model.AbstractDesignerModuleHook;
import com.inductiveautomation.ignition.designer.model.DesignerContext;


/**
 * This is the Designer-scope module hook.  The minimal implementation contains a startup method.
 */

public class ProcessConfigManagerDesignerHook extends AbstractDesignerModuleHook {

    @SuppressWarnings("unused")
    private DesignerContext context;

    @Override
    public void startup(DesignerContext context, LicenseState activationState) throws Exception {
        this.context = context;

        context.registerResourceWorkspace( new ConfigManagerWorkspace(context));
    }

    @Override
    public String getResourceCategoryKey(ProjectResourceId id) {
        if (id.getResourceType().equals(ConfigResource.RESOURCE_TYPE)) {
            return "Process Config";
        } else {
            return super.getResourceCategoryKey(id);
        }
    }

    @Override
    public String getResourceDisplayName(ProjectResourceId id) {
        if (id.getResourceType().equals(ConfigResource.RESOURCE_TYPE)) {
            return "Config";
        } else {
            return super.getResourceDisplayName(id);
        }
    }

    @Override
    public Icon getResourceIcon(ProjectResourceId id) {
        if (id.getResourceType().equals(ConfigResource.RESOURCE_TYPE)) {
            return VectorIcons.get("script-configure");
        } else {
            return super.getResourceIcon(id);
        }
    }
}
