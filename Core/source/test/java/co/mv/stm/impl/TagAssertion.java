package co.mv.stm.impl;

import co.mv.stm.model.AssertionResponse;
import co.mv.stm.model.AssertionType;
import co.mv.stm.model.ModelExtensions;
import co.mv.stm.model.ResourceInstance;
import co.mv.stm.model.impl.ImmutableAssertionResponse;
import java.util.UUID;

public class TagAssertion extends BaseAssertion
{
	public TagAssertion(
		UUID assertionId,
		String name,
		int seqNum,
		String tag)
	{
		super(assertionId, name, seqNum, AssertionType.DatabaseRowExists);
		
		this.setTag(tag);
	}
	
	// <editor-fold desc="Tag" defaultstate="collapsed">

	private String m_tag = null;
	private boolean m_tag_set = false;

	public String getTag() {
		if(!m_tag_set) {
			throw new IllegalStateException("tag not set.  Use the HasTag() method to check its state before accessing it.");
		}
		return m_tag;
	}

	public void setTag(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("tag cannot be null");
		}
		boolean changing = !m_tag_set || m_tag != value;
		if(changing) {
			m_tag_set = true;
			m_tag = value;
		}
	}

	private void clearTag() {
		if(m_tag_set) {
			m_tag_set = true;
			m_tag = null;
		}
	}

	private boolean hasTag() {
		return m_tag_set;
	}

	// </editor-fold>

	@Override public AssertionResponse apply(ResourceInstance instance)
	{
		if (instance == null) { throw new IllegalArgumentException("instance"); }
		FakeResourceInstance fake = ModelExtensions.As(instance, FakeResourceInstance.class);
		if (fake == null) { throw new IllegalArgumentException("instance must be a FakeResourceInstance"); }
		
		AssertionResponse response = null;
		
		if (this.getTag().equals(fake.getTag()))
		{
			response = new ImmutableAssertionResponse(true, "Tag is as expected");
		}
		else
		{
			response = new ImmutableAssertionResponse(false, "Tag not as expected");
		}
		
		return response;
	}
}