// Wildebeest Migration Framework
// Copyright © 2013 - 2015, Zen Digital Co Inc
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

package co.zd.wb.plugin.postgresql;

import co.zd.wb.FaultException;
import co.zd.wb.IndeterminateStateException;
import co.zd.wb.Instance;
import co.zd.wb.Logger;
import co.zd.wb.ModelExtensions;
import co.zd.wb.State;
import co.zd.wb.plugin.ansisql.AnsiSqlDatabaseInstance;
import co.zd.wb.plugin.base.BaseResource;
import co.zd.wb.plugin.database.Extensions;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Defines a PostgreSQL database resource.
 * 
 * @author                                      Brendon Matheson
 * @since                                       4.0
 */
public class PostgreSqlDatabaseResource extends BaseResource
{
    public PostgreSqlDatabaseResource(
        UUID resourceId,
        String name)
    {
        super(resourceId, name);
    }

    @Override public State currentState(Instance instance) throws IndeterminateStateException
    {
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		PostgreSqlDatabaseInstance db = ModelExtensions.As(instance, PostgreSqlDatabaseInstance.class);
		if (db == null) { throw new IllegalArgumentException("instance must be a PostgreSqlDatabaseInstance"); }

		UUID declaredStateId = null;
		
		if (db.databaseExists())
		{
			declaredStateId = PostgreSqlStateHelper.getStateId(
				this.getResourceId(),
				db.getAppDataSource(),
				Extensions.getMetaSchemaName(db),
				Extensions.getStateTableName(db));
		}
		
		// If we found a declared state, check that the state is actually defined
		State result = null;
		if (declaredStateId != null)
		{
			result = this.stateForId(declaredStateId);

			// If the declared state ID is not known, throw
			if (result == null)
			{
				throw new IndeterminateStateException(String.format(
					"The resource is declared to be in state %s, but this state is not defined for this resource",
					declaredStateId.toString()));
			}
		}
		
		return result;
    }

    @Override public void setStateId(
		Logger logger,
		Instance instance,
		UUID stateId)
    {
		if (logger == null) { throw new IllegalArgumentException("logger"); }
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		AnsiSqlDatabaseInstance db = ModelExtensions.As(instance, AnsiSqlDatabaseInstance.class);
		if (db == null) { throw new IllegalArgumentException("instance must be a MySqlDatabaseInstance"); }
		if (stateId == null) { throw new IllegalArgumentException("stateId"); }
		
		// Set the state tracking row
		try
		{
			PostgreSqlStateHelper.setStateId(
				this.getResourceId(),
				db.getAppDataSource(),
				Extensions.getMetaSchemaName(db),
				Extensions.getStateTableName(db),
				stateId);
		}
		catch (SQLException e)
		{
			throw new FaultException(e);
		}
    }
}