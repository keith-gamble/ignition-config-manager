/*
 * Copyright 2021 Keith Gamble
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package com.bwdesigngroup.ignition.configmanager.designer.workspaces;

import javax.swing.Action;
import javax.swing.JPopupMenu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bwdesigngroup.ignition.configmanager.common.resources.AbstractConfigResource;
import com.bwdesigngroup.ignition.configmanager.designer.ConfigManager;
import com.inductiveautomation.ignition.client.icons.VectorIcons;
import com.inductiveautomation.ignition.common.project.resource.ProjectResource;
import com.inductiveautomation.ignition.common.project.resource.ResourcePath;
import com.inductiveautomation.ignition.designer.model.DesignerContext;
import com.inductiveautomation.ignition.designer.navtree.model.AbstractNavTreeNode;
import com.inductiveautomation.ignition.designer.navtree.model.MutableNavTreeNode;
import com.inductiveautomation.ignition.designer.tabbedworkspace.NewResourceAction;
import com.inductiveautomation.ignition.designer.tabbedworkspace.ResourceDescriptor;
import com.inductiveautomation.ignition.designer.tabbedworkspace.ResourceEditor;
import com.inductiveautomation.ignition.designer.tabbedworkspace.ResourceFolderNode;
import com.inductiveautomation.ignition.designer.tabbedworkspace.TabbedResourceWorkspace;

/**
 *
 * @author Keith Gamble
 */
public abstract class AbstractConfigWorkspace extends TabbedResourceWorkspace {

    private AbstractNavTreeNode rootNode;
    private Logger logger = LoggerFactory.getLogger(getClass());
    public ConfigManager configManager;
    
    protected AbstractConfigWorkspace(DesignerContext context, ResourceDescriptor resourceDescriptor, AbstractNavTreeNode rootNode) {
        super(context, resourceDescriptor);
        this.rootNode = rootNode;
        this.configManager = new ConfigManager(this, resourceDescriptor.getResourceType());
    }

    public abstract AbstractConfigResource getResource(String json);
    public abstract AbstractConfigResource getResource(ProjectResource ProjectResource);

    @Override
    protected abstract ResourceEditor<AbstractConfigResource> newResourceEditor(ResourcePath resourcePath);

    @Override
    public MutableNavTreeNode getNavTreeNodeParent() {
        return (MutableNavTreeNode) this.rootNode;
    }
    
    @Override
    public void addNewResourceActions(ResourceFolderNode folderNode, JPopupMenu menu) {
        menu.add(new NewResourceAction(this, folderNode, this.configManager.newConfigJson) {

            {
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
    public String getKey() {
        return "AbstractConfigWorkspace";
    }

}