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

package co.mv.wb;

import java.util.List;
import java.util.UUID;

/**
 * Indicates that the application of an Assertion to a resource Instance failed, and provides the state for the
 * Assertion failed and the AssertionResult.
 * 
 * @since                                       1.0
 */
public class AssertionFailedException extends Exception
{
	/**
	 * Creates a new AssertionFailedException for the specified state and AssertionResult.
	 * 
	 * @param       stateId                     the state that was being asserted
	 * @param       assertionResults            the full set of assertion results for the state including both those
	 *                                          that succeeded as well as those that failed to trigger this exception
	 * @since                                   1.0
	 */
	public AssertionFailedException(
		UUID stateId,
		List<AssertionResult> assertionResults)
	{
		this.setStateId(stateId);
		this.setAssertionResults(assertionResults);
	}
	
	// <editor-fold desc="StateId" defaultstate="collapsed">

	private UUID _stateId = null;
	private boolean _stateId_set = false;

	/**
	 * Gets the ID of the State for which Assertion evaluation failed.
	 * 
	 * @return                                   the ID of the State for which Assertion evaluation failed
	 * @since                                   1.0
	 */
	public UUID getStateId() {
		if(!_stateId_set) {
			throw new IllegalStateException("stateId not set.  Use the HasStateId() method to check its state before accessing it.");
		}
		return _stateId;
	}

	private void setStateId(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("stateId cannot be null");
		}
		boolean changing = !_stateId_set || _stateId != value;
		if(changing) {
			_stateId_set = true;
			_stateId = value;
		}
	}

	// </editor-fold>
	
	// <editor-fold desc="AssertionResults" defaultstate="collapsed">

	private List<AssertionResult> _assertionResults = null;
	private boolean _assertionResults_set = false;

	/**
	 * Gets the result of the evaluation of the Assertion.
	 * 
	 * @return                                  the set of result items from the assertions that were evalulated
	 * @since                                   1.0
	 */
	public List<AssertionResult> getAssertionResults() {
		if(!_assertionResults_set) {
			throw new IllegalStateException("assertionResults not set.  Use the HasAssertionResults() method to check its state before accessing it.");
		}
		return _assertionResults;
	}

	private void setAssertionResults(List<AssertionResult> value) {
		if(value == null) {
			throw new IllegalArgumentException("assertionResults cannot be null");
		}
		boolean changing = !_assertionResults_set || _assertionResults != value;
		if(changing) {
			_assertionResults_set = true;
			_assertionResults = value;
		}
	}

	// </editor-fold>
}
