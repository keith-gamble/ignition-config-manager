/*
 * Copyright 2021 Keith Gamble
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package com.bwdesigngroup.ignition.configmanager.designer.workspaces;

import java.util.List;
import java.util.Optional;

import javax.swing.JComponent;

import com.bwdesigngroup.ignition.configmanager.common.resources.AbstractConfigResource;
import com.bwdesigngroup.ignition.configmanager.common.resources.GatewayConfigResource;
import com.bwdesigngroup.ignition.configmanager.designer.editors.GatewayConfigEditor;
import com.inductiveautomation.ignition.client.icons.VectorIcons;
import com.inductiveautomation.ignition.common.project.resource.ProjectResource;
import com.inductiveautomation.ignition.common.project.resource.ResourcePath;
import com.inductiveautomation.ignition.designer.model.DesignerContext;
import com.inductiveautomation.ignition.designer.navtree.model.AbstractNavTreeNode;
import com.inductiveautomation.ignition.designer.tabbedworkspace.ResourceDescriptor;
import com.inductiveautomation.ignition.designer.tabbedworkspace.ResourceEditor;
import com.inductiveautomation.ignition.designer.workspacewelcome.RecentlyModifiedTablePanel;
import com.inductiveautomation.ignition.designer.workspacewelcome.ResourceBuilderDelegate;
import com.inductiveautomation.ignition.designer.workspacewelcome.ResourceBuilderPanel;
import com.inductiveautomation.ignition.designer.workspacewelcome.WorkspaceWelcomePanel;

/**
 *
 * @author Keith Gamble
 */
public class GatewayConfigWorkspace extends AbstractConfigWorkspace {
    private DesignerContext designerContext;

    public GatewayConfigWorkspace(DesignerContext context, AbstractNavTreeNode navTreeNode) {
        super(context, ResourceDescriptor.builder()
                .resourceType(GatewayConfigResource.RESOURCE_TYPE)
                .nounKey("ConfigManager.config.noun")
                
                .rootFolderText("Gateway")
                .rootIcon(VectorIcons.get("gateway"))
                .navTreeLocation(999)

                .build(), navTreeNode);
        
            
        designerContext = context;
    }

    @Override
    public AbstractConfigResource getResource(String resourceType) {
        return new GatewayConfigResource(resourceType);
    }

    @Override
    public AbstractConfigResource getResource(ProjectResource projectResource) {
        return new GatewayConfigResource(projectResource);
    }

    @Override
    protected ResourceEditor<AbstractConfigResource> newResourceEditor(ResourcePath resourcePath) {
        return new GatewayConfigEditor(this, resourcePath);
    }

    @Override
    public String getKey() {
        return "GatewayConfigWorkspace";
    }

    @Override
    public Optional<JComponent> createWorkspaceHomeTab() {
        return Optional.of(new WorkspaceWelcomePanel("Gateway Config Manager") {
            
        @Override
        public List<JComponent> createPanels() {
            return List.of(
                    new ResourceBuilderPanel(
                            designerContext,
                            "gateway config",
                            configManager.getResourceType().rootPath(),
                            List.of(
                                    ResourceBuilderDelegate.build(
                                            "gateway config",
                                            VectorIcons.get("script-configure"),
                                            configManager.newConfigJson)),
                                            GatewayConfigWorkspace.this::newResourceEditor),
                    new RecentlyModifiedTablePanel(
                        designerContext,
                        configManager.getResourceType(),
                        "gateway config",
                        GatewayConfigWorkspace.this::newResourceEditor));
        }    
        });
    }
}