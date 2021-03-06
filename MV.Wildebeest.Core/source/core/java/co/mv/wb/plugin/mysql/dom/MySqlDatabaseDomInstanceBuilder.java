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

package co.mv.wb.plugin.mysql.dom;

import co.mv.wb.Instance;
import co.mv.wb.InstanceBuilder;
import co.mv.wb.Messages;
import co.mv.wb.PluginBuildException;
import co.mv.wb.V;
import co.mv.wb.plugin.base.dom.BaseDomInstanceBuilder;
import co.mv.wb.plugin.mysql.MySqlDatabaseInstance;

import java.util.Optional;

/**
 * An {@link InstanceBuilder} that builds a {@link MySqlDatabaseInstance} from a DOM {@link org.w3c.dom.Element}.
 * 
 * @since                                       1.0
 */
public class MySqlDatabaseDomInstanceBuilder extends BaseDomInstanceBuilder
{
	@Override public Instance build() throws
		PluginBuildException
	{
		Optional<String> hostName = this.tryGetString("hostName");
		Optional<Integer> port = this.tryGetInteger("port");
		Optional<String> adminUsername = this.tryGetString("adminUsername");
		Optional<String> adminPassword = this.tryGetString("adminPassword");
		Optional<String> databaseName = this.tryGetString("databaseName");
		Optional<String> stateTableName = this.tryGetString("stateTableName");
		
		Messages messages = new Messages();
		if (!hostName.isPresent()) { V.elementMissing(messages, null, "hostName", MySqlDatabaseInstance.class); }
		if (!port.isPresent()) { V.elementMissing(messages, null, "port", MySqlDatabaseInstance.class); }
		if (!adminUsername.isPresent()) { V.elementMissing(messages, null, "adminUsername", MySqlDatabaseInstance.class); }
		if (!adminPassword.isPresent()) { V.elementMissing(messages, null, "adminPassword", MySqlDatabaseInstance.class); }
		if (!databaseName.isPresent()) { V.elementMissing(messages, null, "databaseName", MySqlDatabaseInstance.class); }

		if (messages.size() > 0)
		{
			throw new PluginBuildException(messages);
		}
		
		Instance result = new MySqlDatabaseInstance(
			hostName.get(),
			port.get(),
			adminUsername.get(),
			adminPassword.get(),
			databaseName.get(),
			stateTableName.orElse(null));

		return result;
	}
}
