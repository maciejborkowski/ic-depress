package org.impressivecode.depress.its.jiraonline;

import static com.google.common.collect.Lists.newArrayList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.impressivecode.depress.its.jiraonline.model.JiraOnlineFilterListItem;
import org.knime.core.node.defaultnodesettings.DialogComponent;
import org.knime.core.node.defaultnodesettings.DialogComponentStringSelection;
import org.knime.core.node.defaultnodesettings.SettingsModelString;

public abstract class MapperAbstractCustomField {

    protected List<JiraOnlineFilterListItem> fieldList;
    private List<DialogComponent> dialogComponents;
    protected static final String DEFAULT_MAPPING = "Default mapping";
    private HashMap<String, SettingsModelString> mappingMap;

    public MapperAbstractCustomField(List<JiraOnlineFilterListItem> fieldList) {
        this.fieldList = fieldList;
        this.mappingMap = new HashMap<>();
    }

    public void createDialogComponents() {
        dialogComponents = newArrayList();
        for (JiraOnlineFilterListItem item : fieldList) {
            SettingsModelString sms = new SettingsModelString(getMapperModelString() + "." + item.getName(), "");
            sms.setStringValue(getParserValue(item.getName()));
            DialogComponentStringSelection comp = new DialogComponentStringSelection(sms, formatName(item.getName()),
                    getImplementedMappings());
            comp.setToolTipText(item.getDescription());
            dialogComponents.add(comp);
            mappingMap.put(item.getName(), sms);
        }
    }

    private String formatName(String name) {
        return "Map \"" + name + "\" to: ";
    }

    public List<DialogComponent> getDialogComponents() {
        return dialogComponents == null ? new ArrayList<DialogComponent>() : dialogComponents;
    }
    
    public HashMap<String, String> getMappingMap() {
        HashMap<String, String> map = new HashMap<>();
        for (String key : mappingMap.keySet()) {
            map.put(key, mappingMap.get(key).getStringValue());
        }
        return map;
    }

    protected abstract String getMapperModelString();

    protected abstract Collection<String> getImplementedMappings();
    
    protected abstract String getParserValue(String nameToParse);
}
