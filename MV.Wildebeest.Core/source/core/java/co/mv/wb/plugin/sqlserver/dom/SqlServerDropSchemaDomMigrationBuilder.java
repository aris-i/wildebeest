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

package co.mv.wb.plugin.sqlserver.dom;

import co.mv.wb.Messages;
import co.mv.wb.Migration;
import co.mv.wb.MigrationBuilder;
import co.mv.wb.PluginBuildException;
import co.mv.wb.V;
import co.mv.wb.plugin.base.dom.BaseDomMigrationBuilder;
import co.mv.wb.plugin.sqlserver.SqlServerDropSchemaMigration;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

/**
 * A {@link MigrationBuilder} that builds a {@link SqlServerDropSchemaMigration} from a DOM {@link org.w3c.dom.Element}.
 * 
 * @since                                       2.0
 */
public class SqlServerDropSchemaDomMigrationBuilder extends BaseDomMigrationBuilder
{
	@Override public Migration build(
		UUID migrationId,
		Optional<UUID> fromStateId,
		Optional<UUID> toStateId,
		File baseDir) throws
			PluginBuildException
	{
		Optional<String> schemaName = this.tryGetString("schemaName");
		
		// Validation
		Messages messages = new Messages();
		if (!schemaName.isPresent())
		{
			messages.addMessage(V.elementMissing(
				migrationId,
				"schemaName",
				SqlServerDropSchemaMigration.class));
		}

		if (messages.size() > 0)
		{
			throw new PluginBuildException(messages);
		}
		
		return new SqlServerDropSchemaMigration(
			migrationId,
			fromStateId,
			toStateId,
			schemaName.get());
	}
}
