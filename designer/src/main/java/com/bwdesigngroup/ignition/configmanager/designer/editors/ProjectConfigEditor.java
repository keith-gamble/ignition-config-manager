/*
 * Copyright 2021 Keith Gamble
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package com.bwdesigngroup.ignition.configmanager.designer.editors;

import com.bwdesigngroup.ignition.configmanager.common.resources.AbstractConfigResource;
import com.bwdesigngroup.ignition.configmanager.common.resources.ProjectConfigResource;
import com.bwdesigngroup.ignition.configmanager.designer.workspaces.AbstractConfigWorkspace;
import com.inductiveautomation.ignition.common.project.resource.ProjectResource;
import com.inductiveautomation.ignition.common.project.resource.ResourcePath;

/**
 *
 * @author Keith Gamble
 */
public class ProjectConfigEditor extends AbstractConfigEditor {

    public ProjectConfigEditor(AbstractConfigWorkspace workspace, ResourcePath path) {
        super(workspace, path);
    }

    @Override
    protected AbstractConfigResource getObjectForSave() throws Exception {

        return new ProjectConfigResource(this.textArea.getText());
    }

    @Override
    protected AbstractConfigResource deserialize(ProjectResource resource) {
        return new ProjectConfigResource(resource);
    }
    
}