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

package co.mv.wb.plugin.base;

import co.mv.wb.Migration;

import java.util.Optional;
import java.util.UUID;

/**
 * Provides a base implementation of {@link Migration}
 * 
 * @since                                       1.0
 */
public abstract class BaseMigration implements Migration
{
	/**
	 * Creates a new BaseMigration instance.
	 * 
	 * @param       migrationId                 the ID for the new migration
	 * @param       fromStateId                 the optional from state for the new migration
	 * @param       toStateId                   the optional to state for the new migration
	 */
	protected BaseMigration(
		UUID migrationId,
		Optional<UUID> fromStateId,
		Optional<UUID> toStateId)
	{
		this.setMigrationId(migrationId);
		this.setFromStateId(fromStateId);
		this.setToStateId(toStateId);
	}

	// <editor-fold desc="MigrationId" defaultstate="collapsed">

	private UUID _migrationId = null;
	private boolean _migrationId_set = false;

	@Override public UUID getMigrationId() {
		if(!_migrationId_set) {
			throw new IllegalStateException("migrationId not set.  Use the HasMigrationId() method to check its state before accessing it.");
		}
		return _migrationId;
	}

	private void setMigrationId(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("migrationId cannot be null");
		}
		boolean changing = !_migrationId_set || _migrationId != value;
		if(changing) {
			_migrationId_set = true;
			_migrationId = value;
		}
	}

	private void clearMigrationId() {
		if(_migrationId_set) {
			_migrationId_set = true;
			_migrationId = null;
		}
	}

	private boolean hasMigrationId() {
		return _migrationId_set;
	}

	// </editor-fold>

	// <editor-fold desc="FromStateId" defaultstate="collapsed">

	private Optional<UUID> _fromStateId = null;
	private boolean _fromStateId_set = false;

	@Override public Optional<UUID> getFromStateId() {
		if(!_fromStateId_set) {
			throw new IllegalStateException("fromStateId not set.");
		}
		if(_fromStateId == null) {
			throw new IllegalStateException("fromStateId should not be null");
		}
		return _fromStateId;
	}

	private void setFromStateId(Optional<UUID> value) {
		if(value == null) {
			throw new IllegalArgumentException("fromStateId cannot be null");
		}
		boolean changing = !_fromStateId_set || _fromStateId != value;
		if(changing) {
			_fromStateId_set = true;
			_fromStateId = value;
		}
	}

	// </editor-fold>

	// <editor-fold desc="ToStateId" defaultstate="collapsed">

	private Optional<UUID> _toStateId = null;
	private boolean _toStateId_set = false;

	@Override public Optional<UUID> getToStateId() {
		if(!_toStateId_set) {
			throw new IllegalStateException("toStateId not set.");
		}
		if(_toStateId == null) {
			throw new IllegalStateException("toStateId should not be null");
		}
		return _toStateId;
	}

	private void setToStateId(Optional<UUID> value) {
		if(value == null) {
			throw new IllegalArgumentException("toStateId cannot be null");
		}
		boolean changing = !_toStateId_set || _toStateId != value;
		if(changing) {
			_toStateId_set = true;
			_toStateId = value;
		}
	}

	private void clearToStateId() {
		if(_toStateId_set) {
			_toStateId_set = true;
			_toStateId = null;
		}
	}

	private boolean hasToStateId() {
		return _toStateId_set;
	}

	// </editor-fold>
}
