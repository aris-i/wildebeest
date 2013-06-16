package co.mv.stm.service.dom;

import co.mv.stm.model.Resource;
import co.mv.stm.model.base.FakeResource;
import java.util.UUID;

public class DomFakeResourceBuilder extends BaseDomResourceBuilder
{
	@Override public Resource build(UUID id, String name)
	{
		return new FakeResource(id, name);
	}
}