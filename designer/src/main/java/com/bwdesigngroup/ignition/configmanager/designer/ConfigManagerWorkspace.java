/*
 * Copyright 2021 Keith Gamble
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package com.bwdesigngroup.ignition.configmanager.designer;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;

import com.bwdesigngroup.ignition.configmanager.common.ConfigResource;
import com.inductiveautomation.ignition.client.icons.VectorIcons;
import com.inductiveautomation.ignition.common.project.resource.ProjectResourceBuilder;
import com.inductiveautomation.ignition.common.project.resource.ResourcePath;
import com.inductiveautomation.ignition.common.util.LoggerEx;
import com.inductiveautomation.ignition.designer.model.DesignerContext;
import com.inductiveautomation.ignition.designer.tabbedworkspace.NewResourceAction;
import com.inductiveautomation.ignition.designer.tabbedworkspace.ResourceDescriptor;
import com.inductiveautomation.ignition.designer.tabbedworkspace.ResourceEditor;
import com.inductiveautomation.ignition.designer.tabbedworkspace.ResourceFolderNode;
import com.inductiveautomation.ignition.designer.tabbedworkspace.TabbedResourceWorkspace;
import com.inductiveautomation.ignition.designer.workspacewelcome.RecentlyModifiedTablePanel;
import com.inductiveautomation.ignition.designer.workspacewelcome.ResourceBuilderDelegate;
import com.inductiveautomation.ignition.designer.workspacewelcome.ResourceBuilderPanel;
import com.inductiveautomation.ignition.designer.workspacewelcome.WorkspaceWelcomePanel;

/**
 *
 * @author Keith Gamble
 */
public class ConfigManagerWorkspace extends TabbedResourceWorkspace {
    // Constructor that takes a DesignerContext.

    private static final LoggerEx logger = LoggerEx.newBuilder().build("test.gateway.TestGatewayHook");

    public ConfigManagerWorkspace(DesignerContext context) {
        super(context, ResourceDescriptor.builder()
                .resourceType(ConfigResource.RESOURCE_TYPE)
                .nounKey("Config File")
                .rootFolderText("Process Configs")
                .rootIcon(VectorIcons.get("script-configure"))
                .navTreeLocation(999)
                .build());
    }

    @Override
    public String getKey() {
        return "configEditor";
    }

    private Consumer<ProjectResourceBuilder> newConfigJson = (builder) -> {
        builder.putData(ConfigResource.DATA_KEY, "{}".getBytes());
    };

    
    @Override
    public void addNewResourceActions(ResourceFolderNode folderNode, JPopupMenu menu) {
        menu.add(new NewResourceAction(this, folderNode, newConfigJson) {

            {
                logger.info("addingNewResourceActions");
                putValue(Action.NAME, "New Config");
                putValue(Action.SMALL_ICON, VectorIcons.get("script-configure"));

            }

            @Override
            protected String newResourceName() {
                return "Config";
            }
        });
    }

    @Override
    protected ResourceEditor<ConfigResource> newResourceEditor(ResourcePath resourcePath) {
        return new ConfigEditor(this, resourcePath);
    }

    @Override
    public Optional<JComponent> createWorkspaceHomeTab() {
        DesignerContext context = this.context;
        return Optional.of(new WorkspaceWelcomePanel("Process Config Manager", null, null) {
            @Override
            public List<JComponent> createPanels() {
                return List.of(
                        new ResourceBuilderPanel(
                                context,
                                "process config",
                                ConfigResource.RESOURCE_TYPE.rootPath(),
                                List.of(
                                        ResourceBuilderDelegate.build(
                                                "process config",
                                                VectorIcons.get("script-configure"),
                                                newConfigJson)),
                                ConfigManagerWorkspace.this::newResourceEditor),
                        new RecentlyModifiedTablePanel(
                                context,
                                ConfigResource.RESOURCE_TYPE,
                                "process config",
                                ConfigManagerWorkspace.this::newResourceEditor));
            }
        });
    }

}