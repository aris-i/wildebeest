// Wildebeest Migration Framework
// Copyright © 2013 - 2018, Matheson Ventures Pte Ltd
//
// This file is part of Wildebeest
//
// Wildebeest is free software: you can redistribute it and/or modify it under
// the terms of the GNU General Public License v2 as published by the Free
// Software Foundation.
//
// Wildebeest is distributed in the hope that it will be useful, but WITHOUT ANY
// WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
// A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along with
// Wildebeest.  If not, see http://www.gnu.org/licenses/gpl-2.0.html

package co.mv.wb.fixture;

import java.util.UUID;

/**
 * Entry point for the fluent API for creating XML fixtures for unit tests.
 *
 * @since                                       4.0
 */
public class FixtureCreator {

    private boolean rendered = false;
    private boolean renderedSet = false;
    private ResourceCreator resourceCreator = null;
    private boolean resourceCreatorSet = false;

    public static FixtureCreator create() {
        return new FixtureCreator();
    }

    private FixtureCreator() {
        this.setRendered(false);
    }

    private boolean getRendered() {
        if (!renderedSet) {
            throw new IllegalStateException("rendered not set.");
        }
        return rendered;
    }

    private void setRendered(
            boolean value) {
        boolean changing = !renderedSet || rendered != value;
        if (changing) {
            renderedSet = true;
            rendered = value;
        }
    }

    private void clearRendered() {
        if (renderedSet) {
            renderedSet = true;
            rendered = false;
        }
    }

    private boolean hasRendered() {
        return renderedSet;
    }

    public ResourceCreator resource(String type, UUID resourceId, String name) {
        if (this.hasResourceCreator()) {
            throw new RuntimeException("resource already created");
        }

        this.setResourceCreator(new ResourceCreator(this, type, resourceId, name));
        return this.getResourceCreator();
    }


    private ResourceCreator getResourceCreator() {
        if (!resourceCreatorSet) {
            throw new IllegalStateException("resourceCreator not set.");
        }
        if (resourceCreator == null) {
            throw new IllegalStateException("resourceCreator should not be null");
        }
        return resourceCreator;
    }

    private void setResourceCreator(
            ResourceCreator value) {
        if (value == null) {
            throw new IllegalArgumentException("resourceCreator cannot be null");
        }
        boolean changing = !resourceCreatorSet || resourceCreator != value;
        if (changing) {
            resourceCreatorSet = true;
            resourceCreator = value;
        }
    }

    private void clearResourceCreator() {
        if (resourceCreatorSet) {
            resourceCreatorSet = true;
            resourceCreator = null;
        }
    }

    private boolean hasResourceCreator() {
        return resourceCreatorSet;
    }

    public String render() {
        if (this.getRendered()) {
            throw new RuntimeException("already rendered");
        }

        XmlBuilder xml = new XmlBuilder().processingInstruction();

        // Resource
            xml.openElement(
                    "resource",
                    "type", this.getResourceCreator().getType(),
                    "id", this.getResourceCreator().getResourceId().toString(),
                    "name", this.getResourceCreator().getName());

        xml.openElement("states");

        // States
        for (StateCreator state : this.getResourceCreator().getStates()) {
            if(state.hasDescription() && state.hasLabel()){
                xml.openElement("state", "id", state.getStateId().toString(), "label", state.getLabel(),"description", state.getDescription());
            }
            else if(state.hasDescription() && !state.hasLabel()){
                xml.openElement("state", "id", state.getStateId().toString(), "description", state.getDescription());
            }
            else if (state.hasLabel()) {
                xml.openElement("state", "id", state.getStateId().toString(), "label", state.getLabel());
            } else {
                xml.openElement("state", "id", state.getStateId().toString());
            }

            // Assertions
            xml.openElement("assertions");
            for (AssertionCreator assertion : state.getAssertions()) {
                xml.openAssertion(assertion.getType(), assertion.getAssertionId());
                xml.append(assertion.getInnerXml());
                xml.closeAssertion();
            }
            xml.closeElement("assertions");

            xml.closeElement("state");
        }

        xml.closeElement("states");

        // Migrations
        xml.openElement("migrations");

        for (MigrationCreator migration : this.getResourceCreator().getMigrations()) {
            xml.openMigration(
                    migration.getType(),
                    migration.getMigrationId(),
                    migration.hasFromStateId() ? migration.getFromStateId() : null,
                    migration.hasToStateId() ? migration.getToStateId() : null);
            xml.append(migration.getInnerXml());
            xml.closeMigration();
        }

        xml.closeElement("migrations");

        xml.closeElement("resource");

        return xml.toString();
    }

    private String openElement(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
        if ("".equals(name)) {
            throw new IllegalArgumentException("name cannot be empty");
        }

        return openElement(name, new String[]{}, new String[]{});
    }

    private String openElement(
            String name,
            String attrName1, String attrValue1,
            String attrName2, String attrValue2,
            String attrName3, String attrValue3) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
        if ("".equals(name)) {
            throw new IllegalArgumentException("name cannot be empty");
        }
        if (attrName1 == null) {
            throw new IllegalArgumentException("attrName1 cannot be null");
        }
        if ("".equals(attrName1)) {
            throw new IllegalArgumentException("attrName1 cannot be empty");
        }
        if (attrValue1 == null) {
            throw new IllegalArgumentException("attrValue1 cannot be null");
        }
        if (attrName2 == null) {
            throw new IllegalArgumentException("attrName2 cannot be null");
        }
        if ("".equals(attrName2)) {
            throw new IllegalArgumentException("attrName2 cannot be empty");
        }
        if (attrValue2 == null) {
            throw new IllegalArgumentException("attrValue2 cannot be null");
        }
        if (attrName3 == null) {
            throw new IllegalArgumentException("attrName3 cannot be null");
        }
        if ("".equals(attrName3)) {
            throw new IllegalArgumentException("attrName3 cannot be empty");
        }
        if (attrValue3 == null) {
            throw new IllegalArgumentException("attrValue3 cannot be null");
        }

        return openElement(
                name,
                new String[]{attrName1, attrName2, attrName3},
                new String[]{attrValue1, attrValue2, attrValue3});
    }

    private String openElement(
            String name,
            String[] attrNames,
            String[] attrValues) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
        if ("".equals(name)) {
            throw new IllegalArgumentException("name cannot be empty");
        }
        if (attrNames == null) {
            throw new IllegalArgumentException("attrNames cannot be null");
        }
        if (attrValues == null) {
            throw new IllegalArgumentException("attrValues cannot be null");
        }

        if (attrNames.length != attrValues.length) {
            throw new IllegalArgumentException("attrNames and attrValues must be the same length");
        }

        StringBuilder result = new StringBuilder();
        result.append("<").append(name);

        for (int i = 0; i < attrNames.length; i++) {
            result.append(" ").append(attrNames[i]).append("=\"").append(attrValues[i]).append("\"");
        }

        result.append(">\n");

        return result.toString();
    }

    private String closeElement(
            String name) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
        if ("".equals(name)) {
            throw new IllegalArgumentException("name cannot be empty");
        }

        return String.format("</%s>", name);
    }
}
