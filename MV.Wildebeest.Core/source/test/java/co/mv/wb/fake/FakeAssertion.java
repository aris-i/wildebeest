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

package co.mv.wb.fake;

import co.mv.wb.Assertion;
import co.mv.wb.AssertionResponse;
import co.mv.wb.Instance;
import co.mv.wb.ModelExtensions;
import co.mv.wb.ResourceType;
import co.mv.wb.impl.BaseAssertion;
import co.mv.wb.impl.ImmutableAssertionResponse;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class FakeAssertion extends BaseAssertion implements Assertion
{
	public FakeAssertion(
		UUID assertionId,
		int seqNum,
		String tag)
	{
		super(assertionId, seqNum);
		
		this.setTag(tag);
	}
	
	@Override public String getDescription()
	{
		return "Fake Assertion";
	}
	
	// <editor-fold desc="Tag" defaultstate="collapsed">

	private String _tag = null;
	private boolean _tag_set = false;

	public String getTag() {
		if(!_tag_set) {
			throw new IllegalStateException("tag not set.  Use the HasTag() method to check its state before accessing it.");
		}
		return _tag;
	}

	public final void setTag(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("tag cannot be null");
		}
		boolean changing = !_tag_set || !_tag.equals(value);
		if(changing) {
			_tag_set = true;
			_tag = value;
		}
	}

	public void clearTag() {
		if(_tag_set) {
			_tag_set = true;
			_tag = null;
		}
	}

	public boolean hasTag() {
		return _tag_set;
	}

	// </editor-fold>
	
	@Override public List<ResourceType> getApplicableTypes()
	{
		return Arrays.asList(
			TestResourceTypes.Fake);
	}

	@Override public AssertionResponse perform(Instance instance)
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannt be null"); }
		FakeInstance fake = ModelExtensions.As(instance, FakeInstance.class);
		if (fake == null) { throw new IllegalArgumentException("instance must be a FakeInstance"); }
		
		boolean result = this.getTag().equals(fake.getTag());
		
		AssertionResponse response;
		
		if (result)
		{
			response = new ImmutableAssertionResponse(
				result,
				String.format("Tag is \"%s\"", this.getTag()));
		}
		else
		{
			response = new ImmutableAssertionResponse(
				result,
				String.format("Tag expected to be \"%s\" but was \"%s\"", this.getTag(), fake.getTag()));
		}
		
		return response;
	}
}
