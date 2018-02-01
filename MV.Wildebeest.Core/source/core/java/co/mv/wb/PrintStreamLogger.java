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

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * A {@link Logger} that logs to a {@link java.io.PrintStream}.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public class PrintStreamLogger implements Logger
{
	/**
	 * Creates a new PrintStreamLogger that uses the supplied {@link java.io.PrintStream}.
	 * 
	 * @param       stream                      the PrintStream that this logger should use.
	 * @since                                   1.0
	 */
	public PrintStreamLogger(PrintStream stream)
	{
		this.setStream(stream);
	}

	// <editor-fold desc="Stream" defaultstate="collapsed">

	private PrintStream _stream = null;
	private boolean _stream_set = false;

	private PrintStream getStream() {
		if(!_stream_set) {
			throw new IllegalStateException("stream not set.  Use the HasStream() method to check its state before accessing it.");
		}
		return _stream;
	}

	private void setStream(
		PrintStream value) {
		if(value == null) {
			throw new IllegalArgumentException("stream cannot be null");
		}
		boolean changing = !_stream_set || _stream != value;
		if(changing) {
			_stream_set = true;
			_stream = value;
		}
	}

	private void clearStream() {
		if(_stream_set) {
			_stream_set = true;
			_stream = null;
		}
	}

	private boolean hasStream() {
		return _stream_set;
	}

	// </editor-fold>

	@Override public void assertionStart(Assertion assertion)
	{
		if (assertion == null) { throw new IllegalArgumentException("assertion cannot be null"); }

		logLine(this.getStream(), String.format("Starting assertion: %s", assertion.getDescription()));
	}

	@Override public void assertionComplete(
		Assertion assertion,
		AssertionResponse response)
	{
		if (assertion == null) { throw new IllegalArgumentException("assertion cannot be null"); }
		if (response == null) { throw new IllegalArgumentException("response cannot be null"); }
	
		if (response.getResult())
		{
			logLine(this.getStream(), String.format(
				"Assertion \"%s\" passed: %s",
				assertion.getDescription(),
				response.getMessage()));
		}
		
		else
		{
			logLine(this.getStream(), String.format(
				"Assertion \"%s\" failed: %s",
				assertion.getDescription(),
				response.getMessage()));
		}
	}
	
    @Override public void invalidStateSpecified(InvalidStateSpecifiedException e)
    {
        logLine(String.format(
            "The state \"%s\" that was specified is not a valid Wildebeest state identifier",
            e.getSpecifiedState()));
    }
    
    @Override public void unknownStateSpecified(UnknownStateSpecifiedException e)
    {
        logLine(String.format(
            "The state \"%s\" could not be found in this resource",
            e.getSpecifiedState()));
    }

	@Override public void targetNotSpecified(TargetNotSpecifiedException e)
	{
		logLine(String.format(
			"No target was specified and the resource does not have a default target set"));
	}

	@Override public void migrationStart(
		Resource resource,
		Migration migration,
		Optional<State> fromState,
		Optional<State> toState)
	{
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }
		if (migration == null) { throw new IllegalArgumentException("migration cannot be null"); }
		if (fromState == null) { throw new IllegalArgumentException("fromState cannot be null"); }
		if (toState == null) { throw new IllegalArgumentException("toState cannot be null"); }

		if (fromState.isPresent())
		{
			if (toState.isPresent())
			{
				logLine(this.getStream(), String.format(
					"Migrating from state \"%s\" to \"%s\"",
					fromState.get().getDisplayName(),
					toState.get().getDisplayName()));
			}
			else
			{
				logLine(this.getStream(), String.format(
					"Migrating from state \"%s\" to non-existent",
					fromState.get().getDisplayName()));
			}
		}
		else if (toState.isPresent())
		{
				logLine(this.getStream(), String.format(
					"Migrating from non-existent to \"%s\"",
					toState.get().getDisplayName()));
		}
		else
		{
			// TODO Exception?
		}
	}
	
	@Override public void migrationComplete(
		Resource resource,
		Migration migration)
	{
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }
		if (migration == null) { throw new IllegalArgumentException("migration cannot be null"); }
		
		logLine(this.getStream(), "Migration complete");
	}
	
	@Override public void indeterminateState(
		IndeterminateStateException e)
	{
		logLine("Indeterminate state: " + e.getMessage());
	}
	
	@Override public void assertionFailed(AssertionFailedException e)
	{
		logLine("Assertion failed: " + e.getMessage());
	}

	@Override public void migrationNotPossible(MigrationNotPossibleException e)
	{
		logLine("Migration not possible: " + e.getMessage());
	}
	
	@Override public void migrationFailed(MigrationFailedException e)
	{
		logLine("Migration failed: " + e.getMessage());
	}
	
	@Override public void jumpStateFailed(JumpStateFailedException e)
	{
		logLine("JumpState failed: " + e.getMessage());
	}

	@Override public void logLine(String message)
	{
		if (message == null) { throw new IllegalArgumentException("message cannot be null"); }
		
		logLine(this.getStream(), message);
	}
	
	private static void logLine(
		PrintStream out,
		String message)
	{
		if (out == null) { throw new IllegalArgumentException("out cannot be null"); }
		if (message == null) { throw new IllegalArgumentException("message cannot be null"); }
		
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		out.print(f.format(new Date()));
		out.print(" - ");
		out.println(message);
	}
}
