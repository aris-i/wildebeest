package co.mv.stm.model.base;

import co.mv.stm.model.Assertion;
import java.util.UUID;

public abstract class BaseAssertion implements Assertion
{
	protected BaseAssertion(
		UUID assertionId,
		String name,
		int seqNum)
	{
		this.setAssertionId(assertionId);
		this.setName(name);
		this.setSeqNum(seqNum);
	}
	
	// <editor-fold desc="AssertionId" defaultstate="collapsed">

	private UUID m_assertionId = null;
	private boolean m_assertionId_set = false;

	public UUID getAssertionId() {
		if(!m_assertionId_set) {
			throw new IllegalStateException("assertionId not set.  Use the HasAssertionId() method to check its state before accessing it.");
		}
		return m_assertionId;
	}

	private void setAssertionId(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("assertionId cannot be null");
		}
		boolean changing = !m_assertionId_set || m_assertionId != value;
		if(changing) {
			m_assertionId_set = true;
			m_assertionId = value;
		}
	}

	private void clearAssertionId() {
		if(m_assertionId_set) {
			m_assertionId_set = true;
			m_assertionId = null;
		}
	}

	private boolean hasAssertionId() {
		return m_assertionId_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="Name" defaultstate="collapsed">

	private String m_name = null;
	private boolean m_name_set = false;

	public String getName() {
		if(!m_name_set) {
			throw new IllegalStateException("name not set.  Use the HasName() method to check its state before accessing it.");
		}
		return m_name;
	}

	private void setName(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("name cannot be null");
		}
		boolean changing = !m_name_set || m_name != value;
		if(changing) {
			m_name_set = true;
			m_name = value;
		}
	}

	private void clearName() {
		if(m_name_set) {
			m_name_set = true;
			m_name = null;
		}
	}

	public boolean hasName() {
		return m_name_set;
	}

	// </editor-fold>

	// <editor-fold desc="SeqNum" defaultstate="collapsed">

	private int m_seqNum = 0;
	private boolean m_seqNum_set = false;

	public int getSeqNum() {
		if(!m_seqNum_set) {
			throw new IllegalStateException("seqNum not set.  Use the HasSeqNum() method to check its state before accessing it.");
		}
		return m_seqNum;
	}

	private void setSeqNum(
		int value) {
		boolean changing = !m_seqNum_set || m_seqNum != value;
		if(changing) {
			m_seqNum_set = true;
			m_seqNum = value;
		}
	}

	private void clearSeqNum() {
		if(m_seqNum_set) {
			m_seqNum_set = true;
			m_seqNum = 0;
		}
	}

	private boolean hasSeqNum() {
		return m_seqNum_set;
	}

	// </editor-fold>
}