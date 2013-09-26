// Wildebeest Migration Framework
// Copyright 2013, Zen Digital Co Inc
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

package co.zd.wb.model.mysql;

import co.zd.wb.model.mysql.MySqlDatabaseInstance;
import co.zd.wb.model.mysql.MySqlCreateDatabaseMigration;
import co.zd.wb.model.MigrationFailedException;
import co.zd.helium.fixture.MySqlDatabaseFixture;
import java.sql.SQLException;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;

public class MySqlCreateDatabaseMigrationTests
{
	@Test public void performForNonExistantDatabaseSucceeds() throws
		MigrationFailedException,
		SQLException
	{
		
		//
		// Fixture Setup
		//
		
		MySqlProperties mySqlProperties = MySqlProperties.get();
		
		MySqlCreateDatabaseMigration tr = new MySqlCreateDatabaseMigration(
			UUID.randomUUID(),
			null,
			UUID.randomUUID());

		String databaseName = MySqlElementFixtures.databaseName("StmTest");

		MySqlDatabaseInstance instance = new MySqlDatabaseInstance(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			databaseName);

		//
		// Execute
		//
		
		tr.perform(instance);
		
		//
		// Assert Results
		//

		//
		// Tear-Down
		//

		MySqlUtil.dropDatabase(instance, databaseName);
		
	}

	@Test public void performForExistantDatabaseFails()
	{
		
		//
		// Fixture Setup
		//

		MySqlProperties mySqlProperties = MySqlProperties.get();
		
		MySqlDatabaseFixture f = new MySqlDatabaseFixture(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			"stm_test",
			"");
		f.setUp();
		
		MySqlCreateDatabaseMigration tr = new MySqlCreateDatabaseMigration(
			UUID.randomUUID(),
			null,
			UUID.randomUUID());

		MySqlDatabaseInstance instance = new MySqlDatabaseInstance(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			f.getDatabaseName());

		//
		// Execute
		//

		MigrationFailedException caught = null;
		
		try
		{
			tr.perform(instance);
			
			Assert.fail("MigrationFailedException expected");
		}
		catch (MigrationFailedException e)
		{
			caught = e;
		}
		finally
		{
			f.tearDown();
		}
		
		//
		// Assert Results
		//

		Assert.assertEquals(
			"caught.message",
			String.format("database \"%s\" already exists",	f.getDatabaseName()),
			caught.getMessage());
		
	}
}