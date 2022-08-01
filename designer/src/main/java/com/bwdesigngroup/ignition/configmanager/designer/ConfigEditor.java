/*
 * Copyright 2021 Keith Gamble
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package com.bwdesigngroup.ignition.configmanager.designer;

import java.nio.charset.Charset;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import com.bwdesigngroup.ignition.configmanager.common.ConfigResource;
import com.inductiveautomation.ignition.common.project.resource.ProjectResource;
import com.inductiveautomation.ignition.common.project.resource.ProjectResourceBuilder;
import com.inductiveautomation.ignition.common.project.resource.ResourcePath;
import com.inductiveautomation.ignition.common.util.LoggerEx;
import com.inductiveautomation.ignition.designer.tabbedworkspace.ResourceEditor;
import com.inductiveautomation.ignition.designer.tabbedworkspace.TabbedResourceWorkspace;

import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Keith Gamble
 */
public class ConfigEditor extends ResourceEditor<ConfigResource> {
    private RSyntaxTextArea textArea;
    private static final LoggerEx logger = LoggerEx.newBuilder().build("ConfigEditor");

    public ConfigEditor(TabbedResourceWorkspace workspace, ResourcePath path) {
        super(workspace, path);
    }

    class ConfigDocumentListener implements DocumentListener {
        private ConfigEditor editor;

        public ConfigDocumentListener(ConfigEditor editor) {
            this.editor = editor;
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            logger.info("changedUpdate");
            this.editor.commit();
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            logger.trace("insertUpdate");
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            logger.trace("removeUpdate");
        }

    }


    @Override
    protected void init(ConfigResource configResource) {
        removeAll();
        
        this.setLayout(new MigLayout("fill, insets 0"));

        this.textArea = new RSyntaxTextArea(configResource.json);
        this.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSON);
        this.textArea.getDocument().addDocumentListener(new ConfigDocumentListener(this));
        this.add(new RTextScrollPane(textArea), "grow");
    }

    @Override
    protected ConfigResource getObjectForSave() throws Exception {
        return new ConfigResource(this.textArea.getText());
    }



    @Override
    protected ConfigResource deserialize(ProjectResource resource) {
        byte[] data = resource.getData(ConfigResource.DATA_KEY);

        if (data != null) {
            return new ConfigResource(new String(data, Charset.forName("UTF-8")));
        } else {
            return new ConfigResource("{}");
        }
    }

    @Override
    protected byte[] serialize(ConfigResource configResource) throws Exception {
        return super.serialize(configResource);
    }

    @Override
    protected void serializeResource(ProjectResourceBuilder builder, ConfigResource resource) {
        builder.putData(ConfigResource.DATA_KEY, resource.json.getBytes());
    }

    @Override
    public void commit() {
        logger.info("Commiting change");
        super.commit();
    }

}
